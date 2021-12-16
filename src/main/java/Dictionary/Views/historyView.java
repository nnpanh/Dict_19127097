package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static javax.swing.text.StyleConstants.Background;

public class historyView extends JPanel {
    JList<Object> listData;
    JScrollPane scrollPane;
    DictionaryServices dataHandler;
    historyView(DictionaryServices data){
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout(0,0));
        this.setBorder(new EmptyBorder(10,10,10,10));
        //Set data
        dataHandler=data;
        listData = new JList<>(dataHandler.getHistory().toArray());
        scrollPane = new JScrollPane();
        listData.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setViewportView(listData);
        scrollPane.setBackground(Color.white);
        this.add(scrollPane);

    }
    public void refresh(){
        listData.setListData(dataHandler.getHistory().toArray());
    }
}

