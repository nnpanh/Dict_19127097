package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class historyView extends JPanel {
    JList<Object> listData;
    DictionaryServices dataHandler;
    historyView(DictionaryServices data){
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10,10,10,10));
        //Set data
        dataHandler=data;
        listData = new JList<>(dataHandler.getHistory().toArray());
        this.add(listData,BorderLayout.LINE_START);

    }
    public void refresh(){
        listData.setListData(dataHandler.getHistory().toArray());
    }
}

