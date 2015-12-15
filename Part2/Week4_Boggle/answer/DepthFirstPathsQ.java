import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
//import java.util.StringBuilder;

public class DepthFirstPathsQ {
    private char[] tempChars;
    private boolean[][] marked;    // marked[v] = is there an s-v path?
    private SET<String> allWords;

    /**
     * Computes a path between <tt>s</tt> and every other vertex in graph <tt>G</tt>.
     * @param G the graph
     * @param s the source vertex
     */
    public DepthFirstPathsQ(BoggleBoard board) {
        tempChars = new char[board.rows() * board.cols() * 2];
        allWords = new SET<String>();
        marked = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                dfs(board, i, j, 0);
            }
        }
        
    }

    // depth first search from v
    private void dfs(BoggleBoard board, int x, int y, int depth) {
        tempChars[depth] = board.getLetter(x, y);
        if (tempChars[depth] == 'Q') tempChars[++depth] = 'U';        
        allWords.add(new String(tempChars, 0, depth + 1));       
        
        marked[x][y] = true;        
        for (int i = x - 1; i <= x + 1 ; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if(       i >= 0 && i < board.rows()
                       && j >= 0 && j < board.cols()
                       && !marked[i][j] ) {
                    dfs(board, i, j, depth + 1);         
                }
            }
        }        
        marked[x][y] = false;
    }


    public Iterable<String> getAllWords() {
        return allWords;
    }
    
    

    /**
     * Unit tests the <tt>DepthFirstPaths</tt> data type.
     */
    public static void main(String[] args) {
        String file = args[0];
        BoggleBoard b = new BoggleBoard(file);
        DepthFirstPathsQ dfs = new DepthFirstPathsQ(b);
        
        int i = 0;
        StdOut.println("dfs显示所有可能的单词:");
        for (String str: dfs.getAllWords()) {
            i++;
//            if (str.equals("UNIT"))
//                StdOut.println(str);
            //StdOut.println(str);
        }
        StdOut.printf("总共%d个单词\n", i);
    }

}