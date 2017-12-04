package de.freitag.stefan.officetemperature;

/**
 * Zur Messung benutzte Plattform.
 */
enum Hardware {
    /**
     * Single Board Computer.
     * Raspberry Pi und vergleichbare Bastelrechner wie der Banana Pi.
     */
    @SuppressWarnings("unused")
    SBC,
    /**
     * Arduino-Platinen und vergleichbare Mikrocontroller-Systeme mit separatem Wifi- oder Ethernetmodul.
     */
    @SuppressWarnings("unused")
    ARD,
    /**
     * Nackte Microcontroller mit integriertem Wlan-Modul, zum Beispiel ESP8266 oder Particle Photon.
     */
    @SuppressWarnings("unused")
    WIFIMUC,
    /**
     * Keines der anderen genannten Systeme.
     */
    @SuppressWarnings("unused")
    OTHER;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
