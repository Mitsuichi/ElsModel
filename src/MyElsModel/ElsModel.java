package MyElsModel;

/*处理俄罗斯方块在面板中的位置*/

import javax.swing.*;

import static MyElsModel.ScoreBoard.score;
import static java.lang.System.exit;

public class ElsModel {
    //用于记录面板中每一个方块的值0或1，为1的会打印
    private int[][] data;

    public static ScoreBoard midpanel;

    public static final int MOVE_NO_TURN = 0;
    public static final int MOVE_RIGHT_TURN = 2;
    public static final int MOVE_LEFT_TURN = 1;

    //12*20的面板
    //data[2][3]表示第三行，第四列
    public static final int WIDTH = 12;
    public static final int HEIGHT = 20;

    //true_shape,便于区别方法中的形参shape
    public LineShape tshape;

    public int currentRow;
    public int currentCol;


    public int[][] getData() {
        return data;
    }

    public ElsModel(ScoreBoard panel) {
        //初始化面板中所有方块
        data = new int[HEIGHT][WIDTH];

        tshape = newShape();
        //Row行对应垂直方向,Col列对应水平方向
        //这里设置初始“中心块”在面板中的位置，面板行列标号从0开始
        //任何一个俄罗斯方块的最上面一个方块的在一个4*4的矩形里行号不是-1就是0，可以加负号
        currentRow = -tshape.minY();
        currentCol = WIDTH / 2;

        appendShape(tshape, false);

        midpanel = panel;
    }

    //新建一个俄罗斯方块对象
    public LineShape newShape() {
        return new LineShape();
    }

    //获得刷新到的，将在面板中打印的俄罗斯方块的二维数组
    public void appendShape(LineShape shape, boolean isPersist) {
        //LineShape对象shape调用getData方法，获得刷新到的俄罗斯方块的二维数组
        int[][] shapeData = shape.getData();
        int cnt = shape.getContent();
        //判断是否是永久填充
        int content = isPersist ? 10 + cnt : cnt;
        //一个俄罗斯方块包括四个小方块
        for (int i = 0; i < 4; ++i) {
            //将面板中会打印俄罗斯方块的空白方块置1或2，准备打印
            data[currentRow + shapeData[i][1]][currentCol + shapeData[i][0]] = content;
        }
    }


    //控制俄罗斯方块向下移动一行，并更新数组
    public boolean oneLineDown() {
        //如果移动后越界
        if (!tryMove(tshape, currentRow + 1, currentCol, MOVE_NO_TURN)) {
            appendShape(tshape, true);

            //再刷新一个新的俄罗斯方块
            tshape = newShape();
            currentRow = -tshape.minY();
            currentCol = WIDTH / 2;
            if(isGameOver(tshape)){
                JOptionPane.showMessageDialog(null, "游戏结束！\n"+"总得分："+score+"\n",
                    "游戏结束",JOptionPane.INFORMATION_MESSAGE);
                exit(0);
            }
            processRowFull();
        }
        else{
            clearModel();
        }
        appendShape(tshape, false);
        return true;
    }

    public boolean isGameOver(LineShape shape){
        if(!tryMove(shape, currentRow, currentCol, MOVE_NO_TURN)){
            return true;
        }

        return false;
    }

    //控制俄罗斯方块向左移动一行，并更新数组
    public void moveLeft() {
        if (tryMove(tshape, currentRow, currentCol - 1, MOVE_NO_TURN)) {
            clearModel();
            appendShape(tshape, false);
        }
    }

    //控制俄罗斯方块向右移动一行，并更新数组
    public void moveRight() {
        if (tryMove(tshape, currentRow, currentCol + 1, MOVE_NO_TURN)) {
            clearModel();
            appendShape(tshape, false);
        }
    }

    //控制俄罗斯方块向左旋转90°，并更新数组
    public void rotateLeft4Shape() {
        //如果刷新出来的俄罗斯方块是Square-Shape，禁止旋转
        if (tshape.flag == 4)
            return;
        else if (tryMove(tshape, currentRow, currentCol, MOVE_LEFT_TURN)){
            clearModel();
            appendShape(tshape, false);
        }
    }

