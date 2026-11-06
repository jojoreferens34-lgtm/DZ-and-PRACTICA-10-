


public class Practice10 {

    public static void main(String[] args) {
        PaymentInstrument[] payments = {
                new BankCardInstrument("Card Payer"),
                new CryptoTokenInstrument("Crypto Payer", 150.0),
                new IbanDirectInstrument("IBAN Payer")
        };
        for (PaymentInstrument p : payments) {
            p.processPayment(10000.0);
        }

        Shape2D[] shapes = {
                new CircleShape(5.0),
                new RectangleShape(4.0, 6.0),
                new EquilateralTriangleShape(4.0)
        };
        findMaxAreaShape(shapes);

        VectorMathOverload vMath = new VectorMathOverload();
        vMath.norm(3.0);
        vMath.norm(3.0, 4.0);
        vMath.norm(new double[]{1.0, 2.0, 2.0});

        BinaryOperation[] ops = { new AddOp(), new MultiplyOp(), new HypotenuseOp() };
        for (BinaryOperation op : ops) {
            op.apply(3.0, 4.0);
        }

        NotificationSender[] senders = {
                new EmailSender("user@mail.com"),
                new SmsSender("+380000000000"),
                new TelegramBotSender("@bot")
        };
        broadcastAlert(senders, "Alert Message");

        ShippingTariff[] tariffs = {
                new RoadTruckTariff(),
                new AirCargoTariff(),
                new RailFreightTariff()
        };
        double minCost = Double.MAX_VALUE;
        for (ShippingTariff t : tariffs) {
            double cost = t.calculateCost(250.0, 600.0);
            if (cost < minCost) {
                minCost = cost;
            }
        }

        DifferentiableFunction[] funcs = {
                new QuadraticParabola(1, 2, 3),
                new NaturalLogFunction(2),
                new ExponentialGrowth(1, 0.5)
        };
        for (DifferentiableFunction f : funcs) {
            f.f(2.0);
            f.derivativeAt(2.0);
        }

        UiWidget[] widgets = {
                new ButtonWidget("b1", 100, 30, "Click"),
                new TextInputWidget("t1", 200, 30, "Enter..."),
                new ProgressBarWidget("p1", 300, 20, 75)
        };
        buildPage(widgets);

        IndustrialAsset[] assets = {
                new LinearDepreciationAsset("A1", 100000, 10),
                new DecliningBalanceAsset("A2", 100000, 10, 0.2)
        };
        double totalBookValue = 0;
        for (IndustrialAsset asset : assets) {
            totalBookValue += asset.getBookValueAfterYears(3);
        }

        Measurable[] measurables = {
                new BankVault(500.0),
                new HighwayRoute(120.5),
                new CpuCore(3.6)
        };
        computeAverage(measurables);

        KineticBody body = new KineticBody(10, 5);
        body.getEnergyKwh();

        AmphibiousRescueDrone drone = new AmphibiousRescueDrone();
        testFlight(drone);
        testDiving(drone);
        logPosition(drone);

        EmailValidator ev = new EmailValidator();
        ev.validateOrDefault("test@mail.com", "fallback");

        EmergencyBeacon beacon = new EmergencyBeacon();
        beacon.emitAlert();

        EscrowSmartContract contract = new EscrowSmartContract();
        contract.verifySignature("key");
        contract.getAuditTrail();
        contract.executeContract(50000);

        Transformable2D[] transformables = {
                new BoundingBox(0, 0, 10, 10),
                new CircleRegion(5, 5, 3)
        };
        applyGlobalTransform(transformables, 2, 3, 1.5);

        DataExportable[] records = {
                new StudentGradeRecord("John", 101, 4.8),
                new WeatherObservation("Kyiv", 15.5, 60)
        };
        for (DataExportable r : records) {
            r.toJson();
            r.toCsvRow();
        }

        AbstractBird[] birds = { new Falcon("F1", 1.2), new EmperorPenguin("P1", 30.0) };
        FlyableUnit[] flyers = { new Falcon("F2", 1.1), new CargoPlane() };

        AbstractSmartAppliance[] appliances = {
                new SmartLedChandelier("Living Room", "ModelX"),
                new SmartUnderfloorHeating("Kitchen", "ModelY")
        };
        double totalWatts = 0;
        for (AbstractSmartAppliance app : appliances) {
            totalWatts += app.getCurrentWatts();
        }

        AbstractFinancialInstrument[] instruments = {
                new TechGrowthStock("AAPL", 10, 180.0),
                new BlueChipStock("MSFT", 5, 400.0, 10.0),
                new FixedTermDeposit("DEP123", 1, 50000.0, 0.12)
        };

        Vec2D v = new Vec2D(1, 1);
        AbstractVectorOperator[] vecOps = {
                new Rotation2DOperator("Rot", Math.toRadians(45)),
                new ProjectionXOperator("Proj")
        };
        for (AbstractVectorOperator op : vecOps) {
            op.transform(v);
        }

        AbstractVehicle[] vehicles = {
                new ElectricCityBus("AA1111AA", 100),
                new DieselDumpTruck("AA2222AA", 15),
                new CargoPassengerVan("AA3333AA", 2.5, 7)
        };

        AbstractMilitaryUnit[] army = { new HeavySniper("S1"), new CombatMedic("M1"), new PaladinCaptain("P1") };

        AbstractLabDevice[] labDevices = { new ElectronicScale("E101"), new GlassThermometer("G202") };

        TextProcessor[] textOps = { new CaesarCipherProcessor(3), new WhitespaceTrimmerProcessor() };
        String txt = " Hello ";
        for (TextProcessor top : textOps) {
            txt = top.process(txt);
        }

        Shape2D[] shapeBatch = { new CircleShape(3), new RectangleShape(4, 4) };
        for (Shape2D s : shapeBatch) {
            if (s instanceof CircleShape c) {
                c.getDiameter();
            } else if (s instanceof RectangleShape r) {
                r.getDiagonal();
                r.isSquare();
            }
        }

        Object[] warehouse = { new Laptop(), new DesktopMonitor(), new SmartWatch() };
        chargeAllPossible(warehouse);

        AbstractWorker[] staff = { new FullTimeDeveloper(), new FopContractor(), new InternStudent() };
        double baseSalaryPool = 0, bonusPool = 0;
        int insuredCount = 0;
        for (AbstractWorker w : staff) {
            baseSalaryPool += w.getBaseSalary();
            if (w instanceof BonusEligible b) {
                bonusPool += b.calculateQuarterBonus();
            }
            if (w instanceof MedicalInsured) {
                insuredCount++;
            }
        }

        ShipModule[] modules = { new ShieldGenerator("SG1"), new WarpDrive("WD1"), new LifeSupportModule("LS1") };
        runShipInspection(modules);

        MediaItem[] media = { new VideoFile("V1", 5000, 2160, 7200), new AudioFile("A1", 50, 1411), new EBookFile("E1", 5, 350) };
        double premiumSize = 0;
        for (MediaItem item : media) {
            if (item instanceof VideoFile vf && vf.is4K()) {
                premiumSize += vf.sizeMb;
            } else if (item instanceof AudioFile af && af.isLossless()) {
                premiumSize += af.sizeMb;
            }
        }

        AbstractCustomer cust = new VipCustomer();
        double finalPrice = 1000.0 * (1.0 - cust.getDiscountPercent() / 100.0);

        Matrix2x2[] matrices = { new RotationMatrix2x2(Math.toRadians(30)), new SingularMatrix2x2() };
        for (Matrix2x2 m : matrices) {
            if (m instanceof InvertibleMatrix inv) {
                inv.inverse();
            }
        }

        CargoContainer[] cargo = { new ChemicalCargo(), new FoodCargo() };
        for (CargoContainer cc : cargo) {
            if (cc instanceof HazardousCargo hc) {
                hc.getHazardClass();
            } else if (cc instanceof RefrigeratedCargo rc) {
                rc.getRequiredTempC();
            }
        }

        FinalCalibrationTable fct = new FinalCalibrationTable("SN-999", new double[]{1.1, 2.2});
        fct.getCoefficients()[0] = 5.5;

        AbstractSecurityProtocol sec = new JwtSecurityProtocol();
        sec.runSecureCommand("token", "payload");

        MetricSpaceElement element = new ImmutableVector3D(1, 2, 3);
        element.getNorm();

        PrintableDocument[] docs = { new FiscalReceipt("F-12", 1200), new WarehouseReport() };

        PowerProducer[] grid = { new NuclearStation("N1"), new SolarFarmStation("S1"), new HydroDamStation("H1") };
        for (PowerProducer pp : grid) {
            if (pp instanceof WeatherDependent wd) {
                wd.updateWeatherCondition(0.8);
            }
        }

        CubicFunction cubic = new CubicFunction();
        cubic.integrateTrapezoid(0, 3, 1000);
        cubic.findRootBisection(2.0, 3.0, 1e-6);

        AbstractCombatUnit[] combatants = { new BerserkerAssasin("B1"), new TrollWarlord("T1"), new RoyalPaladin("R1") };
        for (AbstractCombatUnit unit : combatants) {
            if (unit instanceof SelfRegenerating reg) {
                reg.regenerateHp();
            }
            unit.calculateDamage();
        }
    }

    public static Shape2D findMaxAreaShape(Shape2D[] shapes) {
        if (shapes == null || shapes.length == 0) return null;
        Shape2D maxShape = shapes[0];
        for (Shape2D s : shapes) {
            if (s.getArea() > maxShape.getArea()) {
                maxShape = s;
            }
        }
        return maxShape;
    }

    public static void broadcastAlert(NotificationSender[] senders, String alertText) {
        for (NotificationSender s : senders) {
            s.send(alertText);
        }
    }

    public static String buildPage(UiWidget[] pageElements) {
        StringBuilder sb = new StringBuilder("<html><body>");
        for (UiWidget w : pageElements) {
            sb.append(w.renderHtml());
        }
        sb.append("</body></html>");
        return sb.toString();
    }

    public static double computeAverage(Measurable[] items) {
        if (items == null || items.length == 0) return 0;
        double sum = 0;
        for (Measurable item : items) {
            sum += item.getMeasure();
        }
        return sum / items.length;
    }
