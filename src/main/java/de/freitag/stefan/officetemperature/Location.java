package de.freitag.stefan.officetemperature;

import java.util.Locale;
import java.util.Objects;

/**
 * Beschreibt eine Ort identifiziert durch
 * <ul>
 *     <li>Country</li>
 *     <li>City</li>
 *     <li>Zip code</li>
 *     <li>GPS coordinate</li>
 * </ul>
 */
final class Location {
    /**
     * 2 letter country code, e.g. DE for Germany.
     */
    private final String country;
    /**
     * Name of the city.
     */
    private final String city;

    /**
     * Zip code.
     */
    private final String zipCode;

    /**
     *
     */
    private final Coordinate coordinate;

    /**
     * Create a new {@code Location}.
     *
     * @param country 2 letter country code. Can be null.
     * @param city A non-empty city name. Can be null.
     * @param zipCode Zip code. Can be null.
     * @param coordinate A GPS coordinate. Can be null.
     */
    Location(final String country, final String city, final String zipCode, final Coordinate coordinate) {
        if (country !=null && !isValidCountryCode(country)) {
            throw new IllegalArgumentException("Invalid country code " + country + ".");
        }
        this.country = country;

        if (city!=null && city.isEmpty()) {
            throw new IllegalArgumentException("Found empty city.");
        }
        this.city = city;
        this.zipCode = zipCode;
        this.coordinate = coordinate;
    }

    String getCountry() {
        return country;
    }

    String getCity() {
        return city;
    }

    String getZipCode() {
        return zipCode;
    }

    Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        final Location location = (Location) o;
        return Objects.equals(getCountry(), location.getCountry()) &&
                Objects.equals(getCity(), location.getCity()) &&
                Objects.equals(getZipCode(), location.getZipCode()) &&
                Objects.equals(coordinate, location.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry(), getCity(), getZipCode(), coordinate);
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", coordinate=" + coordinate +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

    /**
     * Prueft ob {@code code} ein gueltiger 2-Buchstaben Laendercode ist.
     * @param code Zu pruefende non-null Zeichenkette.
     * @return {@code true} falls {@code code} valide ist, andernfalls wird
     * {@code false} zurueckgegeben.
     */
    static boolean isValidCountryCode(final String code) {
        if (code==null) {
            throw new IllegalArgumentException("Country code is null");
        }
        final String[] locales = Locale.getISOCountries();
        for (final String countryCode : locales) {
            if (code.equals(countryCode)) {
                return true;
            }

        }
        return false;
    }
}
