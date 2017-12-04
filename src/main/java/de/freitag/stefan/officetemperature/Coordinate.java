package de.freitag.stefan.officetemperature;

import java.util.Objects;

/**
 * Eine geographische Koordinate bestehend aus Laengen- und Breitengrad.
 */
final class Coordinate {

    /**
     * Positiver Maximalwert fuer den Breitengrad.
     */
    private static final double MAXIMUM_LATITUDE=90;
    /**
     * Negativer Maximalwert fuer den Breitengrad.
     */
    private static final double MINIMUM_LATITUDE=-90;

    /**
     * Positiver Maximalwert fuer den Laengengrad.
     */
    private static final double MAXIMUM_LONGITUDE=180;
    /**
     * Negativer Maximalwert fuer den Laengengrad.
     */
    private static final double MINIMUM_LONGITUDE=-180;

    private final double longitude;

    private final double latitude;

    /**
     * Create a new {@code Coordinate}.
     * @param longitude A valid longitude value. Allowed range [-180, 180].
     * @param latitude A valid latitude value. Allowed range [-90, 90].
     */
    Coordinate(final double longitude, final double latitude) {
        if (latitude < MINIMUM_LATITUDE || latitude > MAXIMUM_LATITUDE) {
            throw new IllegalArgumentException("Latitude value is out of range: "+ latitude);
        }

        if (longitude < MINIMUM_LONGITUDE || longitude > MAXIMUM_LONGITUDE) {
            throw new IllegalArgumentException("Longitude value is out of range: " + longitude);
        }

        this.longitude = longitude;
        this.latitude = latitude;
    }


    double getLongitude() {
        return this.longitude;
    }


    double getLatitude() {
        return this.latitude;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Coordinate that = (Coordinate) o;
        return Double.compare(that.getLongitude(), getLongitude()) == 0 &&
                Double.compare(that.getLatitude(), getLatitude()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLongitude(), getLatitude());
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
