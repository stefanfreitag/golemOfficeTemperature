package de.freitag.stefan.officetemperature;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link Location}.
 */
public final class LocationTest {

    @Test(expected = IllegalArgumentException.class)
    public void isValidCountryCodeWithNullThrowsIllegalArgumentException(){
        Location.isValidCountryCode(null);
    }

    @Test
    public void isValidCountryCodeReturnsExpectedResult(){
        assertTrue(Location.isValidCountryCode("DE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidCountryCodeThrowsIllegalArgumentException(){
        new Location("XX", "Dortmund", "44137", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyCityThrowsIllegalArgumentException(){
        new Location("DE", "", "44137", null);
    }

    @Test
    public void getCityReturnsExpectedValue(){
        final Location location = new Location("DE", "Dortmund", "44137", null);
        assertTrue("Dortmund".equals(location.getCity()));
    }

    @Test
    public void getCountryReturnsExpectedValue(){
        final Location location = new Location("DE", "Dortmund", "44137", null);
        assertTrue("DE".equals(location.getCountry()));
    }

    @Test
    public void getZipCodeReturnsExpectedValue(){
        final Location location = new Location("DE", "Dortmund", "44137", null);
        assertTrue("44137".equals(location.getZipCode()));
    }

    @Test
    public void testEquals_Symmetric() {
        final Coordinate coordinate_1 = new Coordinate(7.46, 51.51);
        final Location location_1 = new Location("DE", "Dortmund", "44137", coordinate_1);

        final Coordinate coordinate_2 = new Coordinate(7.46, 51.51);
        final Location location_2 = new Location("DE", "Dortmund", "44137", coordinate_2);
        assertTrue(location_1.equals(location_2) && location_2.equals(location_1));
        assertTrue(location_1.hashCode() == location_2.hashCode());
    }
}