import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;

public class Board {    
    
    private int pos0;
    private int n;
    private int len;
    private char[] blocks;
    
    // 初始化
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        len = n * n;
        this.blocks = new char[len];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int pos = i * n + j;
                this.blocks[pos] = (char) blocks[i][j];
                if (this.blocks[pos] == 0)
                    pos0 = pos;
            }
        }        
    }
    
    // 长宽
    // board dimension N       
    public int dimension() {
        return n;
    }                 
    
    // 汉明距离
    // number of blocks out of place       
    public int hamming() {
        int result = 0;
        for (int i = 0; i < len - 1; i++) {
            if (blocks[i] != i + 1)
                result++;
        }
        return result;
    }
    
    // 曼哈顿距离
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < len; i++) {
            if (blocks[i] != 0) {
                int nowX = i % n;
                int nowY = i / n;
                int expectX = (blocks[i]-1) % n;
                int expectY = (blocks[i]-1) / n;
                result += Math.abs(nowX - expectX) + Math.abs(nowY - expectY);
            }
        }
        return result;
    }
    
    // 是否为目标
    // is this board the goal board?
    public boolean isGoal() {
        return 0 == manhattan();
    }
    
    // 孪生盘
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] temp = getArray2D(blocks);
        
        boolean isBreak = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (temp[i][j] != 0 && temp[i][j+1] != 0) {                    
                    int t = temp[i][j];
                    temp[i][j] = temp[i][j+1];
                    temp[i][j+1] = t;
                    isBreak = true;
                    break;
                }                
            }
            if (isBreak) break;
        }
        return new Board(temp);
    }
    
    // 两盘是否相等
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y;
        if (this.len != that.blocks.length) return false;
        for (int i = 0; i < len; i++) {
            if (this.blocks[i] != that.blocks[i])
                return false;
        }
        return true;
    }
    
    // 所有相邻盘
    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> nbors = new ArrayList<Board>(); 
        if (pos0/n != 0) {
            int up = pos0 - n;
            nbors.add(getNeighbor(pos0, up)); 
        }
        if (pos0/n != n -1) {
            int down = pos0 + n;
            nbors.add(getNeighbor(pos0, down));
        }
        if (pos0 % n != 0) {
            int left = pos0 - 1;
            nbors.add(getNeighbor(pos0, left));
        }
        if (pos0 % n != n -1) {
            int right = pos0 + 1;
            nbors.add(getNeighbor(pos0, right));
        }
        return nbors;
    }
    
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        String lineSep = System.getProperty("line.separator", "\n"); 
        
        int[][] array2D = getArray2D(blocks);
        StringBuffer sb = new StringBuffer();
        sb.append(n);
        sb.append(lineSep);
        //str = n + lineSep;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" ");
                sb.append(array2D[i][j]);
                //str = str + " " + array2D[i][j];
            }
            //str = str + lineSep;
            sb.append(lineSep);
        }
        return sb.toString();
    }               

    // unit tests (not graded)
    public static void main(String[] args) {        
        Board board1 = new Board(new int[][]{{1, 2}, {3, 0}});
        Board board2 = new Board(new int[][]{{1, 2}, {3, 0}});
        StdOut.println(board1);
        StdOut.println(board2);
    } 




    private Board getNeighbor(int pos00, int posNeighbor) {
        char[] temp = new char[len];
        System.arraycopy(blocks, 0, temp, 0, len);
        char t = temp[pos00];
        temp[pos00] = temp[posNeighbor];
        temp[posNeighbor] = t;
        int[][] array2D = getArray2D(temp);
        return new Board(array2D);
    }
    
    private int[][] getArray2D(char[] arryay1D) {
        int[][] temp = new int[n][n];
        for (int i = 0; i < len; i++) {
            temp[i/n][i % n] = arryay1D[i];            
        }
        return temp;
    }
    


}