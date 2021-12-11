import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Dictionary {
    public static void main(String[] args) {
        try {
            InputStreamReader fileInputStream = new InputStreamReader(new FileInputStream("/resource/simple.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileInputStream);

            HashMap<String,String> dictionary = new HashMap<>();
            String line = bufferedReader.readLine(); //Skip 1st line
            while ((line = bufferedReader.readLine())!=null){
                StringTokenizer tokenizer = new StringTokenizer(line,"`");
                dictionary.put(tokenizer.nextToken(), tokenizer.nextToken());
            }
            System.out.println(dictionary.values());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
