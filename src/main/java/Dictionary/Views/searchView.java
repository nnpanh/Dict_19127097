package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class searchView extends JPanel {
    DictionaryServices dataHandler;
    JList<Object> list;
    JTextField tfInput;

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
    private void addComponents() {
        JPanel pnSearchBox, pnSlang, pnDefinition, pnTop, pnHolder, pnButton, pnHide, pnCard;
        JRadioButton rbSlang, rbKeyword;
        JButton btnSearch, btnEdit, btnDelete;
        JLabel lbEnter, lbDefinition;
        JScrollPane scrollPane;

        //Divide sections
        pnSearchBox = new JPanel();
        this.add(pnSearchBox, BorderLayout.NORTH);
        pnSlang = new JPanel();
        pnSlang.setPreferredSize(new Dimension(210, 800));
        this.add(pnSlang, BorderLayout.WEST);
        pnDefinition = new JPanel(new BorderLayout());
        this.add(pnDefinition, BorderLayout.CENTER);

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
        btnDelete = new JButton("Delete");
        btnEdit = new JButton("Edit");
        pnTop = new JPanel(new FlowLayout());
        pnTop.add(lbDefinition);
        pnTop.setBackground(Color.white);

        //Show/Hide edit-delete button
        pnButton = new JPanel();
        pnHolder = new JPanel(new FlowLayout());
        pnHide = new JPanel();
        pnCard = new JPanel();

        pnButton.setBackground(Color.white);
        pnHolder.setBackground(Color.white);
        pnHide.setBackground(Color.white);

        pnButton.setLayout(new BoxLayout(pnButton,BoxLayout.LINE_AXIS));
        pnButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnButton.add(Box.createHorizontalGlue());
        pnButton.add(btnEdit);
        pnButton.add(Box.createRigidArea(new Dimension(20, 0)));
        pnButton.add(btnDelete);
        pnHolder.add(pnButton);

        Dimension size = pnHolder.getSize();
        CardLayout cardLayout= new CardLayout(size.height,size.width);
        pnCard.setLayout(cardLayout);
        pnCard.add("hide",pnHide);
        pnCard.add("show",pnHolder);


        pnDefinition.setBackground(Color.white);
        pnDefinition.add(pnTop,BorderLayout.NORTH);
        pnDefinition.add(pnCard,BorderLayout.SOUTH);

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
                //Get key ->search in  dictionary
                String searchKey = list.getSelectedValue().toString();
                String searchResult = dataHandler.searchBySlang(searchKey).replace("|"," OR");
                lbDefinition.setText(searchResult);
                cardLayout.show(pnCard,"show");
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
                //Update UI
                lbDefinition.setText("Refresh");
                cardLayout.show(pnCard,"hide");
                //Add to history
                dataHandler.addHistory("User reset search result");
            } else {
                //If mode=Slang, search one word
                if (searchMode.equals("Slang")) {
                    String searchResult = dataHandler.searchBySlang(searchKey);
                    if (searchResult != null) {
                        searchResult.replace("|", " OR");
                        //Update UI
                        lbDefinition.setText(searchResult);
                        cardLayout.show(pnCard,"show");
                    }
                    else{ //Update UI
                        lbDefinition.setText("Slang not found");
                        cardLayout.show(pnCard,"hide");
                    }
                    //Add to history
                    dataHandler.addHistory("User entered '" + searchKey + "' to search one slang word");
                }
                //Else, return new list of data
                else {
                    //Logical handling
                    ArrayList<String> searchResults = dataHandler.searchByKeyword(searchKey);
                    list.setListData(searchResults.toArray());
                    if (searchResults.size() == 0) lbDefinition.setText("No slang contains '" + searchKey + "'");
                    else lbDefinition.setText("List of slang contains keyword '" + searchKey + "'");
                    //Update UI
                    cardLayout.show(pnCard,"hide");
                    //Add to history
                    dataHandler.addHistory("User entered '" + searchKey + "' to search by keyword");
                }
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(pnCard,"hide");
            }
        });
    }
}
