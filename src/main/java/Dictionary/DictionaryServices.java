package Dictionary;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DictionaryServices {
    final String source = "resource/slang.txt";
    final String dest = "resource/data.txt";
    final String _history="resource/history.txt";
    final String _date="resource/date.txt";
    HashMap<String, String> dictionary;
    HashMap<String, String> currentDictionary;
    ArrayList<String> history;

    DictionaryServices() {
        dictionary = new HashMap<>();
        loadData(dest);
        currentDictionary = dictionary;
        history = new ArrayList<>();
        loadHistory(_history);
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void addHistory(String newLine) {
        history.add(newLine);
    }

    public void loadData(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, "`");
                if (tokenizer.countTokens() == 2) {
                    dictionary.put(tokenizer.nextToken(), tokenizer.nextToken());
                }
            }
            bufferedReader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loading data complete");
    }

    public void loadHistory(String path){
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                history.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (history.size()>1000)
          history.remove(0);
    }
    public void saveHistory(String path){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            for (String line: history
                 ) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
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
    public void saveDictionary(String path){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            currentDictionary.forEach((String k, String v) -> {
                try {
                    bufferedWriter.write(k+"`"+v);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.close();
        }catch (IOException e)
        {
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
        currentDictionary.replace(currentSlang,newSlang);
    }

    public int checkSlang(String slang){
        //0 = NEW, 1 = EXISTS
        for (Map.Entry<String, String> entry : currentDictionary.entrySet()) {
            String k = entry.getKey();
            if (k.equals(slang)) {
                return 1;
            }
        }
        return 0;
    }

    public void addSlang(String slang, String meaning){
        currentDictionary.put(slang,meaning);
    }

    public void overwriteSlang(String slang, String meaning){
        currentDictionary.replace(slang,meaning);
    }

    public void duplicateSlang(String slang, String meaning){
        String old = currentDictionary.get(slang);
        currentDictionary.replace(slang,old+"| "+meaning);
    }
    public void deleteSlang(String slang){
        currentDictionary.remove(slang);
    }

    public String[] randomWord(){
        String[] random ={"slang","meaning","date"};

        String pattern ="yyyy-MM-dd";

        //Read from date.txt
        try {
            FileInputStream fileInputStream = new FileInputStream(_date);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            String slang = bufferedReader.readLine();
            String meaning;
            bufferedReader.close();

            //Compare date in date.txt
            LocalDate today = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate fileDate = LocalDate.parse(line, formatter);

            if (today.equals(fileDate)) {
                meaning=searchBySlang(slang);
            }
            else{
                Object[] keys = currentDictionary.keySet().toArray();
                Random rand = new Random();
                slang = keys[rand.nextInt(keys.length)].toString();
                meaning=searchBySlang(slang);
                //Write new slang to date.txt
                FileOutputStream fileOutputStream = new FileOutputStream(_date);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                bufferedWriter.write(today.toString());
                bufferedWriter.newLine();
                bufferedWriter.write(slang);

                bufferedWriter.close();
            }
            random[0]=slang;
            random[1]=meaning;
            random[2]=today.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return random;
    }
    public String[] loadQuestion(int mode){
        //MODE 1: SLANG - FIND MEANING, 2: MEANING - FIND SLANG
        String[] question={"","","","",""};
        Object[] keys,values;
        //Generate
        keys = currentDictionary.keySet().toArray();
        values = currentDictionary.values().toArray();
        //Get the question
        int size = dictionary.size();
        ArrayList<Integer> random = new ArrayList<>();
        //Random
        for (int i=0;i<size;i++) {
            random.add(i);
        }
        Collections.shuffle(random);
        //Answer
        if (mode ==1) {
            question[0] = keys[random.get(0)].toString();
            question[1]= values[random.get(0)].toString();
            question[2]= values[random.get(1)].toString();
            question[3]= values[random.get(2)].toString();
            question[4]= values[random.get(3)].toString();
            question[0]="What is the meaning of '"+question[0]+"'?";
              }
        else {
            question[0] = values[random.get(0)].toString();
            question[1]= keys[random.get(0)].toString();
            question[2]= keys[random.get(1)].toString();
            question[3]= keys[random.get(2)].toString();
            question[4]= keys[random.get(3)].toString();
            question[0]="What is the definition of '"+question[0]+"'?";
        }
        return question;
    }

}
