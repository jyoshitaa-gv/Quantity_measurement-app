package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        boolean result = l1.equals(l2);
        System.out.println(l1 + " == " + l2 + " ? " + result);
        return result;
    }

    public static double demonstrateLengthConversion(double value,
                                                     Length.LengthUnit from,
                                                     Length.LengthUnit to) {
        double result = Length.convert(value, from, to);
        System.out.printf("convert(%.4f %s → %s) = %.6f%n", value, from.name(), to.name(), result);
        return result;
    }

    public static Length demonstrateLengthConversion(Length length, Length.LengthUnit targetUnit) {
        Length result = length.convertTo(targetUnit);
        System.out.println(length + " → " + targetUnit.name() + " = " + result);
        return result;
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        Length result = l1.add(l2);
        System.out.println(l1 + " + " + l2 + " = " + result);
        return result;
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2,
                                                   Length.LengthUnit targetUnit) {
        Length result = l1.add(l2, targetUnit);
        System.out.println(l1 + " + " + l2 + " in " + targetUnit.name() + " = " + result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("\n=== UC7 Addition with Explicit Target Unit ===");
        demonstrateLengthAddition(
                new Length(1.0, Length.LengthUnit.FEET),
                new Length(12.0, Length.LengthUnit.INCHES),
                Length.LengthUnit.FEET);

        demonstrateLengthAddition(
                new Length(1.0, Length.LengthUnit.FEET),
                new Length(12.0, Length.LengthUnit.INCHES),
                Length.LengthUnit.INCHES);

        demonstrateLengthAddition(
                new Length(1.0, Length.LengthUnit.FEET),
                new Length(12.0, Length.LengthUnit.INCHES),
                Length.LengthUnit.YARDS);

        demonstrateLengthAddition(
                new Length(36.0, Length.LengthUnit.INCHES),
                new Length(1.0,  Length.LengthUnit.YARDS),
                Length.LengthUnit.FEET);

        demonstrateLengthAddition(
                new Length(5.0, Length.LengthUnit.FEET),
                new Length(-2.0, Length.LengthUnit.FEET),
                Length.LengthUnit.INCHES);
    }
}