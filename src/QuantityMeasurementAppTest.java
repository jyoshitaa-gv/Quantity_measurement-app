package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test public void testFeetEquality() {
        assertTrue(new Length(1.0, Length.LengthUnit.FEET).equals(new Length(1.0, Length.LengthUnit.FEET)));
    }
    @Test public void testFeetInequality() {
        assertFalse(new Length(1.0, Length.LengthUnit.FEET).equals(new Length(2.0, Length.LengthUnit.FEET)));
    }
    @Test public void testInchesEquality() {
        assertTrue(new Length(1.0, Length.LengthUnit.INCHES).equals(new Length(1.0, Length.LengthUnit.INCHES)));
    }
    @Test public void testFeetInchesComparison() {
        assertTrue(new Length(1.0, Length.LengthUnit.FEET).equals(new Length(12.0, Length.LengthUnit.INCHES)));
    }
    @Test public void testNullComparison() {
        assertFalse(new Length(1.0, Length.LengthUnit.FEET).equals(null));
    }
    @Test public void testSameReference() {
        Length l = new Length(1.0, Length.LengthUnit.FEET);
        assertTrue(l.equals(l));
    }

    @Test public void testYardToFeet() {
        assertTrue(new Length(1.0, Length.LengthUnit.YARDS).equals(new Length(3.0, Length.LengthUnit.FEET)));
    }
    @Test public void testYardToInches() {
        assertTrue(new Length(1.0, Length.LengthUnit.YARDS).equals(new Length(36.0, Length.LengthUnit.INCHES)));
    }
    @Test public void testCentimetersToInches() {
        assertTrue(new Length(1.0, Length.LengthUnit.CENTIMETERS).equals(new Length(0.393701, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testConversion_FeetToInches() {
        assertEquals(12.0, Length.convert(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES), EPSILON);
    }

    @Test
    public void testConversion_InchesToFeet() {
        assertEquals(2.0, Length.convert(24.0, Length.LengthUnit.INCHES, Length.LengthUnit.FEET), EPSILON);
    }

    @Test
    public void testConversion_YardsToInches() {
        assertEquals(36.0, Length.convert(1.0, Length.LengthUnit.YARDS, Length.LengthUnit.INCHES), EPSILON);
    }

    @Test
    public void testConversion_InchesToYards() {
        assertEquals(2.0, Length.convert(72.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS), EPSILON);
    }

    @Test
    public void testConversion_CentimetersToInches() {
        assertEquals(1.0, Length.convert(2.54, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES), 0.01);
    }

    @Test
    public void testConversion_FeetToYards() {
        assertEquals(2.0, Length.convert(6.0, Length.LengthUnit.FEET, Length.LengthUnit.YARDS), EPSILON);
    }

    @Test
    public void testConversion_ZeroValue() {
        assertEquals(0.0, Length.convert(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES), EPSILON);
    }

    @Test
    public void testConversion_NegativeValue() {
        assertEquals(-12.0, Length.convert(-1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES), EPSILON);
    }

    @Test
    public void testConversion_SameUnit() {
        assertEquals(5.0, Length.convert(5.0, Length.LengthUnit.FEET, Length.LengthUnit.FEET), EPSILON);
    }

    @Test
    public void testConversion_RoundTrip_PreservesValue() {
        double original = 3.0;
        double converted = Length.convert(original, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
        double backToFeet = Length.convert(converted, Length.LengthUnit.INCHES, Length.LengthUnit.FEET);
        assertEquals(original, backToFeet, EPSILON);
    }

    @Test
    public void testConversion_InvalidUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                Length.convert(1.0, null, Length.LengthUnit.INCHES));
        assertThrows(IllegalArgumentException.class, () ->
                Length.convert(1.0, Length.LengthUnit.FEET, null));
    }

    @Test
    public void testConversion_NaNValue_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                Length.convert(Double.NaN, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
    }

    @Test
    public void testConversion_InfiniteValue_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                Length.convert(Double.POSITIVE_INFINITY, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
    }

    @Test
    public void testConversion_PrecisionTolerance() {
        double result = Length.convert(1.0, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES);
        assertEquals(0.393701, result, EPSILON);
    }

    @Test
    public void testConvertTo_InstanceMethod() {
        Length oneYard = new Length(1.0, Length.LengthUnit.YARDS);
        Length inInches = oneYard.convertTo(Length.LengthUnit.INCHES);
        assertEquals(36.0, Length.convert(1.0, Length.LengthUnit.YARDS, Length.LengthUnit.INCHES), EPSILON);
        assertTrue(inInches.equals(new Length(36.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testConstructor_NullUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new Length(1.0, null));
    }


    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
        Length result = new Length(1.0, Length.LengthUnit.FEET).add(new Length(2.0, Length.LengthUnit.FEET));
        assertEquals(new Length(3.0, Length.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_SameUnit_InchPlusInch() {
        Length result = new Length(6.0, Length.LengthUnit.INCHES).add(new Length(6.0, Length.LengthUnit.INCHES));
        assertEquals(new Length(12.0, Length.LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_CrossUnit_FeetPlusInches() {
        Length result = new Length(1.0, Length.LengthUnit.FEET).add(new Length(12.0, Length.LengthUnit.INCHES));
        assertEquals(new Length(2.0, Length.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_CrossUnit_InchPlusFeet() {
        Length result = new Length(12.0, Length.LengthUnit.INCHES).add(new Length(1.0, Length.LengthUnit.FEET));
        assertEquals(new Length(24.0, Length.LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_CrossUnit_YardPlusFeet() {
        Length result = new Length(1.0, Length.LengthUnit.YARDS).add(new Length(3.0, Length.LengthUnit.FEET));
        assertEquals(new Length(2.0, Length.LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {
        Length result = new Length(2.54, Length.LengthUnit.CENTIMETERS).add(new Length(1.0, Length.LengthUnit.INCHES));
        assertEquals(new Length(5.08, Length.LengthUnit.CENTIMETERS), result);
    }

    @Test
    public void testAddition_Commutativity() {
        Length a = new Length(1.0,  Length.LengthUnit.FEET);
        Length b = new Length(12.0, Length.LengthUnit.INCHES);
        Length ab = a.add(b).convertTo(Length.LengthUnit.INCHES);
        Length ba = b.add(a).convertTo(Length.LengthUnit.INCHES);
        assertTrue(ab.equals(ba));
    }

    @Test
    public void testAddition_WithZero() {
        Length result = new Length(5.0, Length.LengthUnit.FEET).add(new Length(0.0, Length.LengthUnit.INCHES));
        assertEquals(new Length(5.0, Length.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_NegativeValues() {
        Length result = new Length(5.0, Length.LengthUnit.FEET).add(new Length(-2.0, Length.LengthUnit.FEET));
        assertEquals(new Length(3.0, Length.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_NullSecondOperand_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new Length(1.0, Length.LengthUnit.FEET).add(null));
    }

    @Test
    public void testAddition_LargeValues() {
        Length result = new Length(1e6, Length.LengthUnit.FEET).add(new Length(1e6, Length.LengthUnit.FEET));
        assertEquals(new Length(2e6, Length.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_SmallValues() {
        Length result = new Length(0.001, Length.LengthUnit.FEET).add(new Length(0.002, Length.LengthUnit.FEET));
        assertEquals(new Length(0.003, Length.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {
        Length result = new Length(1.0, Length.LengthUnit.FEET)
                .add(new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.FEET);
        assertEquals(new Length(2.0, Length.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {
        Length result = new Length(1.0, Length.LengthUnit.FEET)
                .add(new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.INCHES);
        assertEquals(new Length(24.0, Length.LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Yards() {
        Length result = new Length(1.0, Length.LengthUnit.FEET)
                .add(new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS);
        assertEquals(new Length(0.67, Length.LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Centimeters() {
        Length result = new Length(1.0, Length.LengthUnit.INCHES)
                .add(new Length(1.0, Length.LengthUnit.INCHES), Length.LengthUnit.CENTIMETERS);
        assertEquals(new Length(5.08, Length.LengthUnit.CENTIMETERS), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
        Length result = new Length(2.0, Length.LengthUnit.YARDS)
                .add(new Length(3.0, Length.LengthUnit.FEET), Length.LengthUnit.YARDS);
        assertEquals(new Length(3.0, Length.LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
        Length result = new Length(2.0, Length.LengthUnit.YARDS)
                .add(new Length(3.0, Length.LengthUnit.FEET), Length.LengthUnit.FEET);
        assertEquals(new Length(9.0, Length.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Commutativity() {
        Length.LengthUnit target = Length.LengthUnit.YARDS;
        Length ab = new Length(1.0, Length.LengthUnit.FEET)
                .add(new Length(12.0, Length.LengthUnit.INCHES), target);
        Length ba = new Length(12.0, Length.LengthUnit.INCHES)
                .add(new Length(1.0, Length.LengthUnit.FEET), target);
        assertTrue(ab.equals(ba));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_WithZero() {
        Length result = new Length(5.0, Length.LengthUnit.FEET)
                .add(new Length(0.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS);
        assertEquals(new Length(1.67, Length.LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NegativeValues() {
        Length result = new Length(5.0, Length.LengthUnit.FEET)
                .add(new Length(-2.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
        assertEquals(new Length(36.0, Length.LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new Length(1.0, Length.LengthUnit.FEET)
                        .add(new Length(12.0, Length.LengthUnit.INCHES), null));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
        Length result = new Length(1000.0, Length.LengthUnit.FEET)
                .add(new Length(500.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
        assertEquals(new Length(18000.0, Length.LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
        Length result = new Length(12.0, Length.LengthUnit.INCHES)
                .add(new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS);
        assertEquals(new Length(0.67, Length.LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_MathematicalConsistency_SameAddDifferentTargets() {
        Length l1 = new Length(1.0,  Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
        Length inFeet   = l1.add(l2, Length.LengthUnit.FEET);
        Length inInches = l1.add(l2, Length.LengthUnit.INCHES);
        assertTrue(inFeet.equals(inInches));
    }
}