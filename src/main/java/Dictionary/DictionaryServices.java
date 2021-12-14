package Dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DictionaryServices {
    Hashtable<String, String> dictionary;
    DictionaryServices(){
        dictionary = new Hashtable<>();
        loadData("resource/data.txt");
    }

    public void loadData(String path){
        try {
            InputStreamReader fileInputStream = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileInputStream);

            String line;
            while ((line = bufferedReader.readLine())!=null){
                StringTokenizer tokenizer = new StringTokenizer(line,"`");
                if (tokenizer.countTokens()==2) {
                    dictionary.put(tokenizer.nextToken(), tokenizer.nextToken());
                }
            }
        } catch (
        IOException e) {
            e.printStackTrace();
        }

    }
    public List<String> getKeys(){
        List<String> data = new ArrayList<>(dictionary.keySet());
        return data;
    }
}
