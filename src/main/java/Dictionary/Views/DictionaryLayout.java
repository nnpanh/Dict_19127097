package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class DictionaryLayout extends JFrame {
    DictionaryServices dataHandler;
    Color navy = new Color(0, 0, 128);
    Color blue = new Color(0, 191, 255);

    public DictionaryLayout(DictionaryServices services) {
        dataHandler = services;
        this.setTitle("Dictionary-Slang");
        ImageIcon icon = new ImageIcon("resource/img/icon.png");
        Image scaledImg = icon.getImage();
        this.setIconImage(scaledImg);
        this.setSize(new Dimension(700, 500));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addComponents();
        this.setVisible(true);
        System.out.println("Loading UI complete");
    }

    private void addComponents() {
        JButton btnSearch, btnHistory, btnChange, btnReset, btnRandom, btnGame;
        JPanel pnSearch, pnHistory, pnGame;
        JLabel lbFooter;

        //Set layout
        this.setLayout(new BorderLayout());
        //Divide sections
        JPanel header = new JPanel();
        this.add(header, BorderLayout.NORTH);
        JPanel content = new JPanel();
        this.add(content, BorderLayout.CENTER);
        JPanel footer = new JPanel();
        this.add(footer, BorderLayout.SOUTH);

        //Header
        header.setBackground(navy);
        header.setLayout(new FlowLayout());
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        btnSearch = new JButton("Search");
        btnHistory = new JButton("History");
        btnChange = new JButton("Add new slang");
        btnReset = new JButton("Reload");
        btnRandom = new JButton("On this day slang");
        btnGame = new JButton("Game");
        //Custom button
        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(btnSearch);
        buttons.add(btnHistory);
        buttons.add(btnChange);
        buttons.add(btnReset);
        buttons.add(btnRandom);
        buttons.add(btnGame);
        //Design button
        for (JButton btn : buttons) {
            btn.setFocusPainted(false);
            btn.setForeground(Color.white);
            btn.setBackground(blue);
            header.add(btn);
        }

        //Content
        pnGame = new gameView(dataHandler);
        pnSearch = new searchView(dataHandler);
        pnHistory = new historyView(dataHandler);

        Dimension size = content.getSize();
        CardLayout cardLayout = new CardLayout(size.height, size.width);
        content.setLayout(cardLayout);
        content.add("search", pnSearch);
        content.add("game", pnGame);
        content.add("history", pnHistory);


        //Buttons action listener
        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField slang = new JTextField("Slang", 20);
                JTextField meaning = new JTextField("Meaning", 20);
                JPanel option = new JPanel(new GridLayout(4, 1));
                option.setPreferredSize(new Dimension(100, 100));
                option.add(new JLabel("Slang: "));
                option.add(slang);
                option.add(new JLabel("Meaning: "));
                option.add(meaning);
                int confirm = JOptionPane.showConfirmDialog(getContentPane(), option, "Enter new slang word:", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == JOptionPane.OK_OPTION) {
                    if (dataHandler.checkSlang(slang.getText()) == 0) {
                        dataHandler.addSlang(slang.getText(), meaning.getText());
                        JOptionPane.showMessageDialog(getContentPane(), "New slang added");
                    } else {
                        String[] buttons = {"Overwrite", "Duplicate"};
                        int pick = JOptionPane.showOptionDialog(getContentPane(), "Slang already existed.", "Click a button",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
                        if (pick == 0) dataHandler.overwriteSlang(slang.getText(), meaning.getText());
                        else dataHandler.duplicateSlang(slang.getText(), meaning.getText());
                    }
                }
            }
        });
        btnGame.addActionListener(e -> {
            ((gameView) pnGame).refresh();
            cardLayout.show(content, "game");


        });
        btnHistory.addActionListener(e -> {
            ((historyView) pnHistory).refresh();
            cardLayout.show(content, "history");
        });
        btnSearch.addActionListener(e -> cardLayout.show(content, "search"));
        btnRandom.addActionListener(e -> {
            String[] random = dataHandler.randomWord();
            JOptionPane.showMessageDialog(getContentPane(),"Today random slang word: "+ random[0]
            +" - "+random[1],random[2],JOptionPane.INFORMATION_MESSAGE);
        });
        btnReset.addActionListener(e -> {
            dataHandler.reloadData();
            ((searchView) pnSearch).refresh();
            cardLayout.show(content, "search");
            JOptionPane.showMessageDialog(getContentPane(), "Data reloaded");
        });

        //Redirect card layout
        //Footer
        lbFooter = new JLabel("HCMUS-CLC-19KTPM3-Introduction to Java-19127097");
        lbFooter.setFont(lbFooter.getFont().deriveFont(Font.ITALIC));
        footer.add(lbFooter);

    }
}
