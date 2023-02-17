import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle5 {
    private static final String SHIT = "💩";
    private static final String TEST_URL_5 = "https://i18n-challenges.helixsoft.nl/puzzleinput/5";
    private static final String TEST_DATA_5 =
            " ⚘   ⚘ \n" +
            "  ⸫   ⸫\n" +
            "🌲   💩  \n" +
            "     ⸫⸫\n" +
            " 🐇    💩\n" +
            "⸫    ⸫ \n" +
            "⚘🌲 ⸫  🌲\n" +
            "⸫    🐕 \n" +
            "  ⚘  ⸫ \n" +
            "⚘⸫⸫   ⸫\n" +
            "  ⚘⸫   \n" +
            " 💩  ⸫  \n" +
            "     ⸫⸫";
    public static void main(String[] args) throws Exception {
        URL url = new URL(TEST_URL_5);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_5));
        String line;
        int lineNo = 0;
        int shitCount = 0;
        int searchedCP = SHIT.codePointAt(0);
        System.out.println("Searching for code point "+searchedCP);
        int xPosition = 0;
        while ((line=bufferedReader.readLine()) != null) {
            lineNo++;
            int codePoints = line.codePointCount(0,line.length());
            int currentCP = line.codePointAt(xPosition);
            currentCP = line.codePoints().skip(xPosition).findAny().getAsInt();
            boolean inShit = searchedCP == currentCP;
            if (inShit) {shitCount++;}
            System.out.println(
                    "%04d %s %s %d %s %d".formatted(lineNo, ".".repeat(xPosition)+"x"+".".repeat(codePoints-xPosition),
                            line, currentCP, inShit?"◀":" ", shitCount)
            );
            xPosition = (xPosition+2) % codePoints;
        }
        System.out.println("Total count : "+shitCount);
    }


}