import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle4 {
    private static final String TEST_URL_4 = "https://i18n-challenges.helixsoft.nl/puzzleinput/4";
    private static final String TEST_DATA_4 =
            "Departure: Europe/London                  Mar 04, 2020, 10:00\n" +
            "Arrival:   Europe/Paris                   Mar 04, 2020, 11:59\n" +
            "\n" +
            "Departure: Europe/Paris                   Mar 05, 2020, 10:42\n" +
            "Arrival:   Australia/Adelaide             Mar 06, 2020, 16:09\n" +
            "\n" +
            "Departure: Australia/Adelaide             Mar 06, 2020, 19:54\n" +
            "Arrival:   America/Argentina/Buenos_Aires Mar 06, 2020, 19:10\n" +
            "\n" +
            "Departure: America/Argentina/Buenos_Aires Mar 07, 2020, 06:06\n" +
            "Arrival:   America/Toronto                Mar 07, 2020, 14:43\n" +
            "\n" +
            "Departure: America/Toronto                Mar 08, 2020, 04:48\n" +
            "Arrival:   Europe/London                  Mar 08, 2020, 16:52";
    private static final Pattern DEPART_OR_ARRIVE_PATTERN = Pattern.compile("^(Departure:|Arrival:)[ ]+([^ ]+)[ ]+(.+)$");
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MMM dd, yyyy, HH:mm", new Locale("en","US"));
    public static void main(String[] args) throws Exception {
        URL url = new URL(TEST_URL_4);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_4));
        OffsetDateTime departAt = null;
        OffsetDateTime arriveAt = null;
        String line;
        int counter = 0;
        boolean end = false;
        do {
            end = (line=bufferedReader.readLine()) == null;
            if (line == null) {line="";}
            int ll1 = line.length();
            line = line.replaceAll("<[^>]+>","").trim();
            int ll2 = line.length();
            if (ll1 != ll2) {System.out.println("💚 HTML code found 💚");}

            Matcher matcher = DEPART_OR_ARRIVE_PATTERN.matcher(line);
            if (matcher.matches()) {
                String zone = matcher.group(2);
                String minute = matcher.group(3);
                if ("Departure:".equals(matcher.group(1))) {
                    LocalDateTime localDateTime = LocalDateTime.parse(minute, DTF);
                    departAt = OffsetDateTime.of(localDateTime, ZoneId.of(zone).getRules().getOffset(localDateTime));
                } else if ("Arrival:".equals(matcher.group(1))) {
                    LocalDateTime localDateTime = LocalDateTime.parse(minute, DTF);
                    arriveAt = OffsetDateTime.of(localDateTime, ZoneId.of(zone).getRules().getOffset(localDateTime));
                }
            } else if (departAt != null && arriveAt != null) {
                ZonedDateTime departUtc = departAt.atZoneSameInstant(ZoneOffset.UTC);
                ZonedDateTime arriveUtc = arriveAt.atZoneSameInstant(ZoneOffset.UTC);
                long diff = ChronoUnit.MINUTES.between(departUtc, arriveUtc);
                counter += diff;
                System.out.println("%s➡%s %s➡%s %4d %6d".formatted(departAt, departUtc, arriveAt, arriveUtc, diff, counter));
                departAt = null;
                arriveAt = null;
            }
        } while (!end);
    }


}