package com.apps.quantitymeasurement;

public class Length {

    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 1e-6;

    public Length(double value, LengthUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        this.value = value;
        this.unit = unit;
    }

    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);
        converted = Math.round(converted * 100.0) / 100.0;
        return new Length(converted, targetUnit);
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) throw new IllegalArgumentException("Units cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        double baseValue = source.convertToBaseUnit(value);
        return target.convertFromBaseUnit(baseValue);
    }

    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public boolean compare(Length other) {
        return Math.abs(this.convertToBaseUnit() - other.convertToBaseUnit()) < EPSILON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.compare((Length) o);
    }

    @Override
    public String toString() {
        return String.format("Length(%.6f %s)", value, unit.name());
    }


    private Length addInTargetUnit(Length other, LengthUnit targetUnit) {
        if (other == null) throw new IllegalArgumentException("Operand cannot be null");
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
        double sumInBase = this.convertToBaseUnit() + other.convertToBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);
        resultValue = Math.round(resultValue * 100.0) / 100.0;
        return new Length(resultValue, targetUnit);
    }

    public Length add(Length other) {
        return addInTargetUnit(other, this.unit);
    }

    public Length add(Length other, LengthUnit targetUnit) {
        return addInTargetUnit(other, targetUnit);
    }

    public static void main(String[] args) {
        Length feet   = new Length(1.0,  LengthUnit.FEET);
        Length inches = new Length(12.0, LengthUnit.INCHES);
        System.out.println(feet.convertTo(LengthUnit.INCHES));      // Length(12.0 INCHES)
        System.out.println(feet.add(inches, LengthUnit.FEET));       // Length(2.0 FEET)
        System.out.println(feet.equals(inches));                     // true
        System.out.println(LengthUnit.FEET.convertToBaseUnit(12.0)); // 12.0
        System.out.println(LengthUnit.INCHES.convertToBaseUnit(12.0)); // 1.0
    }
}