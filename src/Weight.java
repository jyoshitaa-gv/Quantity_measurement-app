package com.apps.quantitymeasurement;


public class Weight {

    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 1e-6;

    public Weight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        this.value = value;
        this.unit = unit;
    }


    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public Weight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);
        converted = Math.round(converted * 100.0) / 100.0;
        return new Weight(converted, targetUnit);
    }

    public static double convert(double value, WeightUnit source, WeightUnit target) {
        if (source == null || target == null) throw new IllegalArgumentException("Units cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        return source.convertToBaseUnit(value) / target.getConversionFactor();
    }

    public boolean compare(Weight other) {
        return Math.abs(this.convertToBaseUnit() - other.convertToBaseUnit()) < EPSILON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.compare((Weight) o);
    }

    @Override
    public int hashCode() {
        // Round to 9 decimal places before hashing to make equal objects hash the same
        long bits = Double.doubleToLongBits(
                Math.round(convertToBaseUnit() * 1_000_000_000.0) / 1_000_000_000.0);
        return Long.hashCode(bits);
    }

    @Override
    public String toString() {
        return String.format("Weight(%.6f %s)", value, unit.name());
    }

    private Weight addInTargetUnit(Weight other, WeightUnit targetUnit) {
        if (other == null) throw new IllegalArgumentException("Operand cannot be null");
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
        double sumInBase = this.convertToBaseUnit() + other.convertToBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);
        resultValue = Math.round(resultValue * 100.0) / 100.0;
        return new Weight(resultValue, targetUnit);
    }

    public Weight add(Weight other) {
        return addInTargetUnit(other, this.unit);
    }

    public Weight add(Weight other, WeightUnit targetUnit) {
        return addInTargetUnit(other, targetUnit);
    }

    public static void main(String[] args) {
        Weight kg   = new Weight(1.0,    WeightUnit.KILOGRAM);
        Weight gram = new Weight(1000.0, WeightUnit.GRAM);
        System.out.println(kg.equals(gram));
        System.out.println(kg.convertTo(WeightUnit.GRAM));
        System.out.println(kg.add(gram));
        System.out.println(kg.add(gram, WeightUnit.GRAM));
    }
}