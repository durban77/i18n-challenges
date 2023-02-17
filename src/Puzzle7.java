import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.*;
import java.time.zone.ZoneRules;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle7 {
    private static final String TEST_URL_7 = "https://i18n-challenges.helixsoft.nl/puzzleinput/7";
    private static final String TEST_DATA_7 =
            "2012-11-05T09:39:00.000-04:00   969 3358\n" +
            "2012-05-27T17:38:00.000-04:00   2771    246\n" +
            "2001-01-15T22:27:00.000-03:00   2186    2222\n" +
            "2017-05-15T07:23:00.000-04:00   2206    4169\n" +
            "2005-09-02T06:15:00.000-04:00   1764    794\n" +
            "2008-03-23T05:02:00.000-03:00   1139    491\n" +
            "2016-03-11T00:31:00.000-04:00   4175    763\n" +
            "2015-08-14T12:40:00.000-03:00   3697    568\n" +
            "2013-11-03T07:56:00.000-04:00   402 3366\n" +
            "2010-04-16T09:32:00.000-04:00   3344    2605";
    private static final Pattern LINE_PATTERN = Pattern.compile("^([^ ]+)[\\s]+([0-9]+)[\\s]+([0-9]+)$");
    private static final ZoneId ZONE_HALIFAX = ZoneId.of("America/Halifax");
    private static final ZoneId ZONE_SANTIAGO = ZoneId.of("America/Santiago");
    public static void main(String[] args) throws Exception {
        URL url = new URL(TEST_URL_7);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_7));
        String line;
        int lineNo = 0;
        int totalHour = 0;
        while ((line=bufferedReader.readLine()) != null) {
            lineNo++;
            OffsetDateTime timestampWithOffset = null;
            ZonedDateTime timestampWithZone = null;
            int goodMinutes = -1;
            int badMinutes = -1;
            ZoneId zone = null;
            int localHour = -1;
            Matcher lineMatcher = LINE_PATTERN.matcher(line);
            if (lineMatcher.matches()) {
                timestampWithOffset = OffsetDateTime.parse(lineMatcher.group(1));
                Instant instant = timestampWithOffset.toInstant();
                goodMinutes = Integer.parseInt(lineMatcher.group(2));
                badMinutes = Integer.parseInt(lineMatcher.group(3));
                ZoneOffset offset = timestampWithOffset.getOffset();
                //America/Halifax, which is GMT-4 (~ Nov-Mar) or GMT-3 (~ Apr-Oct)
                ZoneOffset halifaxOffset = ZONE_HALIFAX.getRules().getOffset(instant);
                if (halifaxOffset.equals(offset)) {
                    zone = ZONE_HALIFAX;
                }
                //America/Santiago[^1], which is GMT-3 (~ Sep-Mar) or GMT-4 (~ Apr-Aug)
                ZoneOffset santiagoOffset = ZONE_SANTIAGO.getRules().getOffset(instant);
                if (santiagoOffset.equals(offset)) {
                    zone = ZONE_SANTIAGO;
                }
                instant = instant.minusSeconds(60 * badMinutes);
                instant = instant.plusSeconds(60 * goodMinutes);
                timestampWithZone = instant.atZone(zone);
                localHour = timestampWithZone.getHour();
                totalHour += lineNo*localHour;
            }
            System.out.println("%4d. %s +%4d -%4d %-16s %s %02dH %10d".formatted(
                    lineNo, timestampWithOffset, goodMinutes, badMinutes, zone, timestampWithZone != null ? timestampWithZone.toOffsetDateTime() : "", localHour, totalHour
            ));
        }
    }


}