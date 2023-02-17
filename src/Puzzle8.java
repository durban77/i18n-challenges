import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

public class Puzzle8 {
    private static final String TEST_URL_8 = "https://i18n-challenges.helixsoft.nl/puzzleinput/8";
    private static final String TEST_DATA_8 =
            "iS0\n" +
            "V8AeC1S7KhP4Ļu\n" +
            "pD9Ĉ*jXh\n" +
            "E1-0\n" +
            "ĕnz2cymE\n" +
            "tqd~üō\n" +
            "IgwQúPtd9\n" +
            "k2lp79ąqV";
    public static void main(String[] args) throws Exception {
        URL url = new URL(TEST_URL_8);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_8));
        String line;
        int counter = 0;
        while ((line=bufferedReader.readLine()) != null) {
            if (line.length() > 0) {
                String deaccented = Normalizer.normalize(line, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
                int length = deaccented.length();
                boolean from4To12Length = length>=4 && length<=12;

                Set<Character> lettersAlreadyUsed = new HashSet<>();
                int digits = 0, vowel = 0, consonant = 0, doubling = 0;
                for (int i = 0; i < length; i++) {
                    char c = deaccented.charAt(i);
                    if (Character.isDigit(c)) {digits++;}
                    if (c >= 'a' && c <= 'z') {
                        if (c=='a'||c=='e'||c=='i'||c=='o'||c=='u') { // ||c=='y'
                            vowel++;
                        } else {
                            consonant++;
                        }
                        if (lettersAlreadyUsed.contains(c)) {
                            doubling++;
                        }
                        lettersAlreadyUsed.add(c);
                    }
                }
                boolean good = from4To12Length&&digits>0&&vowel>0&&consonant>0&&doubling<=0;
                if (good) {counter++;}

                System.out.println("↔%2d %s 🔢%2d %s 🅰%2d %s 🅱%2d %s ➿%2d %s = %s %2d %s".formatted(length, from4To12Length?"🟢":"❌",
                        digits, digits>0?"🟢":"❌",
                        vowel, vowel>0?"🟢":"❌",
                        consonant, consonant>0?"🟢":"❌",
                        doubling, doubling>0?"🟢":"❌",
                        good?"🟢":"❌",counter,
                        line));
            }
        }
    }


}