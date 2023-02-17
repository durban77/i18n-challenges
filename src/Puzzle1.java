import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.Normalizer;

public class Puzzle1 {
    private static final java.lang.String TEST_URL_1 = "https://i18n-challenges.helixsoft.nl/puzzleinput/1";
    private static final java.lang.String TEST_DATA_1 =
    "néztek bele az „ártatlan lapocskába“, mint ahogy belenézetlen mondták ki rá a halálos itéletet a sajtó csupa 20–30 éves birái s egyben hóhérai."+"\n"+
    "livres, et la Columbiad Rodman ne dépense que cent soixante livres de poudre pour envoyer à six milles son boulet d'une demi-tonne.  Ces"+"\n"+
    "Люди должны были тамъ и сямъ жить въ палаткахъ, да и мы не были помѣщены въ посольскомъ дворѣ, который также сгорѣлъ, а въ двухъ деревянныхъ"+"\n"+
    "Han hade icke träffat Märta sedan Arvidsons middag, och det hade gått nära en vecka sedan dess. Han hade dagligen promenerat på de gator, där";

    public static void main(java.lang.String[] args) throws Exception {
        java.net.URL url = new java.net.URL(TEST_URL_1);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        //bufferedReader = new BufferedReader(new StringReader(TEST_DATA_1));
        String line;
        int counter = 0;
        while ((line=bufferedReader.readLine()) != null) {
            int ll1 = line.length();
            line = line.replaceAll("<[^>]+>","");
            int ll2 = line.length();
            if (ll1 != ll2) {System.out.println("💚 HTML code found 💚");}

            if (line.length() > 0) {
                byte[] bytes = line.getBytes("UTF-8");
                int byteCount = bytes.length;
                boolean goodForSms = byteCount > 0 && byteCount <= 160;

                int characterCount = line.length();
                boolean goodForTweet = characterCount > 0 && characterCount <= 140;

                boolean countAsOne = (goodForTweet ^ goodForSms);
                counter += countAsOne ? 1 : 0;
                System.out.println("S %4d byte %s T %4d char %s C %4d %s %s".formatted(
                        byteCount, goodForSms ? "🆗" : "❌",
                        characterCount, goodForTweet ? "🆗" : "❌",
                        counter, countAsOne ? "🆗" : "❌",
                        line));
            }
        }
    }


}