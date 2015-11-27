import edu.princeton.cs.algs4.Stack;

public class DepthFirst {
    
    //head 0,-1;
    //tail 0,-2;
    
    
    private boolean[][] marked;
    private boolean isHeadMarked;
    private boolean isTailMarked;
    private Stack<Position> reversePost;
    private int row;
    private int col;
    
    public DepthFirst(double[][] energies, int row, int col) {
        this.row = row;
        this.col = col;        
        this.reversePost = new Stack<Position>();
        this.marked = new boolean[row][col];
        
        dfs(energies, 0, -1);        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!marked[i][j]) dfs(energies, i, j);
            }
        }
        
    }
    
    
    private void dfs (double[][] energies, int i, int j) {
        if (j >= 0) marked[i][j] = true;
        
        if (j == -1) {             //如果是头
            isHeadMarked = true;
            for (int y = col - 1; y >= 0; y--) {
                if(!marked[0][y])
                    dfs(energies, 0, y);
            }
        } 
        else if (j == -2) {        //如果是尾
            isTailMarked = true;
        }
        else if (i == row - 1) {   //如果是最后一排
            if (!isTailMarked)
                dfs(energies, 0, -2);
        }
        else {                     //一般情况
            int x = i + 1;
            for (int y = j+1; y >= j -1; y--) {
                if (y >= 0 && y < col && !marked[x][y]) 
                    dfs(energies, x, y);
            }  
        }
        reversePost.push(new Position(i, j));
    }    
    
    
    public Iterable<Position> reversePost() {
        return reversePost;
    }
}