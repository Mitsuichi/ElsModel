package MyElsModel;

/*Shape是LineShape的父类*/

public abstract class Shape {
    protected int[][] data;
    public static final int COUNT = 4;
    protected int content = 0;

    public int[][] getData() {
        return data;
    }

    //获取一个俄罗斯方块最右边的小方块的列号
    public int maxX() {
        int max = -1;
        for (int i = 0; i < COUNT; ++i) {
            if (data[i][0] > max)
                max = data[i][0];
        }

        return max;
    }

    //获取一个俄罗斯方块最下面的小方块的行号
    public int maxY() {
        int max = -1;
        for (int i = 0; i < COUNT; ++i) {
            if (data[i][1] > max)
                max = data[i][1];
        }
        return max;
    }

    //获取一个俄罗斯方块最左边的小方块的列号
    public int minX() {
        int min = 2;
        for (int i = 0; i < COUNT; ++i) {
            if (data[i][0] < min)
                min = data[i][0];
        }

        return min;
    }

    //获取一个俄罗斯方块最上面的小方块的行号
    public int minY() {
        int min = 2;
        for (int i = 0; i < COUNT; ++i) {
            if (data[i][1] < min)
                min = data[i][1];
        }

        return min;
    }

    //逆时针左旋90°，规律为(x, y)  (y, -x)
    public void rotateLeft() {
        for (int i = 0; i < COUNT; ++i) {
            int tmp = data[i][1];
            data[i][1] = -data[i][0];
            data[i][0] = tmp;
        }
    }

    //顺时针右旋90°，规律为(x, y)  (-y, x)
    public void rotateRight() {
        for (int i = 0; i < COUNT; ++i) {
            int tmp = data[i][0];
            data[i][0] = -data[i][1];
            data[i][1] = tmp;
        }
    }

    public int getContent(){
        return content;
    }
}
