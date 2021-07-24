package MyElsModel;

/*处理小方块，打印俄罗斯方块*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import static java.lang.System.exit;

public class Board extends JPanel {
    private ElsModel elsModel;
    private Board thisBoard;
    Timer timer;

    private boolean isPause;


    public Board(ScoreBoard scoreBoard){
        thisBoard = this;

        //设置组件的焦点值，如果为true，就是得到了焦点，即一个组件被选中，或者正在被操作着。
        setFocusable(true);

        elsModel = new ElsModel(scoreBoard);

        addKeyListener(new TAdapter());


        //创建Timer类时要指定一个延迟参数和一个ActionListener。
        //延迟参数用于设置初始延迟和事件触发之间的延迟（以毫秒为单位）
        //e->{}为Lambda 表达式，也可称为闭包
        timer = new Timer(1000, e->{
            elsModel.oneLineDown();
            this.repaint();
        });
        //如果flag为false，则指示Timer只向其监听器发送一次动作事件
        timer.setRepeats(true);
        //启动Timer，使它开始向其监听器发送动作事件
        timer.start();
    }

    public void startGame(){
        timer.start();
        isPause = false;
    }
    public void stopGame(){
        timer.stop();
        isPause = true;
    }


    protected void paintComponent(Graphics g){
        Dimension dimension = this.getSize();
        //得到一个方块的列宽和行高，类似于长度除以个数
        //dimension.width指长度,ElsModel.WIDTH指个数
        int cellWidth = dimension.width / ElsModel.WIDTH;
        int cellHeight = dimension.height / ElsModel.HEIGHT;

        //设置打印方块的颜色
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0,0,dimension.width,dimension.height);

        //打印俄罗斯方块
        //j代表行，i代表列，data[2][3]表示第三行，第四列
        for(int i = 0; i< ElsModel.WIDTH; ++i){
            for(int j = 0; j < ElsModel.HEIGHT; ++j){
                int content = elsModel.getData()[j][i];
                //drawRect画面板格子线
                if(content == 0) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(i * cellWidth, j * cellHeight,
                            cellWidth, cellHeight);
                }
                //fillRect画填充格子
                else if(content > 0){
                    Color color = randomcolor(content);
                    g2d.setColor(color);
                    g2d.fillRect(i*cellWidth, j * cellHeight,
                            cellWidth, cellHeight);
                }
            }
        }
    }

    public Color randomcolor(int cnt){
        int flag;
        Color data=Color.BLUE;

        Random r = new Random();
        flag = r.nextInt(7);

        switch (cnt){
            //Z-Shape
            case 1:
            case 11:
                return Color.BLUE;
            //S-Shape
            case 2:
            case 12:
                return Color.YELLOW;
            //Line-Shape
            case 3:
            case 13:
                return Color.RED;
            //T-Shape
            case 4:
            case 14:
                return Color.GREEN;
            //Square-Shape
            case 5:
            case 15:
                return Color.ORANGE;
            //L-Shape
            case 6:
            case 16:
                return Color.PINK;
            //Mirrored-L-Shape
            case 7:
            case 17:
                return Color.GRAY;
            default:
                break;
        }
        return data;
    }

    class TAdapter extends KeyAdapter {
        @Override
        //获取键盘动作，并做相应操作
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();

            // Java 12 switch expressions
            switch (keycode) {
                case KeyEvent.VK_DOWN:
                    if(!isPause)
                        elsModel.oneLineDown();
                    break;
                case KeyEvent.VK_LEFT:
                    if(!isPause)
                        elsModel.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    if(!isPause)
                        elsModel.moveRight();
                    break;
                case KeyEvent.VK_A:
                    if(!isPause)
                        elsModel.rotateLeft4Shape();
                    break;
                case KeyEvent.VK_D:
                    if(!isPause)
                        elsModel.rotateRight4Shape();
                    break;
                case KeyEvent.VK_W:
                    timer.start();
                    isPause = false;
                    break;
                case KeyEvent.VK_S:
                    timer.stop();
                    isPause = true;
                    break;
                case KeyEvent.VK_SPACE:
                    exit(0);
                case KeyEvent.VK_H:
                    JOptionPane.showMessageDialog(null, "← 左移\n"+"→ 右移\n"+"↓ 下移\n"
                                    +"A 左转\n"+"D 右转\n"+"\n"
                                    +"W 开始\n"+"S 暂停\n"+"Space 退出游戏\n"+"H 帮助\n"+"\n",
                            "键盘操作规则",JOptionPane.INFORMATION_MESSAGE);
                    break;
            }

            thisBoard.repaint();
        }
    }
}
