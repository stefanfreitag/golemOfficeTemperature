package de.freitag.stefan.officetemperature;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link Hardware}
 */
public class HardwareTest {

    @Test
    public void toStringReturnsExpectedValue(){
        assertTrue("ard".equals(Hardware.ARD.toString()));
    }
}