public class QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return value * unit.getConversionFactor();
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
            double converted = toBaseUnit() / targetUnit.getConversionFactor();
            return new QuantityLength(converted, targetUnit);
        }

        public QuantityLength add(QuantityLength other) {
            if (other == null) throw new IllegalArgumentException("Operand cannot be null");
            double sumInBase = this.toBaseUnit() + other.toBaseUnit();
            double resultInUnit = sumInBase / this.unit.getConversionFactor();
            return new QuantityLength(resultInUnit, this.unit);
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit.name();
        }
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) throw new IllegalArgumentException("Units cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        return new QuantityLength(value, source).convertTo(target).getValue();
    }

    public static QuantityLength add(QuantityLength a, QuantityLength b) {
        if (a == null || b == null) throw new IllegalArgumentException("Operands cannot be null");
        return a.add(b);
    }

    public static QuantityLength add(double v1, LengthUnit u1, double v2, LengthUnit u2) {
        return add(new QuantityLength(v1, u1), new QuantityLength(v2, u2));
    }

    public static void main(String[] args) {
        System.out.println(add(1.0, LengthUnit.FEET, 2.0, LengthUnit.FEET));
        System.out.println(add(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCH));
        System.out.println(add(12.0, LengthUnit.INCH, 1.0, LengthUnit.FEET));
        System.out.println(add(1.0, LengthUnit.YARD, 3.0, LengthUnit.FEET));
        System.out.println(add(36.0, LengthUnit.INCH, 1.0, LengthUnit.YARD));
        System.out.println(add(2.54, LengthUnit.CENTIMETER, 1.0, LengthUnit.INCH));
        System.out.println(add(5.0, LengthUnit.FEET, 0.0, LengthUnit.INCH));
        System.out.println(add(5.0, LengthUnit.FEET, -2.0, LengthUnit.FEET));
    }
}