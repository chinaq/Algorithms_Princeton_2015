import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    
    private byte[][] grid;  //位1:open, 位2:head, 位3:tail
    private WeightedQuickUnionUF uf;
    private boolean isPercolates;
    
    
    //初始化一个n*n的表格； create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        if (N <= 0)
            throw new java.lang.IllegalArgumentException("N is illegal");
        n = N;        
       
        grid = new byte[n+1][n+1];
        uf = new WeightedQuickUnionUF(n*n+2);
        isPercolates = false;
    }
       
    //开启该点； (row i, column j) if it is not open already   
    public void open(int i, int j)
    {
        checkIndex(i, j);
        connectFourdire(i, j);            
    }
   
    //该点是否已开启； is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        checkIndex(i, j);
        return (grid[i][j] & 1) == 1;
    }
    
    //该点是否连接到了顶点； is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        checkIndex(i, j);
        byte root = getRoot(i, j);
        if ((root & 2) == 2)
            return true;
        return false;
    }

   //系统是否已经贯穿；does the system percolate?
    public boolean percolates()
    {
        return isPercolates;
    }
   
      
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
   
    private int indexToX(int index) {
        return (index-1) / n +1;
    }   
    
    private int indexToY(int index) {
        int y = index % n;
        if (y == 0)
            y = n;
        return y;
    }
   
    private void connectFourdire(int i, int j) {
        byte status = 1;  //00000001
        if (i == 1)
            status |= 2;  //00000010
        if (i == n)
            status |= 4;  //00000100
        
        status |= connect(i, j, i-1, j);
        status |= connect(i, j, i+1, j);
        status |= connect(i, j, i, j-1);
        status |= connect(i, j, i, j+1);
        
        int rootIndex = uf.find(xyTo1D(i, j));
        int rootX = indexToX(rootIndex);
        int rootY = indexToY(rootIndex);
        grid[rootX][rootY] |= status;
        grid[i][j] = grid[rootX][rootY];
        
        checkIsPercolates(rootX, rootY); 
    }
   
    
    private void checkIsPercolates(int i, int j) {
        if (!isPercolates && (grid[i][j] & 6) == 6)  //00000110
            isPercolates = true;
    }
    
   
    private byte connect(int i, int j, int x, int y) {
        if (x >= 1 && x <= n && y >= 1 && y <= n && (grid[x][y] & 1) == 1) { 
            byte oRoot = getRoot(x, y);
            int des = xyTo1D(x, y);
            int sour = xyTo1D(i, j);
            uf.union(sour, des);
            
            //返回root的status
            return oRoot;
        }
        return 0;
    }
      
    
    private byte getRoot(int i, int j) {
        int rootIndex = uf.find(xyTo1D(i, j));
        byte root = grid[indexToX(rootIndex)][indexToY(rootIndex)];
        return root;
    }
    
    
   
}