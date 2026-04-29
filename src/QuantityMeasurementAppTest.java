import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    private QuantityMeasurementApp.QuantityLength q(double v, QuantityMeasurementApp.LengthUnit u) {
        return new QuantityMeasurementApp.QuantityLength(v, u);
    }

    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(1.0, QuantityMeasurementApp.LengthUnit.FEET), q(2.0, QuantityMeasurementApp.LengthUnit.FEET));
        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testAddition_SameUnit_InchPlusInch() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(6.0, QuantityMeasurementApp.LengthUnit.INCH), q(6.0, QuantityMeasurementApp.LengthUnit.INCH));
        assertEquals(12.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.INCH, result.getUnit());
    }

    @Test
    public void testAddition_CrossUnit_FeetPlusInches() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(1.0, QuantityMeasurementApp.LengthUnit.FEET), q(12.0, QuantityMeasurementApp.LengthUnit.INCH));
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testAddition_CrossUnit_InchPlusFeet() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(12.0, QuantityMeasurementApp.LengthUnit.INCH), q(1.0, QuantityMeasurementApp.LengthUnit.FEET));
        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.INCH, result.getUnit());
    }

    @Test
    public void testAddition_CrossUnit_YardPlusFeet() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(1.0, QuantityMeasurementApp.LengthUnit.YARD), q(3.0, QuantityMeasurementApp.LengthUnit.FEET));
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.YARD, result.getUnit());
    }

    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(2.54, QuantityMeasurementApp.LengthUnit.CENTIMETER), q(1.0, QuantityMeasurementApp.LengthUnit.INCH));
        assertEquals(5.08, result.getValue(), 0.01);
        assertEquals(QuantityMeasurementApp.LengthUnit.CENTIMETER, result.getUnit());
    }

    @Test
    public void testAddition_Commutativity() {
        QuantityMeasurementApp.QuantityLength r1 = QuantityMeasurementApp.add(q(1.0, QuantityMeasurementApp.LengthUnit.FEET), q(12.0, QuantityMeasurementApp.LengthUnit.INCH));
        QuantityMeasurementApp.QuantityLength r2 = QuantityMeasurementApp.add(q(12.0, QuantityMeasurementApp.LengthUnit.INCH), q(1.0, QuantityMeasurementApp.LengthUnit.FEET));
        assertEquals(r1.getValue(), r2.convertTo(QuantityMeasurementApp.LengthUnit.FEET).getValue(), EPSILON);
    }

    @Test
    public void testAddition_WithZero() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(5.0, QuantityMeasurementApp.LengthUnit.FEET), q(0.0, QuantityMeasurementApp.LengthUnit.INCH));
        assertEquals(5.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testAddition_NegativeValues() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(5.0, QuantityMeasurementApp.LengthUnit.FEET), q(-2.0, QuantityMeasurementApp.LengthUnit.FEET));
        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    public void testAddition_NullSecondOperand() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityMeasurementApp.add(q(1.0, QuantityMeasurementApp.LengthUnit.FEET), null));
    }

    @Test
    public void testAddition_LargeValues() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(1e6, QuantityMeasurementApp.LengthUnit.FEET), q(1e6, QuantityMeasurementApp.LengthUnit.FEET));
        assertEquals(2e6, result.getValue(), EPSILON);
    }

    @Test
    public void testAddition_SmallValues() {
        QuantityMeasurementApp.QuantityLength result = QuantityMeasurementApp.add(q(0.001, QuantityMeasurementApp.LengthUnit.FEET), q(0.002, QuantityMeasurementApp.LengthUnit.FEET));
        assertEquals(0.003, result.getValue(), EPSILON);
    }
}