package Dictionary;

import Dictionary.Views.DictionaryLayout;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {

        DictionaryServices services = new DictionaryServices();
        DictionaryLayout form = new DictionaryLayout(services);
    }
}
