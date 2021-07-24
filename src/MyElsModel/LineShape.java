package MyElsModel;

/*设计俄罗斯方块*/

import java.util.Random;

public class LineShape extends Shape {
    int flag;
    public LineShape(){
        //随机刷新一个俄罗斯方块
        Random r = new Random();
        flag = r.nextInt(7);

        switch (flag){
            //Z-Shape
            case 0:
                data = new int[][]{{0,-1},{0,0},{-1,0},{-1,1}};
                content = 1;
                break;
            //S-Shape
            case 1:
                data = new int[][]{{0,-1},{0,0},{1,0},{1,1}};
                content = 2;
                break;
            //Line-Shape
            case 2:
                data = new int[][]{{0,-1},{0,0},{0,1},{0,2}};
                content = 3;
                break;
            //T-Shape
            case 3:
                data = new int[][]{{-1,0},{0,0},{1,0},{0,1}};
                content = 4;
                break;
            //Square-Shape
            case 4:
                data = new int[][]{{0,0},{1,0},{0,1},{1,1}};
                content = 5;
                break;
            //L-Shape
            case 5:
                data = new int[][]{{-1,-1},{0,-1},{0,0},{0,1}};
                content = 6;
                break;
            //Mirrored-L-Shape
            case 6:
                data =new int[][]{{1,-1},{0,-1},{0,0},{0,1}};
                content = 7;
                break;
            default:
                throw new RuntimeException("no shape");
        }
    }
}
