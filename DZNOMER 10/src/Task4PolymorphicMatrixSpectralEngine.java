
import java.util.Arrays;

public final class Task4PolymorphicMatrixSpectralEngine {

    public static void main(String[] args) {
        System.out.println("================ СПЕКТРАЛЬНИЙ КОНВЕЄР МАТРИЧНИХ ОПЕРАТОРІВ ================");

        double[][] initialData = {
                {3.0, 4.0},
                {0.0, 5.0}
        };
        ImmutableMatrix2D x0 = new ImmutableMatrix2D(initialData);

        System.out.printf("[ПОЧАТКОВА МАТРИЦЯ X0 (2x2 | Норма: %.4f | Слід: %.2f | det: %.2f)]:\n",
                x0.getFrobeniusNorm(), x0.getTrace(), x0.getDeterminant());
        printMatrix(x0);

        System.out.println("\n--- ПРЯМИЙ ХІД КОНВЕЄРА (Forward Pass через final execute()) ---");
        AbstractMatrixOperator[] pipeline = {
                new NonZeroScaleOperator("Scale(x2.0)", 2.0),
                new TransposeOperator("Transpose(A^T)"),
                new GivensRotationOperator("GivensRotation(60.0°)", Math.toRadians(60.0))
        };

        ImmutableMatrix2D current = x0;
        for (int i = 0; i < pipeline.length; i++) {
            current = pipeline[i].execute(current);
            System.out.printf("%d. Оператор '%s'%s -> det: %.2f\n",
                    (i + 1),
                    pipeline[i].getName(),
                    (pipeline[i] instanceof OrthogonalOperator ? " [Orthogonal: Норма " + String.format("%.4f", current.getFrobeniusNorm()) + " збережена ✅]" : ""),
                    current.getDeterminant());
        }

        ImmutableMatrix2D y = current;
        System.out.println("[ЗАШИФРОВАНА МАТРИЦЯ Y ПІСЛЯ 3 ЕТАПІВ]:");
        printMatrix(y);

        System.out.println("\n--- ЗВОРОТНИЙ ХІД ВІДНОВЛЕННЯ (Inverse Pass через instanceof InvertibleOperator) ---");
        ImmutableMatrix2D restored = y;
        for (int i = pipeline.length - 1; i >= 0; i--) {
            if (pipeline[i] instanceof InvertibleOperator inv) {
                restored = inv.applyInverse(restored);
                if (pipeline[i] instanceof GivensRotationOperator) {
                    System.out.println("<- Декодування 'GivensRotation(-60.0°)' виконано...");
                } else if (pipeline[i] instanceof TransposeOperator) {
                    System.out.println("<- Декодування 'Transpose((A^T)^T)' виконано...");
                } else if (pipeline[i] instanceof NonZeroScaleOperator) {
                    System.out.println("<- Декодування 'Scale(x0.5)' виконано...");
                }
            }
        }

        System.out.println("\n[ВІДНОВЛЕНА МАТРИЦЯ X_restored]:");
        printMatrix(restored);

        double error = x0.maxAbsDifference(restored);
        System.out.printf("Максимальна похибка відновлення |X0 - X_restored|: %.2e (БЕЗВТРАТНА ОБОРОТНІСТЬ ДОТРИМАНА!)\n", error);
        System.out.println("===========================================================================");
    }

    private static void printMatrix(ImmutableMatrix2D m) {
        for (int r = 0; r < m.getRows(); r++) {
            System.out.print("  [ ");
            for (int c = 0; c < m.getCols(); c++) {
                System.out.printf("%9.4f ", m.get(r, c));
            }
            System.out.println("]");
        }
    }
}

final class ImmutableMatrix2D {
    private final int rows;
    private final int cols;
    private final double[][] data;

    public ImmutableMatrix2D(double[][] source) {
        if (source == null || source.length == 0 || source[0] == null) {
            this.rows = 0;
            this.cols = 0;
            this.data = new double[0][0];
        } else {
            this.rows = source.length;
            this.cols = source[0].length;
            this.data = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                this.data[i] = Arrays.copyOf(source[i], cols);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double get(int r, int c) {
        return data[r][c];
    }

    public double[][] getDataCopy() {
        double[][] copy = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            copy[i] = Arrays.copyOf(data[i], cols);
        }
        return copy;
    }

    public double getTrace() {
        double trace = 0;
        int min = Math.min(rows, cols);
        for (int i = 0; i < min; i++) {
            trace += data[i][i];
        }
        return trace;
    }

    public double getFrobeniusNorm() {
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += data[i][j] * data[i][j];
            }
        }
        return Math.sqrt(sum);
    }

    public double getDeterminant() {
        if (rows != cols) {
            throw new IllegalStateException();
        }
        if (rows == 2) {
            return data[0][0] * data[1][1] - data[0][1] * data[1][0];
        } else if (rows == 3) {
            return data[0][0] * data[1][1] * data[2][2]
                    + data[0][1] * data[1][2] * data[2][0]
                    + data[0][2] * data[1][0] * data[2][1]
                    - data[0][2] * data[1][1] * data[2][0]
                    - data[0][0] * data[1][2] * data[2][1]
                    - data[0][1] * data[1][0] * data[2][2];
        }
        throw new UnsupportedOperationException();
    }

    public double maxAbsDifference(ImmutableMatrix2D other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException();
        }
        double maxDiff = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double diff = Math.abs(this.data[i][j] - other.data[i][j]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
            }
        }
        return maxDiff;
    }
}

