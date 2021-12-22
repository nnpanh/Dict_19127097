package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameView extends JPanel {
    Color blue = new Color(207,238,250);
    DictionaryServices dataHandler;
    CardLayout cardLayout;
    JPanel pnContent;

    gameView(DictionaryServices data) {
        dataHandler = data;
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        addComponents();
    }
    public void addComponents(){
        JPanel pnMode, pnButton, pnHolder , pnHide, pnSlang, pnDefinition;
        JButton btnSlang, btnDefinition;
        JLabel lbMode, lbQuestion;
        JRadioButton rbA,rbB,rbC,rbD;

        //Set top buttons for mode
        pnMode = new JPanel(new GridLayout(2,1));
        //Label
        lbMode = new JLabel("Select your game mode: ");
        lbMode.setHorizontalAlignment(JLabel.CENTER);
        //Buttons
        btnSlang = new JButton("Slang");
        btnDefinition = new JButton("Definition");

        //Button panel
        pnButton = new JPanel();
        pnHolder = new JPanel(new FlowLayout());
        pnButton.setLayout(new BoxLayout(pnButton, BoxLayout.LINE_AXIS));
        pnButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        pnButton.add(btnSlang);
        pnButton.add(Box.createRigidArea(new Dimension(20, 0)));
        pnButton.add(btnDefinition);
        pnHolder.add(pnButton);

        //Combine to Mode panel
        pnButton.setBackground(blue);
        pnHolder.setBackground(blue);
        pnMode.setBackground(blue);
        pnMode.add(lbMode);
        pnMode.add(pnHolder);
        pnMode.setPreferredSize(new Dimension(700,100));

        //Set content
         cardLayout = new CardLayout();
        pnContent = new JPanel(cardLayout);
        pnHide = new JPanel();
        pnSlang = new JPanel(new BorderLayout());
        pnDefinition = new JPanel(new BorderLayout());

        pnHide.setBackground(Color.white);
        pnSlang.setBackground(Color.white);
        pnDefinition.setBackground(Color.white);

        pnContent.add("hide",pnHide);
        pnContent.add("slang",pnSlang);
        pnContent.add("definition",pnDefinition);

        //Compose to main
        this.add(pnMode,BorderLayout.NORTH);
        this.add(pnContent,BorderLayout.CENTER);

        //Action listener
        btnSlang.addActionListener(e ->{
            cardLayout.show(pnContent,"slang");

        });
        btnDefinition.addActionListener(e ->{
            cardLayout.show(pnContent,"definition");
        });

    }
    public void refresh(){
        cardLayout.show(pnContent,"hide");
    }
}
