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
    Color navy = new Color(0,0,128);
    Color blue = new Color(0,191,255);
    private void addComponents(){
        JButton btnSearch, btnHistory, btnChange, btnReset, btnRandom, btnGame;
        JPanel pnSearch, pnHistory, pnGame;
        JLabel lbFooter;

        //Set layout
        this.setLayout(new BorderLayout());
        //Divide sections
        JPanel header = new JPanel();
        this.add(header,BorderLayout.NORTH);
        JPanel content = new JPanel();
        this.add(content,BorderLayout.CENTER);
        JPanel footer = new JPanel();
        this.add(footer, BorderLayout.SOUTH);

        //Header
        header.setBackground(navy);
        header.setLayout(new FlowLayout());
        header.setBorder(new EmptyBorder(10,10,10,10));
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
        for (JButton btn:buttons) {
            btn.setFocusPainted(false);
            btn.setForeground(Color.white);
            btn.setBackground(blue);
            header.add(btn);
        }

        //Content
        pnGame = new gameView();
        pnHistory = new historyView();
        pnSearch = new searchView(dataHandler);

        Dimension size = content.getSize();
        CardLayout cardLayout= new CardLayout(size.height,size.width);
        content.setLayout(cardLayout);
        content.add("search",pnSearch);
        content.add("game",pnGame);
        content.add("history",pnHistory);


        //Buttons action listener
        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(content,"game");
            }
        });
        btnHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(content,"history");
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(content,"search");
            }
        });
        btnRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Redirect card layout
        //Footer
        lbFooter = new JLabel("HCMUS-CLC-19KTPM3-Introduction to Java-19127097");
        lbFooter.setFont(lbFooter.getFont().deriveFont(Font.ITALIC));
        footer.add(lbFooter);

    }

    public DictionaryLayout(DictionaryServices services){
        dataHandler = services;
        this.setTitle("Dictionary-Slang");
        ImageIcon icon = new ImageIcon("resource/img/icon.png");
        Image scaledImg = icon.getImage();
        this.setIconImage(scaledImg);
        this.setSize(new Dimension(800,800));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponents();
        this.setVisible(true);
    }
}
