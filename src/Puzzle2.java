import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLConnection;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Puzzle2 {
    private static final java.lang.String TEST_URL_2 = "https://i18n-challenges.helixsoft.nl/puzzleinput/2";
    private static final java.lang.String TEST_DATA_2 = "2019-06-05T08:15:00-04:00\n"+
"2019-06-05T14:15:00+02:00\n"+
"2019-06-05T17:45:00+05:30\n"+
"2019-06-05T05:15:00-07:00\n"+
"2011-02-01T09:15:00-03:00\n"+
"2011-02-01T09:15:00-05:00";
    public static void main(String[] args) throws Exception {
        java.net.URL url = new java.net.URL(TEST_URL_2);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
       //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_2));
        Map<Long,Integer> result = new HashMap<>();
        String line;
        int counter = 0;
        while ((line=bufferedReader.readLine()) != null) {
            int ll1 = line.length();
            line = line.replaceAll("<[^>]+>","").trim();
            int ll2 = line.length();
            if (ll1 != ll2) {System.out.println("💚 HTML code found 💚");}

            if (line.length() > 0) {
                OffsetDateTime timestamp = OffsetDateTime.parse(line);

                long epochMinute = timestamp.toEpochSecond()/60;
                result.put(epochMinute, result.getOrDefault(epochMinute, 0) + 1);

                var utc = timestamp.atZoneSameInstant(ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

                if (result.get(epochMinute) >= 2) {
                    System.out.println("" + timestamp + " ➡ " + utc + " 〰 " + epochMinute + " 🎲 " + result.get(epochMinute) + " " + (result.get(epochMinute) >= 4 ? "⬅" : " "));
                }
            }
        }

    }
}
