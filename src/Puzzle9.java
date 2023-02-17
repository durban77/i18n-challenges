import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle9 {
    private static final String TEST_URL_9 = "https://i18n-challenges.helixsoft.nl/puzzleinput/9";
    private static final String TEST_DATA_9 =
            "16-05-18: Margot, Frank\n" +
            "02-17-04: Peter, Elise\n" +
            "06-02-29: Peter, Margot\n" +
            "31-09-11: Elise, Frank\n" +
            "09-11-01: Peter, Frank, Elise\n" +
            "11-09-01: Margot, Frank";
    private static final Pattern LINE_PATTERN = Pattern.compile(
            "^([0-9]{2}-[0-9]{2}-[0-9]{2}): ([A-Za-z, ]+)$"
    );
    public static void main(String[] args) throws Exception {
        URL url = new URL(TEST_URL_9);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_9));
        String line;
        int lineNo = 0;
        Map<String, FMTS> stats = new HashMap<>();
        Map<String, Set<String>> dates = new HashMap<>();
        while ((line=bufferedReader.readLine()) != null) {
            lineNo++;
            Matcher lineMatcher = LINE_PATTERN.matcher(line);
            if (!lineMatcher.matches()) {
                System.out.println("Uexpected line "+line);
                break;
            }
            for (String person : lineMatcher.group(2).split(", ")) {
                stats.putIfAbsent(person, new FMTS());
                stats.get(person).keepAcceptables(lineMatcher.group(1));
                dates.putIfAbsent(person, new HashSet<>());
                dates.get(person).add(lineMatcher.group(1));
            }
        }
        List<String> persons = new LinkedList<>();
        for (String person : stats.keySet()) {
            System.out.println("%s : %s".formatted(person, stats.get(person)));
            SimpleDateFormat formatter = stats.get(person).first().getFormatter();
            for (String date : dates.get(person)) {
                Date logDate = formatter.parse(date);
                if (logDate.getYear() == 101 && logDate.getMonth() == 8 && logDate.getDate() == 11) {
                    System.out.println("-> "+person+" at "+date+" using "+formatter.toPattern());
                    persons.add(person);
                }
            }
        }
        Collections.sort(persons);
        System.out.println(String.join(" ",persons));
    }
    private enum FMT {
        DMY("dd-MM-yy"),MDY("MM-dd-yy"),YMD("yy-MM-dd"),YDM("yy-dd-MM");
        public String dateFormat;
        private FMT(String fmt) {
            dateFormat = fmt;
        }
        public SimpleDateFormat getFormatter() {
            return new SimpleDateFormat(dateFormat);
        }

        public boolean isAcceptable(String dateString) {
            SimpleDateFormat formatter = getFormatter();
            try {
                var date = formatter.parse(dateString);
                var reformatted = formatter.format(date);
                return dateString.equals(reformatted);
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public static class FMTS {
        private List<FMT> holder = new LinkedList<>(Arrays.asList(FMT.values()));

        public FMTS keepAcceptables(String dateString) {
            Iterator<FMT> i = holder.iterator();
            while (i.hasNext()) {
                if (!i.next().isAcceptable(dateString)) {
                    i.remove();
                }
            }
            return this;
        }

        public FMT first() {
            return holder.get(0);
        }

        @Override
        public String toString() {
            return "FMTS{" +
                    "holder=" + holder +
                    '}';
        }
    }

}