package Dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DictionaryServices {
    HashMap<String, String> dictionary;
    HashMap<String, String> currentDictionary;
    DictionaryServices(){
        dictionary = new HashMap<>();
        loadData("resource/data.txt");
        currentDictionary=dictionary;
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
        System.out.println("Loading data complete");
    }
    public ArrayList<String> getKeys(){
        return new ArrayList<>(dictionary.keySet());
    }
    public String searchBySlang(String key){
        return dictionary.get(key);
    }
    public ArrayList<String> refresh(){
        currentDictionary=dictionary;
        return getKeys();
    }
    public ArrayList<String>  searchByKeyword(String key){
        HashMap<String, String> searchResult = new HashMap<>();
        currentDictionary.forEach((String k, String v)->{
            if(v.contains(key)) searchResult.put(k,v);
        });
        currentDictionary=searchResult;
        return new ArrayList<>(currentDictionary.keySet());
        }

}
