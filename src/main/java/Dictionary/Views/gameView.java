package Dictionary.Views;

import Dictionary.DictionaryServices;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class gameView extends JPanel {
    Color blue = new Color(207,238,250);
    DictionaryServices dataHandler;
    CardLayout cardLayout;
    JPanel pnContent;
    String[] question;
    String answer;

    gameView(DictionaryServices data) {
        dataHandler = data;
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        addComponents();

    }
    public void addComponents(){
        JPanel pnMode, pnButton, pnHolder , pnHide, pnShow, pnQuestion, pnAnswer, pnHolder2;
        JButton btnSlang, btnDefinition, btnGiveUp, btnAnswer;
        JLabel lbMode, lbQuestion;
        JRadioButton rbA,rbB,rbC,rbD;
        AtomicInteger mode= new AtomicInteger(1);

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
        pnShow = new JPanel(new BorderLayout());

        pnContent.add("hide",pnHide);
        pnContent.add("show",pnShow);

        //Question
        rbA = new JRadioButton("A");
        rbB = new JRadioButton("B");
        rbC = new JRadioButton("C");
        rbD = new JRadioButton("D");
        lbQuestion = new JLabel("Question?");
        lbQuestion.setHorizontalAlignment(JLabel.CENTER);
        lbQuestion.setBorder(new EmptyBorder(10,10,10,10));
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbA);
        buttonGroup.add(rbB);
        buttonGroup.add(rbC);
        buttonGroup.add(rbD);
        buttonGroup.setSelected(rbA.getModel(), true);
        pnQuestion = new JPanel();
        pnQuestion.setLayout(new BoxLayout(pnQuestion,BoxLayout.Y_AXIS));
        pnQuestion.setBorder(new EmptyBorder(10,30,10,0));
        pnQuestion.add(rbA);
        pnQuestion.add(rbB);
        pnQuestion.add(rbC);
        pnQuestion.add(rbD);

        //Options
        btnGiveUp = new JButton("Give up");
        btnAnswer = new JButton("Answer");
        pnAnswer = new JPanel();
        pnHolder2 = new JPanel(new FlowLayout());
        pnAnswer.setLayout(new BoxLayout(pnAnswer, BoxLayout.LINE_AXIS));
        pnAnswer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        pnAnswer.add(btnGiveUp);
        pnAnswer.add(Box.createRigidArea(new Dimension(20, 0)));
        pnAnswer.add(btnAnswer);
        pnHolder2.add(pnAnswer);

        //Add content
        pnShow.add(lbQuestion,BorderLayout.NORTH);
        pnShow.add(pnQuestion,BorderLayout.CENTER);
        pnShow.add(pnHolder2,BorderLayout.SOUTH);

        //Set color
        rbA.setBackground(Color.white);
        rbB.setBackground(Color.white);
        rbC.setBackground(Color.white);
        rbD.setBackground(Color.white);
        pnContent.setBackground(Color.white);
        pnHide.setBackground(Color.white);
        pnShow.setBackground(Color.white);
        pnQuestion.setBackground(Color.white);
        pnAnswer.setBackground(Color.white);
        pnHolder2.setBackground(Color.white);


        //Compose to main
        this.add(pnMode,BorderLayout.NORTH);
        this.add(pnContent,BorderLayout.CENTER);

        //Action listener
        btnSlang.addActionListener(e ->{
            mode.set(1);
            question = dataHandler.loadQuestion(mode.get());
            setQuestion(lbQuestion, rbA, rbB, rbC, rbD);
        });
        btnDefinition.addActionListener(e ->{
            mode.set(2);
            question = dataHandler.loadQuestion(mode.get());
            setQuestion(lbQuestion, rbA, rbB, rbC, rbD);
        });

        btnGiveUp.addActionListener(e ->{
            JOptionPane.showMessageDialog(null,"The answer is: "+answer,"Try harder!",JOptionPane.PLAIN_MESSAGE);
            question = dataHandler.loadQuestion(mode.get());
            setQuestion(lbQuestion,rbA,rbB,rbC,rbD);
        });

        btnAnswer.addActionListener(e ->{
            if (answer.equals(buttonGroup.getSelection().getActionCommand())){
                JOptionPane.showMessageDialog(null,"Correct!","Answer",JOptionPane.PLAIN_MESSAGE);
                question = dataHandler.loadQuestion(mode.get());
                setQuestion(lbQuestion,rbA,rbB,rbC,rbD);
            } else
                JOptionPane.showMessageDialog(null,"Wrong answer. Try again!","Answer",JOptionPane.PLAIN_MESSAGE);

        });

    }

    private void setQuestion(JLabel lbQuestion, JRadioButton rbA, JRadioButton rbB, JRadioButton rbC, JRadioButton rbD) {
        lbQuestion.setText(question[0]);
        answer=question[1];
        ArrayList<String> shuffle = new ArrayList<>(Arrays.asList(question[1], question[2],question[3],question[4]));
        Collections.shuffle(shuffle);
        rbA.setText(shuffle.get(0).replace("|"," OR"));
        rbA.setActionCommand(shuffle.get(0).replace("|"," OR"));
        rbB.setText(shuffle.get(1).replace("|"," OR"));
        rbB.setActionCommand(shuffle.get(1).replace("|"," OR"));
        rbC.setText(shuffle.get(2).replace("|"," OR"));
        rbC.setActionCommand(shuffle.get(2).replace("|"," OR"));
        rbD.setText(shuffle.get(3).replace("|"," OR"));
        rbD.setActionCommand(shuffle.get(3).replace("|"," OR"));
        cardLayout.show(pnContent,"show");
    }


    public void refresh(){
        cardLayout.show(pnContent,"hide");
    }
}
