package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class searchView extends JPanel {
    DictionaryServices dataHandler;
    searchView(DictionaryServices services){
        dataHandler=services;
        this.setBackground(Color.green);
        this.setLayout(new BorderLayout());
        addComponents();
    }
    private void addComponents(){
        JPanel pnSearchBox, pnSlang, pnDefinition;
        JRadioButton rbSlang, rbKeyword;
        JButton btnSearch;
        JLabel lbEnter;
        JTextField tfInput;
        JList list;
        JScrollPane scrollPane;

        //Divide sections
        pnSearchBox = new JPanel();
        this.add(pnSearchBox,BorderLayout.NORTH);
        pnSlang = new JPanel();
        this.add(pnSlang,BorderLayout.WEST);
        pnDefinition = new JPanel();
        this.add(pnDefinition,BorderLayout.SOUTH);

        //Searchbox
        btnSearch = new JButton("Search");
        btnSearch.setFocusPainted(false);
        rbSlang = new JRadioButton("Slang");
        rbKeyword = new JRadioButton("Keyword");
        lbEnter = new JLabel("Enter search key: ");
        tfInput = new JTextField(20);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbKeyword);
        buttonGroup.add(rbSlang);

        pnSearchBox.setLayout(new FlowLayout());
        pnSearchBox.add(rbSlang);
        pnSearchBox.add(rbKeyword);
        pnSearchBox.add(lbEnter);
        pnSearchBox.add(tfInput);
        pnSearchBox.add(btnSearch);
        pnSearchBox.setBorder(new EmptyBorder(10,10,10,10));

        //Add JList
        list = new JList(dataHandler.getKeys().toArray());
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        pnSlang.setLayout(new BorderLayout());
        pnSlang.add(scrollPane);
        

        //Add actionLisnter
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
