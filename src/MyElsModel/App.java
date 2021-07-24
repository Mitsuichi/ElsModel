package MyElsModel;

/*主函数，设计游戏主界面*/

import javax.swing.*;
import java.awt.*;

import static java.lang.System.exit;

public class App extends JFrame {


    public static void main(String[] args) {
        App app = new App();
        app.setTitle("俄罗斯方块");

        JOptionPane.showMessageDialog(null, "← 左移\n"+"→ 右移\n"+"↓ 下移\n"
                        +"A 左转\n"+"D 右转\n"+"\n"
                        +"W 开始\n"+"S 暂停\n"+"Space 退出游戏\n"+"H 帮助\n"+"\n",
                "键盘操作规则",JOptionPane.INFORMATION_MESSAGE);


        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = null;


        //主面板
        JPanel panel = new JPanel(gridBag);

        //分数面板
        ScoreBoard scorePanel = new ScoreBoard();
        scorePanel.CreatScoreBoard();

        //游戏面板
        Board gamePanel = new Board(scorePanel);
        gamePanel.setPreferredSize(new Dimension(300, 480));


        //按钮控制面板
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 17));
        controlPanel.setPreferredSize(new Dimension(120, 200));
        controlPanel.setBackground(Color.DARK_GRAY);

        Box btnbox = Box.createVerticalBox();

        JButton start = new JButton("开始");
        JButton stop = new JButton("暂停");
        JButton end = new JButton("结束游戏");
        JButton help = new JButton("帮助");
        //创建三个按钮的监视器
        start.addActionListener(e->{
            gamePanel.startGame();
        });
        stop.addActionListener(e->{
            gamePanel.stopGame();
        });
        end.addActionListener(e->{
            exit(0);
        });
        help.addActionListener(e->{
            gamePanel.stopGame();
            JOptionPane.showMessageDialog(null, "← 左移\n"+"→ 右移\n"+"↓ 下移\n"
                            +"A 左转\n"+"D 右转\n"+"\n"
                            +"W 开始\n"+"S 暂停\n"+"Space 退出游戏\n"+"H 帮助\n"+"\n",
                    "键盘操作规则",JOptionPane.INFORMATION_MESSAGE);
        });

        controlPanel.add(start);
        start.setFocusable(false);
        controlPanel.add(stop);
        stop.setFocusable(false);
        controlPanel.add(end);
        controlPanel.add(help);
        help.setFocusable(false);

        controlPanel.add(btnbox);



        //在主面板添加三个面板
        c = new GridBagConstraints();
        c.gridheight = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(gamePanel, c);

        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(scorePanel.CreatScoreBoard(), c);

        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(controlPanel, c);

        panel.add(gamePanel);
        panel.add(scorePanel.CreatScoreBoard());
        panel.add(controlPanel);


        //在游戏界面添加主面板
        app.add(panel, BorderLayout.CENTER);
        //设置游戏主界面大小
        app.setSize(550, 550);
        //设置游戏主界面可见
        app.setVisible(true);
        //设置游戏主界面初始在屏幕位置
        app.setLocation(550, 100);
        //设置游戏主界面关闭方式
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}


