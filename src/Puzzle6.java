import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle6 {
    private static final String TEST_URL_6 = "https://i18n-challenges.helixsoft.nl/puzzleinput/6";
    private static final String TEST_DATA_6 =
            "geléet\n" +
                    "träffs\n" +
                    "religiÃ«n\n" +
                    "tancées\n" +
                    "kÃ¼rst\n" +
                    "roekoeÃ«n\n" +
                    "skälen\n" +
                    "böige\n" +
                    "fÃ¤gnar\n" +
                    "dardÃ©es\n" +
                    "amènent\n" +
                    "orquestrÃ¡\n" +
                    "imputarão\n" +
                    "molières\n" +
                    "pugilarÃ\u0083Â£o\n" +
                    "azeitámos\n" +
                    "dagcrème\n" +
                    "zÃ¶ger\n" +
                    "ondulât\n" +
                    "blÃ¶kt\n" +
                    "\n" +
                    "   ...d...\n" +
                    "    ..e.....\n" +
                    "     .l...\n" +
                    "  ....f.\n" +
                    "......t..";
    public static void main(String[] args) throws Exception {
        URL url = new URL(TEST_URL_6);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_6));
        String line;
        int lineNo = 0;
        List<String> words = new LinkedList<>();
        List<String> puzzle = new LinkedList<>();
        while ((line=bufferedReader.readLine()) != null) {
            lineNo++;
            if (line.isEmpty()) {break;}
            boolean a3rd = (lineNo%3)==0;
            boolean a5th = (lineNo%5)==0;
            if (a3rd) {
                byte[] utf8Bytes = line.getBytes(StandardCharsets.ISO_8859_1);
                line = new String(utf8Bytes, StandardCharsets.UTF_8);
            }
            if (a5th) {
                byte[] utf8Bytes = line.getBytes(StandardCharsets.ISO_8859_1);
                line = new String(utf8Bytes, StandardCharsets.UTF_8);
            }
            System.out.println("%4d %s %s%s".formatted(
                    lineNo, line,
                    a3rd?"3️⃣":"",
                    a5th?"5️⃣":""
            ));
            words.add(line);
        }
        while ((line=bufferedReader.readLine()) != null) {
            lineNo++;
            System.out.println("%4d %s".formatted(
                    lineNo, line
            ));
            puzzle.add(line);
        }
        AtomicInteger lineNoSum = new AtomicInteger();
        puzzle.forEach(puzzleLine -> {
            int spaces = puzzleLine.lastIndexOf(' ')+1;
            final String puzzleLineRx = puzzleLine.substring(spaces);
            //String aWord = words.stream().filter(word -> word.matches(puzzleLineRx)).findAny().get();
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                if (word.matches(puzzleLineRx)) {
                    System.out.println("0000 "+" ".repeat(spaces)+word+" "+(i+1)+".");
                    lineNoSum.addAndGet((i + 1));
                }
            }
        });
        System.out.println("Line number sum is "+lineNoSum);
    }


}