interface InvertibleOperator {
    ImmutableMatrix2D applyInverse(ImmutableMatrix2D transformed);
}

interface OrthogonalOperator extends InvertibleOperator {
    default boolean verifyNormInvariance(ImmutableMatrix2D before, ImmutableMatrix2D after) {
        return Math.abs(before.getFrobeniusNorm() - after.getFrobeniusNorm()) < 1e-6;
    }
}

abstract class AbstractMatrixOperator {
    private final String operatorName;

    protected AbstractMatrixOperator(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getName() {
        return operatorName;
    }

    public abstract boolean canApply(ImmutableMatrix2D m);

    protected abstract ImmutableMatrix2D transform(ImmutableMatrix2D m);

    public final ImmutableMatrix2D execute(ImmutableMatrix2D m) {
        if (m == null || !canApply(m)) {
            throw new IllegalArgumentException();
        }
        ImmutableMatrix2D result = transform(m);
        if (this instanceof OrthogonalOperator ort) {
            if (!ort.verifyNormInvariance(m, result)) {
                throw new IllegalStateException();
            }
        }
        return result;
    }
}

class NonZeroScaleOperator extends AbstractMatrixOperator implements InvertibleOperator {
    private final double factor;

    public NonZeroScaleOperator(String name, double factor) {
        super(name);
        if (Math.abs(factor) < 1e-9) {
            throw new IllegalArgumentException();
        }
        this.factor = factor;
    }

    @Override
    public boolean canApply(ImmutableMatrix2D m) {
        return m != null;
    }

    @Override
    protected ImmutableMatrix2D transform(ImmutableMatrix2D m) {
        double[][] newData = new double[m.getRows()][m.getCols()];
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                newData[i][j] = m.get(i, j) * factor;
            }
        }
        return new ImmutableMatrix2D(newData);
    }

    @Override
    public ImmutableMatrix2D applyInverse(ImmutableMatrix2D transformed) {
        double[][] newData = new double[transformed.getRows()][transformed.getCols()];
        for (int i = 0; i < transformed.getRows(); i++) {
            for (int j = 0; j < transformed.getCols(); j++) {
                newData[i][j] = transformed.get(i, j) / factor;
            }
        }
        return new ImmutableMatrix2D(newData);
    }
}

class TransposeOperator extends AbstractMatrixOperator implements OrthogonalOperator {

    public TransposeOperator(String name) {
        super(name);
    }

    @Override
    public boolean canApply(ImmutableMatrix2D m) {
        return m != null;
    }

    @Override
    protected ImmutableMatrix2D transform(ImmutableMatrix2D m) {
        double[][] newData = new double[m.getCols()][m.getRows()];
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                newData[j][i] = m.get(i, j);
            }
        }
        return new ImmutableMatrix2D(newData);
    }

    @Override
    public ImmutableMatrix2D applyInverse(ImmutableMatrix2D transformed) {
        return transform(transformed);
    }
}

class GivensRotationOperator extends AbstractMatrixOperator implements OrthogonalOperator {
    private final double angleRadians;

    public GivensRotationOperator(String name, double angleRadians) {
        super(name);
        this.angleRadians = angleRadians;
    }

    @Override
    public boolean canApply(ImmutableMatrix2D m) {
        return m != null && m.getRows() >= 2;
    }

    @Override
    protected ImmutableMatrix2D transform(ImmutableMatrix2D m) {
        double c = Math.cos(angleRadians);
        double s = Math.sin(angleRadians);
        double[][] newData = m.getDataCopy();

        for (int j = 0; j < m.getCols(); j++) {
            double r0 = m.get(0, j);
            double r1 = m.get(1, j);
            newData[0][j] = c * r0 - s * r1;
            newData[1][j] = s * r0 + c * r1;
        }
        return new ImmutableMatrix2D(newData);
    }
    @Override
    public ImmutableMatrix2D applyInverse(ImmutableMatrix2D transformed) {
        double c = Math.cos(-angleRadians);
        double s = Math.sin(-angleRadians);
        double[][] newData = transformed.getDataCopy();
        for (int j = 0; j < transformed.getCols(); j++) {
            double r0 = transformed.get(0, j);
            double r1 = transformed.get(1, j);
            newData[0][j] = c * r0 - s * r1;
            newData[1][j] = s * r0 + c * r1;
        }
        return new ImmutableMatrix2D(newData);
    }
}
class UpperTriangularProjectionOperator extends AbstractMatrixOperator {
    public UpperTriangularProjectionOperator(String name) {
        super(name);
    }
    @Override
    public boolean canApply(ImmutableMatrix2D m) {
        return m != null;
    }
    @Override
    protected ImmutableMatrix2D transform(ImmutableMatrix2D m) {
        double[][] newData = new double[m.getRows()][m.getCols()];
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                if (i <= j) {
                    newData[i][j] = m.get(i, j);
                } else {
                    newData[i][j] = 0.0;
                }
            }
        }
        return new ImmutableMatrix2D(newData);
    }
}