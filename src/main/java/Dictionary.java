import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Dictionary {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            InputStreamReader fileInputStream = new InputStreamReader(new FileInputStream("resource/slang.txt"), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileInputStream);

            HashMap<String,String> dictionary = new HashMap<>();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                StringTokenizer tokenizer = new StringTokenizer(line,"`");
                if (tokenizer.countTokens()==2) {
                    dictionary.put(tokenizer.nextToken(), tokenizer.nextToken());
                }
            }
            System.out.println(dictionary.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
