package de.freitag.stefan.officetemperature;

import com.pi4j.io.i2c.I2CBus;
import de.freitag.stefan.sht21.SHT21;
import de.freitag.stefan.sht21.SHT21Impl;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import de.freitag.stefan.sht21.model.UnsupportedMeasureTypeException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public final class Sender {

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOG = LogManager.getLogger(Sender.class.getCanonicalName());

    /**
     * Base URL to send data to.
     */
    private static final String BASE_URL = "http://www.golem.de/projekte/ot/temp.php?";

    /**
     * Debug Mode.
     * <ul>
     * <li>{@code 1}: Enabled</li>
     * <li>{@code 0}: Disabled</li>
     * </ul>
     */
    @Option(name = "-debug", usage = "Debug Mode (1 enabled, 0 disabled)", required = true)
    private int debug;

    @Option(name = "-type", usage = "Hardware type")
    private Hardware hardware;

    @Option(name = "-longitude", depends = "-latitude", usage = "-180 to 180")
    private Double longitude;

    @Option(name = "-latitude", depends = "-longitude", usage = "-90 to 90")
    private Double latitude;

    /**
     * Land der Messung als zweistelliger Landescode (z.B. DE fuer Deutschland)
     */
    @Option(name = "-country", usage = "Zweistelliger Landescode")
    private String country;
    /**
     * Stadt-/Gemeindename.
     */
    @Option(name = "-city", usage = "Stadt-/Gemeindename")
    private String city;

    /**
     * Zip code.
     */
    @Option(name = "-zip", usage = "Postleitzahl")
    private String zip;
    /**
     * Eindeutiger Token zur Geräteerkennung
     */
    @Option(name = "-token", usage = "Eindeutiger Token zur Geraeteerkennung")
    private String token;

    /**
     * Submission interval in minutes.
     */
    @Option(name = "-interval", usage = "Sendeintervall (Minuten)", required = true)
    private int interval;


    /**
     * The sensor device.
     */
    private final SHT21 sht21;

    /**
     * Create a new {@link Sender}.
     */

    private Sender(){
        this.sht21 =  SHT21Impl.create(I2CBus.BUS_1, 0x40);
    }

    /**
     * Entry point of the application.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        final Sender sender = new Sender();
        sender.doMain(args);
    }

    private void doMain(final String[] args) {
        final CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);

            if (interval<1) {
                throw new CmdLineException(parser, "Interval must be equal to or greater than 1.", null);
            }
            Coordinate coordinate = null;
            if (longitude != null && latitude != null) {
                coordinate = new Coordinate(longitude, latitude);
            }
            final Location location = new Location(country, city, zip,coordinate);

            while (true) {
                try {
                    try {
                        final Measurement measurement = sht21.measurePoll(MeasureType.TEMPERATURE);
                        final float temperature = measurement.getValue();
                        if (temperature>-20 && temperature<100) {
                            final String parameters = createURLParameters(debug, hardware, location, token, temperature);
                            final String url = BASE_URL + parameters;
                            send(url);
                        } else {
                            LOG.warn("Temperature value of " + temperature + " exceeds allowed range of -20 to 100 deg C. Skipping.");
                        }

                    } catch (final UnsupportedMeasureTypeException exception) {
                        LOG.error(exception.getMessage(), exception);
                    }
                    LOG.info("Next transmission expected at " + LocalDateTime.now().plusMinutes(interval));
                    Thread.sleep(TimeUnit.MINUTES.toMillis(interval));
                } catch (final InterruptedException exception) {
                        Thread.currentThread().interrupt();
                }
            }

        } catch (final CmdLineException exception) {
            System.err.println(exception.getMessage());
            parser.printUsage(System.err);
        }

    }

    private boolean send(final String url) {
        assert url != null;

        URL obj;
        try {
            LOG.info("URL: " + url);
            obj = new URL(url);
        } catch (final MalformedURLException exception) {
            LOG.error(exception.getMessage(), exception);
            return false;
        }

        HttpURLConnection con;
        try {
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            final int responseCode = con.getResponseCode();
            LOG.info("Response Code : " + responseCode);

            final BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),"UTF-8"));
            String inputLine;
            final StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            LOG.info(response.toString());
        } catch (final IOException exception) {
            LOG.error(exception.getMessage(), exception);
            return false;
        }
        return true;
    }

    /**
     * Create the parameter list to send.
     * @param debug Debug mode enabled(0), disabled (1).
     * @param hardware Hardware device used to retrieve measurement
     * @param location The location the measurement took place.
     * @param token A unique token identifying the hardware device.
     * @param temperature The measured temperature in degree celsius.
     * @return The parameter string that can be attached to the {@link #BASE_URL}.
     */
    static String createURLParameters(final int debug, final Hardware hardware, final Location location, final String token, final double temperature) {

        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("dbg", String.valueOf(debug)));
        if (hardware!=null) {
            params.add(new BasicNameValuePair("type", hardware.toString()));
        }
        if (location != null) {
            if (location.getCity() != null) {
                params.add(new BasicNameValuePair("city", location.getCity()));
            }
            if (location.getZipCode() != null) {
                params.add(new BasicNameValuePair("zip", location.getZipCode()));
            }
            if (location.getCountry() != null) {
                params.add(new BasicNameValuePair("country", location.getCountry()));
            }
            final Coordinate coordinate = location.getCoordinate();
            if (coordinate!= null) {
                params.add(new BasicNameValuePair("lat", String.valueOf(coordinate.getLatitude())));
                params.add(new BasicNameValuePair("lon", String.valueOf(coordinate.getLongitude())));
            }
        }
        if (token!=null) {
            params.add(new BasicNameValuePair("token", token));
        }
        params.add(new BasicNameValuePair("temp", String.valueOf(temperature)));
        return  URLEncodedUtils.format(params, "utf-8");

    }
}
