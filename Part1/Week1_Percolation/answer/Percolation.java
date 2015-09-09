import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int head;
    private int tail;
    
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    
    //初始化一个n*n的表格； create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        if (N <= 0)
            throw new java.lang.IllegalArgumentException("N is illegal");
        n = N;
        head = 0;
        tail = n*n+1;
       
        grid = new int[n+1][n+1];
        uf = new WeightedQuickUnionUF(n*n+2);
    }
       
    //开启该点； (row i, column j) if it is not open already   
    public void open(int i, int j)
    {
        checkIndex(i, j);
        grid[i][j] = 1;
        connectFourdire(i, j);            
        connectHeadOrTail(i, j);
    }
   
    //该点是否已开启； is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        checkIndex(i, j);
        return grid[i][j] == 1;
    }
    
    //该点是否连接到了顶点； is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        checkIndex(i, j);
        if (isOpen(i, j) && uf.connected(head, xyTo1D(i, j)))
            return true;
        return false;
    }

   //系统是否已经贯穿；does the system percolate?
    public boolean percolates()
    {
        return uf.connected(head, tail);
    }
   
   
    //public static void main(String[] args   // test client (optional)
   
   
    private void checkIndex(int i, int j) {   
        if (i <= 0 || i > n) 
            throw new 
            java.lang.IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > n) 
            throw new 
            java.lang.IndexOutOfBoundsException("col index j out of bounds");   
    }
   
   
    private int xyTo1D(int i, int j) {
        return n*(i-1)+j;
    }   
   
   
    private void connectFourdire(int i, int j) {
        connect(i, j, i-1, j);
        connect(i, j, i+1, j);
        connect(i, j, i, j-1);
        connect(i, j, i, j+1);
    }
   
   
    private void connect(int i, int j, int x, int y) {
        if (x >= 1 && x <= n && y >= 1 && y <= n && grid[x][y] == 1) {
            int des = xyTo1D(x, y);
            int sour = xyTo1D(i, j);
            uf.union(sour, des);
        }
    }
      
    private void connectHeadOrTail(int i, int j) {
        if (i == 1) {
            uf.union(head, xyTo1D(i, j));
        }
        if (i == n) {
            uf.union(tail, xyTo1D(i, j));
        }       
    }
   
}