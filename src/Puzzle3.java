import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;

public class Puzzle3 {
    private static final String TEST_URL_3 = "https://i18n-challenges.helixsoft.nl/puzzleinput/3";
    private static final String TEST_DATA_3 =
            "d9Ō\n" +
            "uwI.E9GvrnWļbzO\n" +
            "ž-2á\n" +
            "Ģ952W*F4\n" +
            "?O6JQf\n" +
            "xi~Rťfsa\n" +
            "r_j4XcHŔB\n" +
            "71äĜ3";
    public static void main(String[] args) throws Exception {
        URL url = new URL(TEST_URL_3);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_3));
        String line;
        int counter = 0;
        while ((line=bufferedReader.readLine()) != null) {
            int ll1 = line.length();
            line = line.replaceAll("<[^>]+>","").trim();
            int ll2 = line.length();
            if (ll1 != ll2) {System.out.println("💚 HTML code found 💚");}

            if (line.length() > 0) {
                int length = line.length();
                boolean from4To12Length = length>=4 && length<=12;

                int dig = 0, lc = 0, uc = 0, acc = 0;
                for (int i = 0; i < length; i++) {
                    char c = line.charAt(i);
                    if (Character.isDigit(c)) {dig++;}
                    if (Character.isLowerCase(c)) {lc++;}
                    if (Character.isUpperCase(c)) {uc++;}
                    if (!Normalizer.isNormalized(new String(new char[]{c}), Normalizer.Form.NFKD)) {acc++;}
                }
                boolean good = from4To12Length&&dig>0&&lc>0&&uc>0&&acc>0;
                if (good) {counter++;}

                System.out.println("↔%2d %s 🔢%2d %s 🔽%2d %s 🔼%2d %s 🔣%2d %s = %s %2d %s".formatted(length, from4To12Length?"🟢":"❌",
                        dig, dig>0?"🟢":"❌",
                        lc, lc>0?"🟢":"❌",
                        uc, uc>0?"🟢":"❌",
                        acc, acc>0?"🟢":"❌",
                        good?"🟢":"❌",counter,
                        line));
            }
        }
    }


}