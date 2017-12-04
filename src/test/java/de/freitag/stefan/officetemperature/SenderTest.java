package de.freitag.stefan.officetemperature;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link Sender}
 * @author Stefan Freitag (stefan.freitag@udo.edu)
 */
public final class SenderTest {

    @Test
    public void createURLParametersSetsDebugWithExpectedValues(){
        final String parametersDbgOn = Sender.createURLParameters(1, null, null, null, 0.0f);
        assertTrue(parametersDbgOn.contains("dbg=1"));

        final String parametersDbgOff = Sender.createURLParameters(0, null, null, null, 0.0f);
        assertTrue(parametersDbgOff.contains("dbg=0"));
    }

    @Test
    public void createURLParametersSetsHardwareWithExpectedValues(){
        final String parameters = Sender.createURLParameters(0, Hardware.SBC, null, null, 0.0f);
        assertTrue(parameters.contains("type=sbc"));
    }

    @Test
    public void createURLParametersSetsCityWithExpectedValues(){
        final Location location = new Location("DE", "Dortmund", "44135", null);
        final String parameters = Sender.createURLParameters(0, null, location, null, 0.0f);
        assertTrue(parameters.contains("city=Dortmund"));
    }

    @Test
    public void createURLParametersSetsCountryWithExpectedValues(){
        final Location location = new Location("DE", "Dortmund", "44135", null);
        final String parameters = Sender.createURLParameters(0, null, location, null, 0.0f);
        assertTrue(parameters.contains("country=DE"));
    }

    @Test
    public void createURLParametersSetsZipWithExpectedValues(){
        final Location location = new Location("DE", "Dortmund", "44135", null);
        final String parameters = Sender.createURLParameters(0, null, location, null, 0.0f);
        assertTrue(parameters.contains("zip=44135"));
    }



    @Test
    public void createURLParametersSetsLatLonWithExpectedValues(){
        final Coordinate coordinate = new Coordinate(51.51, 7.51);
        final Location location = new Location("DE", "Dortmund", "44135", coordinate);
        final String parameters = Sender.createURLParameters(0, null, location, null, 0.0f);
        assertTrue(parameters.contains("lon=51.51"));
        assertTrue(parameters.contains("lat=7.51"));
    }
    @Test
    public void createURLParametersSetsTokenWithExpectedValues(){
        final String token = "1234567890";
        final String parameters = Sender.createURLParameters(0, null, null, token, 0.0f);
        assertTrue(parameters.contains("token="+token));
    }
}