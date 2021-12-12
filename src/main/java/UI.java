import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.Color.blue;

public class UI extends JFrame {
    Color navy = new Color(0,0,128);
    Color blue = new Color(0,191,255);
    private void addComponents(){
        JButton btnSearch, btnHistory, btnChange, btnReset, btnRandom, btnGame;
        JPanel pnSearch, pnHistory, pnChange, pnReset, pnRandom, pnGame;
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
        btnChange = new JButton("Edit dictionary");
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
        //Redirect card layout
        //Footer
        lbFooter = new JLabel("HCMUS-CLC-19KTPM3-Introduction to Java-19127097");
        lbFooter.setFont(lbFooter.getFont().deriveFont(Font.ITALIC));
        footer.add(lbFooter);

    }

    UI(){
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

    public static void main(String[] args) {
        UI ui = new UI();
    }
}
