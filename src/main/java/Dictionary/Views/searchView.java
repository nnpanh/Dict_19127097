package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class searchView extends JPanel {
    DictionaryServices dataHandler;
    searchView(DictionaryServices services){
        dataHandler=services;
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        addComponents();
    }
    private void addComponents(){
        JPanel pnSearchBox, pnSlang, pnDefinition;
        JRadioButton rbSlang, rbKeyword;
        JButton btnSearch;
        JLabel lbEnter, lbDefinition;
        JTextField tfInput;
        JList<Object> list;
        JScrollPane scrollPane;

        //Divide sections
        pnSearchBox = new JPanel();
        this.add(pnSearchBox,BorderLayout.NORTH);
        pnSlang = new JPanel();
        pnSlang.setPreferredSize(new Dimension(200,800));
        this.add(pnSlang,BorderLayout.WEST);
        pnDefinition = new JPanel(new FlowLayout());
        this.add(pnDefinition,BorderLayout.CENTER);

        //Searchbox
        btnSearch = new JButton("Search");
        btnSearch.setFocusPainted(false);
        rbSlang = new JRadioButton("Slang");
        rbSlang.setActionCommand("Slang");
        rbKeyword = new JRadioButton("Keyword");
        rbKeyword.setActionCommand("Keyword");

        lbEnter = new JLabel("Enter search key: ");
        tfInput = new JTextField(20);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbKeyword);
        buttonGroup.add(rbSlang);
        buttonGroup.setSelected(rbSlang.getModel(), true);

        pnSearchBox.setLayout(new FlowLayout());
        pnSearchBox.add(rbSlang);
        pnSearchBox.add(rbKeyword);
        pnSearchBox.add(lbEnter);
        pnSearchBox.add(tfInput);
        pnSearchBox.add(btnSearch);
        pnSearchBox.setBorder(new EmptyBorder(10,10,10,10));

        //Add content panel
        lbDefinition = new JLabel("Pick a slang");
        lbDefinition.setFont(new Font(lbDefinition.getFont().getFontName(), Font.PLAIN, 14));
        pnDefinition.setBackground(Color.white);
        pnDefinition.add(lbDefinition);

        //Add JList
        list = new JList<>(dataHandler.getKeys().toArray());
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        pnSlang.setLayout(new BorderLayout());
        pnSlang.add(scrollPane);

        //Add actionListener
        list.addListSelectionListener(e -> {
            if (!list.isSelectionEmpty()) {
                String searchKey = list.getSelectedValue().toString();
                String searchResult = dataHandler.searchBySlang(searchKey).replace("|"," or");
                lbDefinition.setText(searchResult);
            }
    });

        btnSearch.addActionListener(e -> {
            String searchMode = buttonGroup.getSelection().getActionCommand();
            String searchKey = tfInput.getText();
            if (searchKey.equals("")) {
                list.setListData(dataHandler.refresh().toArray());
            }
            if (searchMode.equals("Slang")) {
                String searchResult = dataHandler.searchBySlang(searchKey);
                if (!searchResult.isBlank()) searchResult.replace("|"," or");
                lbDefinition.setText(Objects.requireNonNullElse(searchResult, "Slang not found"));
            }
            else
            {
                ArrayList<String> searchResults = dataHandler.searchByKeyword(searchKey);
                list.setListData(searchResults.toArray());
            }

        });
    }
}