    //控制俄罗斯方块向右旋转90°，并更新数组
    public void rotateRight4Shape() {
        //如果刷新出来的俄罗斯方块是Square-Shape，禁止旋转
        if (tshape.flag == 4)
            return;
        else if(tryMove(tshape, currentRow, currentCol, MOVE_RIGHT_TURN)){
            clearModel();
            appendShape(tshape, false);
        }
    }

    //清空原来非永久填充的方块
    public void clearModel() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; ++j)
                if (data[i][j] > 0 && data[i][j] <= 10)
                    data[i][j] = 0;
        }
    }

    //判断移动是否会到达面板边界，或者移动是否会碰到永久填充的格子，是则返回错误，禁止移动
    //shape 要移动的俄罗斯方块,newRow 要移动到的新的行,newCol 要移动到的新的列
    //moveType 移动类型 0: 不旋转, 1: 左转, 2:右转
    public boolean tryMove(LineShape shape, int newRow, int newCol, int moveType) {
        int[][] shapeData = shape.getData();

        //先尝试旋转
        //如果是左右平移动作，不会执行这部分
        if(moveType == MOVE_LEFT_TURN)
            shape.rotateLeft();
        else if(moveType == MOVE_RIGHT_TURN)
            shape.rotateRight();

        //判断平移后的俄罗斯方块是否左右越界，如果没有越界则判断是否旋转会碰到永久填充的格子
        //无论是旋转还是左右平移都会执行这部分

//        if(newCol + shape.maxX() >= WIDTH || newCol + shape.minX() < 0){
//            for (int i = 0; i < Shape.COUNT; ++i) {
//                if (data[newRow + shapeData[i][1]][newCol + shapeData[i][0]] > 1) {
//                    return false;
//                }
//            }
//            return false;
//        }
        //判断旋转后的俄罗斯方块是否越界，如果没有越界则判断是否旋转会碰到永久填充的格子
        //俄罗斯方块在一个4*4的行列标号从-1开始的矩阵中
        //每一个俄罗斯方块的中心块在4*4俄罗斯矩阵中是（0,0)
        //考虑极端情况，当一个4*4俄罗斯矩阵紧贴面板左上角，俄罗斯方块中心块的在面板中的行列号都至少为1
        if (newRow + shape.maxY() < HEIGHT && newRow + shape.minY() >= 0
                && newCol + shape.maxX() < WIDTH && newCol + shape.minX() >= 0) {

            for (int i = 0; i < Shape.COUNT; ++i) {
                if (data[newRow + shapeData[i][1]][newCol + shapeData[i][0]] > 10) {
                    //条件成立，会碰到永久填充的格子
                    //将尝试旋转的俄罗斯方块转回去，返回错误
                    if (moveType == MOVE_LEFT_TURN)
                        shape.rotateRight();
                    else if (moveType == MOVE_RIGHT_TURN)
                        shape.rotateLeft();

                    return false;
                }
            }

            //如果不越界，也不会移动后碰到永久填充的格子，更新在面板中坐标，返回正确，允许移动
            currentRow = newRow;
            currentCol = newCol;

            return true;
        }

        //将尝试旋转的俄罗斯方块转回去，返回错误
        if(moveType == MOVE_LEFT_TURN)
            shape.rotateRight();
        else if(moveType == MOVE_RIGHT_TURN)
            shape.rotateLeft();
        return false;
    }

    //row为要消除的行，从0开始
    public void eraseLine(int row){
        for(int i = row; i > 0; --i){
            for(int j = 0; j < WIDTH; j++){
                data[i][j] = data[i-1][j];
            }
        }
    }
    //判断行row是否已经填满,row为要判断的行
    public boolean isRowFull(int row){
        for(int i = 0; i<WIDTH; ++i){
            if(data[row][i] <= 1)
                return false;
        }

        return true;
    }

    //消除行i
    public void processRowFull(){
        for(int i=0; i<HEIGHT; ++i){
            if(isRowFull(i)){
                eraseLine(i);
                midpanel.increasescore();
            }
        }
    }
}