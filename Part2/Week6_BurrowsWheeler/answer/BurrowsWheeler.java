import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue; 
import java.util.ArrayList;
import java.util.Arrays;



public class BurrowsWheeler {
    
    // apply Burrows-Wheeler encoding, reading from standard input 
    // and writing to standard output
    public static void encode() {
        //StdOut.println("encode");
        
        int first = 0;
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        for (int i = 0; i < s.length(); i++) {
            if (csa.index(i) == 0) {
                first = i;
            }
        }
        BinaryStdOut.write(first);
        
        for (int i = 0; i < s.length(); i++) {            
            int tempPos = ((csa.index(i)) + s.length() - 1) % s.length();
            BinaryStdOut.write(s.charAt(tempPos));     
        }           
        BinaryStdOut.close();
    }

    
    // apply Burrows-Wheeler decoding, reading from standard input
    // and writing to standard output
    public static void decode() {
        int R = 256;   // extend ASCII alphabet size
        int first = BinaryStdIn.readInt();
        
        //使用key-index count 计算next和第一列
         // compute frequency counts
         int[] count = new int[R+1];             
         ArrayList<Character> t = new ArrayList<Character>();
         while (!BinaryStdIn.isEmpty()) {
             char c = BinaryStdIn.readChar();
             count[c + 1]++;
             t.add(c);
         }         
         int n = t.size();
         int[] next = new int[n];
         char[] f = new char[n];         
         
         // compute cumulates
         for (int r = 0; r < R; r++)
             count[r+1] += count[r];

         //得到next和第一列
         for (int i = 0; i < n; i++) {
             char c = t.get(i);
             f[count[c]] = c;
             next[count[c]] = i;
             count[c]++;
         }
                  
        // 开始重建原编码
        int tempNext = first;
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(f[tempNext]);
            tempNext = next[tempNext];
        }
        BinaryStdOut.close();       
         
    }
    
    
    

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
    }
}