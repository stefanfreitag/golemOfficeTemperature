package de.freitag.stefan.officetemperature;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testklasse fuer {@link Coordinate}
 */
public final class CoordinateTest {

    @Test(expected =IllegalArgumentException.class)
    public void constructorWithTooLowLongitudeThrowsIllegalArgumentException(){
        new Coordinate(-181.0,0.0);
    }

    @Test(expected =IllegalArgumentException.class)
    public void constructorWithTooHighLongitudeThrowsIllegalArgumentException(){
        new Coordinate(181.0,0.0);
    }

    @Test(expected =IllegalArgumentException.class)
    public void constructorWithTooLowLatitudeThrowsIllegalArgumentException(){
        new Coordinate(0.0,-91.0);
    }

    @Test(expected =IllegalArgumentException.class)
    public void constructorWithTooHighLatitudeThrowsIllegalArgumentException(){
        new Coordinate(0.0,91.0);
    }

    @Test
    public void getLatitudeReturnsExceptedValue(){
        final double longitude = 20.0;
        final double latitude = -31.0;
        final Coordinate coordinate = new Coordinate(longitude, latitude);
        assertEquals(latitude, coordinate.getLatitude(), 0.0000001);
    }

    @Test
    public void getLongitudeReturnsExceptedValue(){
        final double longitude = 20.0;
        final double latitude = -31.0;
        final Coordinate coordinate = new Coordinate(longitude, latitude);
        assertEquals(longitude, coordinate.getLongitude(), 0.0000001);
    }

    @Test
    public void equalsOnSameObjectReturnsTrue() {
        final Coordinate coordinate_1 = new Coordinate(7.46, 51.51);
        assertTrue(coordinate_1.equals(coordinate_1));
    }

    @Test
    public void equalsOnNullReturnsFalse() {
        final Coordinate coordinate_1 = new Coordinate(7.46, 51.51);
        assertFalse(coordinate_1.equals(null));
    }

    @Test
    public void testEquals_Symmetric() {
        final Coordinate coordinate_1 = new Coordinate(7.46, 51.51);
        final Coordinate coordinate_2 = new Coordinate(7.46, 51.51);
        assertTrue(coordinate_1.equals(coordinate_2) && coordinate_2.equals(coordinate_1));
        assertTrue(coordinate_1.hashCode() == coordinate_2.hashCode());
    }
}