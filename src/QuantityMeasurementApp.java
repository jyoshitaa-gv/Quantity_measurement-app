package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    public static boolean demonstrateWeightEquality(Weight w1, Weight w2) {
        boolean result = w1.equals(w2);
        System.out.println(w1 + " == " + w2 + " ? " + result);
        return result;
    }

    public static Weight demonstrateWeightConversion(Weight weight, WeightUnit targetUnit) {
        Weight result = weight.convertTo(targetUnit);
        System.out.println(weight + " → " + targetUnit.name() + " = " + result);
        return result;
    }

    public static Weight demonstrateWeightAddition(Weight w1, Weight w2) {
        Weight result = w1.add(w2);
        System.out.println(w1 + " + " + w2 + " = " + result);
        return result;
    }

    public static Weight demonstrateWeightAddition(Weight w1, Weight w2, WeightUnit targetUnit) {
        Weight result = w1.add(w2, targetUnit);
        System.out.println(w1 + " + " + w2 + " in " + targetUnit.name() + " = " + result);
        return result;
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit targetUnit) {
        Length result = l1.add(l2, targetUnit);
        System.out.println(l1 + " + " + l2 + " in " + targetUnit.name() + " = " + result);
        return result;
    }

    public static void main(String[] args) {
        demonstrateWeightEquality(
                new Weight(1.0,    WeightUnit.KILOGRAM),
                new Weight(1000.0, WeightUnit.GRAM));

        demonstrateWeightEquality(
                new Weight(1.0,     WeightUnit.KILOGRAM),
                new Weight(2.20462, WeightUnit.POUND));

        demonstrateWeightConversion(
                new Weight(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);

        demonstrateWeightConversion(
                new Weight(2.0, WeightUnit.POUND),    WeightUnit.KILOGRAM);

        demonstrateWeightAddition(
                new Weight(1.0,    WeightUnit.KILOGRAM),
                new Weight(1000.0, WeightUnit.GRAM));

        demonstrateWeightAddition(
                new Weight(1.0,    WeightUnit.KILOGRAM),
                new Weight(1000.0, WeightUnit.GRAM),
                WeightUnit.GRAM);
    }
}