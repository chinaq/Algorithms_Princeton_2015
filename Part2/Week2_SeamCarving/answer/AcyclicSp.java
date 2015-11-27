import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class AcyclicSp {
    //head 0,-1;
    //tail 0,-2;
    
    private int row;
    private int col;
    private Position[][] posTo;
    private double[][] distTo;
    private Position posToTail;
    private double distToTail;
    
    public AcyclicSp(double[][] energies, int row, int col) {
        this.row = row;
        this.col = col;
        this.posTo = new Position[row][col];
        this.distTo = new double[row][col];
        this.distToTail = Double.POSITIVE_INFINITY;
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j <col; j++) {
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        
        Toplogic top = new Toplogic(energies, row, col);
//        for (Position p: top.order()) {
//            StdOut.printf(p.getX() + "," + p.getY() + "\n");
//        }
//        StdOut.printf("toplogic over");
        
        
        for (Position p: top.order()) {
//            StdOut.printf(p.getX() + "," + p.getY() + "\n");
            relax(energies, p);
        }
    }
    
    
    private void relax(double[][] energies, Position pos) {
        if (pos.getY() == -1 && pos.getX() == 0) {
            int i = 0;
            for (int j = col - 1; j >= 0; j--) {
                distTo[i][j] = energies[i][j]; 
                posTo[i][j] = pos;
            }
        }
        else if (pos.getX() == row - 1 && pos.getY() >=0 && pos.getY() < col) {
            int x = pos.getX();
            int y = pos.getY();                
            if (distToTail > distTo[x][y]) {
                distToTail = distTo[x][y];
                posToTail = pos;
            }  
        } else {
            int fX = pos.getX();
            int fY = pos.getY();
            
            int tX = fX + 1;
            for (int tY = fY+1; tY >= fY -1; tY--) {
                if (tY >= 0 && tY < col 
                        && distTo[tX][tY] > distTo[fX][fY] + energies[tX][tY]) { 
                    distTo[tX][tY] = distTo[fX][fY] + energies[tX][tY];
                    posTo[tX][tY] = pos;
                }
            }  
        }
        
    }
    
    public Stack<Position> pathToTail() {
        Stack<Position> path = new Stack<Position>();
        //StdOut.println("pathToTail - start");
        for (Position p = posToTail; p.getY() != -1; p = posTo[p.getX()][p.getY()]){
            //StdOut.println(p.getX() + "," + p.getY());
            path.push(p);
        }
        //StdOut.println("pathToTail - over");
        return path;
    }
    
}