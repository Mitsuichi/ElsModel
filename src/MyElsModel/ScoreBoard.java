package MyElsModel;

/*封装的分数面板*/

import javax.swing.*;
import java.awt.*;

public class ScoreBoard extends JPanel {
    //记录分数
    public static int score = 0;
    JTextField ScoreMessage;

    public JPanel CreatScoreBoard(){

        //分数面板
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 17));
        scorePanel.setPreferredSize(new Dimension(120, 280));
        scorePanel.setBackground(Color.LIGHT_GRAY);
        JLabel TitleMessage = new JLabel();
        TitleMessage.setText("分数");
        ScoreMessage = new JTextField(10);
        ScoreMessage.setEditable(false);
        ScoreMessage.setFocusable(false);
        ScoreMessage.setText(""+score);
        scorePanel.add(TitleMessage);
        scorePanel.add(ScoreMessage);

        return scorePanel;
    }

    public void increasescore(){
        score += 10;
        ScoreMessage.setText("" + score);
    }

}
