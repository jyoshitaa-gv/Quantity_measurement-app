package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        boolean result = l1.equals(l2);
        System.out.println(l1 + " == " + l2 + " ? " + result);
        return result;
    }

    public static double demonstrateLengthConversion(double value,
                                                     LengthUnit from, LengthUnit to) {
        double result = Length.convert(value, from, to);
        System.out.printf("convert(%.4f %s → %s) = %.6f%n", value, from.name(), to.name(), result);
        return result;
    }

    public static Length demonstrateLengthConversion(Length length, LengthUnit targetUnit) {
        Length result = length.convertTo(targetUnit);
        System.out.println(length + " → " + targetUnit.name() + " = " + result);
        return result;
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        Length result = l1.add(l2);
        System.out.println(l1 + " + " + l2 + " = " + result);
        return result;
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit targetUnit) {
        Length result = l1.add(l2, targetUnit);
        System.out.println(l1 + " + " + l2 + " in " + targetUnit.name() + " = " + result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== UC8 Refactored Demo ===");
        demonstrateLengthEquality(
                new Length(1.0,  LengthUnit.FEET),
                new Length(12.0, LengthUnit.INCHES));

        demonstrateLengthAddition(
                new Length(1.0,  LengthUnit.FEET),
                new Length(12.0, LengthUnit.INCHES),
                LengthUnit.FEET);

        System.out.println(LengthUnit.FEET.convertToBaseUnit(12.0));
        System.out.println(LengthUnit.INCHES.convertToBaseUnit(12.0));
        System.out.println(LengthUnit.INCHES.convertFromBaseUnit(1.0));
        System.out.println(LengthUnit.YARDS.convertFromBaseUnit(3.0));
    }
}