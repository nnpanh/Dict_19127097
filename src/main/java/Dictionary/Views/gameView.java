package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import java.awt.*;

public class gameView extends JPanel {
    DictionaryServices dataHandler;
    gameView(DictionaryServices data) {
        this.setBackground(Color.white);
        dataHandler = data;
    }
}
