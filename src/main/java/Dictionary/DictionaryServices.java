package Dictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class DictionaryServices {
    final String source = "resource/slang.txt";
    final String dest = "resource/data.txt";
    HashMap<String, String> dictionary;
    HashMap<String, String> currentDictionary;
    ArrayList<String> history;

    DictionaryServices() {
        dictionary = new HashMap<>();
        loadData(dest);
        currentDictionary = dictionary;
        history = new ArrayList<>();
        history.add("Start");
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void addHistory(String newLine) {
        history.add(newLine);
    }

    public void loadData(String path) {
        try {
            InputStreamReader fileInputStream = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileInputStream);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, "`");
                if (tokenizer.countTokens() == 2) {
                    dictionary.put(tokenizer.nextToken(), tokenizer.nextToken());
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loading data complete");
    }

    public void reloadData() {
        dictionary.clear();
        loadData(source);
        currentDictionary = dictionary;
        try {
            InputStream input = new FileInputStream(source);
            OutputStream output = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            System.out.println("Overwritten data.txt");
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getKeys() {
        return new ArrayList<>(dictionary.keySet());
    }

    public String searchBySlang(String key) {
        return dictionary.get(key);
    }

    public ArrayList<String> refresh() {
        currentDictionary = dictionary;
        return getKeys();
    }

    public ArrayList<String> searchByKeyword(String key) {
        HashMap<String, String> searchResult = new HashMap<>();
        currentDictionary.forEach((String k, String v) -> {
            if (v.contains(key)) searchResult.put(k, v);
        });
        return new ArrayList<>(searchResult.keySet());
    }

    public void editSlang(String currentSlang, String newSlang) {
        currentDictionary.put(currentSlang,newSlang);
    }
}
