package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    public void testAddition_ExplicitTargetUnit_WithZero() {
        Length result = new Length(5.0, LengthUnit.FEET)
                .add(new Length(0.0, LengthUnit.INCHES), LengthUnit.YARDS);
        assertEquals(new Length(1.67, LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NegativeValues() {
        Length result = new Length(5.0, LengthUnit.FEET)
                .add(new Length(-2.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertEquals(new Length(36.0, LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new Length(1.0, LengthUnit.FEET)
                        .add(new Length(12.0, LengthUnit.INCHES), null));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
        Length result = new Length(1000.0, LengthUnit.FEET)
                .add(new Length(500.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertEquals(new Length(18000.0, LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
        Length result = new Length(12.0, LengthUnit.INCHES)
                .add(new Length(12.0, LengthUnit.INCHES), Length.LengthUnit.YARDS);
        assertEquals(new Length(0.67, LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_MathematicalConsistency_SameAddDifferentTargets() {
        Length l1 = new Length(1.0,  LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length inFeet   = l1.add(l2, LengthUnit.FEET);
        Length inInches = l1.add(l2, LengthUnit.INCHES);
        assertTrue(inFeet.equals(inInches));
    }

    @Test
    public void testLengthUnitEnum_FeetConstant() {
        assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), 1e-6);
    }

    @Test
    public void testLengthUnitEnum_InchesConstant() {
        assertEquals(1.0 / 12.0, LengthUnit.INCHES.getConversionFactor(), 1e-6);
    }

    @Test
    public void testLengthUnitEnum_YardsConstant() {
        assertEquals(3.0, LengthUnit.YARDS.getConversionFactor(), 1e-6);
    }

    @Test
    public void testLengthUnitEnum_CentimetersConstant() {
        assertEquals(1.0 / 30.48, LengthUnit.CENTIMETERS.getConversionFactor(), 1e-6);
    }

    @Test
    public void testConvertToBaseUnit_FeetToFeet() {
        assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5.0), 1e-6);
    }

    @Test
    public void testConvertToBaseUnit_InchesToFeet() {
        assertEquals(1.0, LengthUnit.INCHES.convertToBaseUnit(12.0), 1e-6);
    }

    @Test
    public void testConvertToBaseUnit_YardsToFeet() {
        assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0), 1e-6);
    }

    @Test
    public void testConvertToBaseUnit_CentimetersToFeet() {
        assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), 1e-4);
    }

    @Test
    public void testConvertFromBaseUnit_FeetToFeet() {
        assertEquals(2.0, LengthUnit.FEET.convertFromBaseUnit(2.0), 1e-6);
    }

    @Test
    public void testConvertFromBaseUnit_FeetToInches() {
        assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(1.0), 1e-6);
    }

    @Test
    public void testConvertFromBaseUnit_FeetToYards() {
        assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3.0), 1e-6);
    }

    @Test
    public void testConvertFromBaseUnit_FeetToCentimeters() {
        assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0), 1e-4);
    }

    @Test
    public void testQuantityLengthRefactored_Equality() {
        assertTrue(new Length(1.0, LengthUnit.FEET).equals(new Length(12.0, LengthUnit.INCHES)));
    }

    @Test
    public void testQuantityLengthRefactored_ConvertTo() {
        assertTrue(new Length(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES)
                .equals(new Length(12.0, LengthUnit.INCHES)));
    }

    @Test
    public void testQuantityLengthRefactored_Add() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES));
        assertEquals(new Length(2.0, LengthUnit.FEET), result);
    }

    @Test
    public void testQuantityLengthRefactored_AddWithTargetUnit() {
        Length result = new Length(1.0, LengthUnit.FEET)
                .add(new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
        assertEquals(new Length(0.67, LengthUnit.YARDS), result);
    }

    @Test
    public void testQuantityLengthRefactored_NullUnit() {
        assertThrows(IllegalArgumentException.class, () -> new Length(1.0, null));
    }

    @Test
    public void testQuantityLengthRefactored_InvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> new Length(Double.NaN, LengthUnit.FEET));
    }

    @Test
    public void testRoundTripConversion_RefactoredDesign() {
        double original = 2.5;
        double toInches = LengthUnit.FEET.convertToBaseUnit(original);
        double backToFeet = LengthUnit.FEET.convertFromBaseUnit(toInches);
        assertEquals(original, backToFeet, 1e-6);
    }

    @Test
    public void testEquality_KilogramToKilogram_SameValue() {
        assertTrue(new Weight(1.0, WeightUnit.KILOGRAM).equals(new Weight(1.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testEquality_KilogramToKilogram_DifferentValue() {
        assertFalse(new Weight(1.0, WeightUnit.KILOGRAM).equals(new Weight(2.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testEquality_GramToGram_SameValue() {
        assertTrue(new Weight(500.0, WeightUnit.GRAM).equals(new Weight(500.0, WeightUnit.GRAM)));
    }

    @Test
    public void testEquality_PoundToPound_SameValue() {
        assertTrue(new Weight(2.0, WeightUnit.POUND).equals(new Weight(2.0, WeightUnit.POUND)));
    }

    @Test
    public void testEquality_KilogramToGram_EquivalentValue() {
        assertTrue(new Weight(1.0, WeightUnit.KILOGRAM).equals(new Weight(1000.0, WeightUnit.GRAM)));
    }

    @Test
    public void testEquality_GramToKilogram_EquivalentValue() {
        assertTrue(new Weight(1000.0, WeightUnit.GRAM).equals(new Weight(1.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testEquality_KilogramToPound_EquivalentValue() {
        assertTrue(new Weight(1.0, WeightUnit.KILOGRAM).equals(new Weight(2.20462, WeightUnit.POUND)));
    }

    @Test
    public void testEquality_GramToPound_EquivalentValue() {
        assertTrue(new Weight(453.592, WeightUnit.GRAM).equals(new Weight(1.0, WeightUnit.POUND)));
    }

    @Test
    public void testEquality_NullComparison() {
        assertFalse(new Weight(1.0, WeightUnit.KILOGRAM).equals(null));
    }

    @Test
    public void testEquality_SameReference() {
        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);
        assertTrue(w.equals(w));
    }

    @Test
    public void testEquality_NullUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () -> new Weight(1.0, null));
    }

    @Test
    public void testEquality_InvalidValue_Throws() {
        assertThrows(IllegalArgumentException.class, () -> new Weight(Double.NaN, WeightUnit.KILOGRAM));
    }

    @Test
    public void testEquality_ZeroValue() {
        assertTrue(new Weight(0.0, WeightUnit.KILOGRAM).equals(new Weight(0.0, WeightUnit.GRAM)));
    }

    @Test
    public void testEquality_NegativeWeight() {
        assertTrue(new Weight(-1.0, WeightUnit.KILOGRAM).equals(new Weight(-1000.0, WeightUnit.GRAM)));
    }

    @Test
    public void testEquality_LargeWeightValue() {
        assertTrue(new Weight(1000000.0, WeightUnit.GRAM).equals(new Weight(1000.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testEquality_SmallWeightValue() {
        assertTrue(new Weight(0.001, WeightUnit.KILOGRAM).equals(new Weight(1.0, WeightUnit.GRAM)));
    }


    @Test
    public void testConversion_KilogramToGram() {
        assertTrue(new Weight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM)
                .equals(new Weight(1000.0, WeightUnit.GRAM)));
    }

    @Test
    public void testConversion_PoundToKilogram() {
        assertTrue(new Weight(2.20462, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM)
                .equals(new Weight(1.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testConversion_KilogramToPound() {
        assertTrue(new Weight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND)
                .equals(new Weight(2.20, WeightUnit.POUND)));
    }

    @Test
    public void testConversion_SameUnit() {
        assertTrue(new Weight(5.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.KILOGRAM)
                .equals(new Weight(5.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testConversion_ZeroValue() {
        assertTrue(new Weight(0.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM)
                .equals(new Weight(0.0, WeightUnit.GRAM)));
    }

    @Test
    public void testConversion_NegativeValue() {
        assertTrue(new Weight(-1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM)
                .equals(new Weight(-1000.0, WeightUnit.GRAM)));
    }

    @Test
    public void testConversion_RoundTrip() {
        Weight original = new Weight(1.5, WeightUnit.KILOGRAM);
        Weight roundTrip = original.convertTo(WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);
        assertTrue(original.equals(roundTrip));
    }

    @Test
    public void testAddition_SameUnit_KilogramPlusKilogram() {
        Weight result = new Weight(1.0, WeightUnit.KILOGRAM).add(new Weight(2.0, WeightUnit.KILOGRAM));
        assertEquals(new Weight(3.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    public void testAddition_CrossUnit_KilogramPlusGram() {
        Weight result = new Weight(1.0, WeightUnit.KILOGRAM).add(new Weight(1000.0, WeightUnit.GRAM));
        assertEquals(new Weight(2.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    public void testAddition_CrossUnit_PoundPlusKilogram() {
        Weight result = new Weight(2.20462, WeightUnit.POUND).add(new Weight(1.0, WeightUnit.KILOGRAM));
        assertEquals(new Weight(4.41, WeightUnit.POUND), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Gram() {
        Weight result = new Weight(1.0, WeightUnit.KILOGRAM)
                .add(new Weight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);
        assertEquals(new Weight(2000.0, WeightUnit.GRAM), result);
    }

    @Test
    public void testAddition_WithZero() {
        Weight result = new Weight(5.0, WeightUnit.KILOGRAM).add(new Weight(0.0, WeightUnit.GRAM));
        assertEquals(new Weight(5.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    public void testAddition_NegativeValues() {
        Weight result = new Weight(5.0, WeightUnit.KILOGRAM).add(new Weight(-2000.0, WeightUnit.GRAM));
        assertEquals(new Weight(3.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    public void testAddition_NullOperand_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new Weight(1.0, WeightUnit.KILOGRAM).add(null));
    }

    @Test
    public void testAddition_LargeValues() {
        Weight result = new Weight(1e6, WeightUnit.KILOGRAM).add(new Weight(1e6, WeightUnit.KILOGRAM));
        assertEquals(new Weight(2e6, WeightUnit.KILOGRAM), result);
    }

    @Test
    public void testAddition_Commutativity() {
        Weight kg   = new Weight(1.0,    WeightUnit.KILOGRAM);
        Weight gram = new Weight(1000.0, WeightUnit.GRAM);
        Weight ab = kg.add(gram).convertTo(WeightUnit.GRAM);
        Weight ba = gram.add(kg).convertTo(WeightUnit.GRAM);
        assertTrue(ab.equals(ba));
    }


    @Test
    public void testEquality_WeightVsLength_Incompatible() {
        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);
        Length l = new Length(1.0, LengthUnit.FEET);
        assertFalse(w.equals(l)); // different classes → false
        assertFalse(l.equals(w)); // symmetric
    }
}