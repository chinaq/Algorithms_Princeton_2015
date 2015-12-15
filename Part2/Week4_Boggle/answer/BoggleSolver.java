import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;


public class BoggleSolver
{
    private String[] arrayDic;
    private LinearProbingHashST<String, Integer> dictionary;    
    
    // Initializes the data structure using the given array of strings 
    // as the dictionary.
    // (You can assume each word in the dictionary contains 
    // only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.arrayDic = dictionary;
        this.dictionary = new LinearProbingHashST<String, Integer>();        
        for (int i = 0; i < dictionary.length; i++) {
            this.dictionary.put(dictionary[i], i);            
        }
    }

        
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        //生成所有可出现的单词，使用dfs处理board
        DepthFirstPathsQ dfs = new DepthFirstPathsQ(board);
        Iterable<String> result = dfs.getAllWords(); 
        
        ArrayList<String> temp = new ArrayList<String>();
        for (String str: result) {
            if (str.length() >=3 && dictionary.contains(str))
                temp.add(str);
        }
        
        return temp;
    }

        
        
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int len = word.length();
        if      (len <= 2) return 0;
        else if (len <= 4) return 1;
        else if (len == 5) return 2;
        else if (len == 6) return 3;
        else if (len == 7) return 5;
        else               return 11;
    }
    
    
    
    
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

    
    
}