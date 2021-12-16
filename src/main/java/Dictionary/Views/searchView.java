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
    JPanel pnSearchBox, pnSlang, pnDefinition;
    JRadioButton rbSlang, rbKeyword;
    JButton btnSearch;
    JLabel lbEnter, lbDefinition;
    JTextField tfInput;
    JList<Object> list;
    JScrollPane scrollPane;

    searchView(DictionaryServices services){
        dataHandler=services;
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        addComponents();
    }
    public void refresh(){
        list.setListData(dataHandler.getKeys().toArray());
        list.clearSelection();
        tfInput.setText(null);
    }
    private void addComponents(){
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
                //Get key ->search in current dictionary
                String searchKey = list.getSelectedValue().toString();
                String searchResult = dataHandler.searchBySlang(searchKey).replace("|"," OR");
                lbDefinition.setText(searchResult);
                dataHandler.addHistory("User clicked on the slang word '"+searchKey+"'");
            }
    });

        btnSearch.addActionListener(e -> {
            //Unselect on list
            list.clearSelection();

            //Get key and mode in radio button
            String searchMode = buttonGroup.getSelection().getActionCommand();
            String searchKey = tfInput.getText();

            //If search key = null, refresh data list
            if (searchKey.equals("")) {
                list.setListData(dataHandler.refresh().toArray());
                lbDefinition.setText("Refresh");
                dataHandler.addHistory("User reset search result");
            }

            //If mode=Slang, search one word
            if (searchMode.equals("Slang")) {
                dataHandler.addHistory("User entered '"+searchKey+"' to search one slang word");
                String searchResult = dataHandler.searchBySlang(searchKey);
                if (searchResult!=null) searchResult.replace("|"," OR");
                lbDefinition.setText(Objects.requireNonNullElse(searchResult, "Slang not found"));
            }
            //Else, return new list of data
            else
            {
                dataHandler.addHistory("User entered '"+searchKey+"' to search by keyword");
                ArrayList<String> searchResults = dataHandler.searchByKeyword(searchKey);
                list.setListData(searchResults.toArray());
                if (searchResults.size()==0) lbDefinition.setText("No slang contains '"+searchKey+"'");
                else lbDefinition.setText("List of slang contains keyword '"+searchKey+"'");
            }

        });
    }
}
