package Dictionary;

import Dictionary.Views.DictionaryLayout;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {

        DictionaryServices services = new DictionaryServices();
        DictionaryLayout form = new DictionaryLayout(services);
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                services.saveHistory("resource/history.txt");

                if (JOptionPane.showConfirmDialog(form,
                        "Do you want to save changes on your dictionary?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    services.saveDictionary("resource/data.txt");
                }
                    System.exit(0);

            }
        });
    }

}
