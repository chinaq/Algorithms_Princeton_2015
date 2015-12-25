import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;


public class TrieSetQ {
    public static final int Diff = 65;
    
    public static final int NotExist = -1;
    public static final int IsIn = 0;
    public static final int IsWord = 1;
    
    private static final int R = 26;        // alphbet

    private Node root;      // root of trie
    private int N;          // number of keys in trie
    private Stack<Node> searchedNodes; //node for search
    private Stack<Character> stackChars;

    // R-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    
    public TrieSetQ() {        
        searchedNodes = new Stack<Node>();
        //stackChars = new Stack<Character>();
    }
    
    
    public TrieSetQ(String[] dic) { 
        this();
        for(int i = 0; i < dic.length; i++) {
            add(dic[i]);
        }
    }
    

    //add
    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString) N++;
            x.isString = true;
        }
        else {
            char c = (char)(key.charAt(d) - Diff);
            x.next[c] = add(x.next[c], key, d+1);
        }
        return x;
    }


    public int forwardSearch(char c) {
        if (searchedNodes.isEmpty()) searchedNodes.push(root);
        Node now = searchedNodes.peek().next[c];
        searchedNodes.push(now);
        
//        stackChars.push((char)(c + Diff));        
//        StdOut.print("stack: ");
//        for (char ch: stackChars) {
//            StdOut.print(ch);
//            StdOut.print(" ");
//        }
//        StdOut.println();
        
        if      (now == null)   return NotExist;
        else if (!now.isString) return IsIn;
        else                    return IsWord;        
    }
    
    
    public void back() {
        if (!searchedNodes.isEmpty()) searchedNodes.pop();
        
//        StdOut.print("pop: ");
//        StdOut.print(stackChars.pop());
//        StdOut.println();
    }
    



    
     public boolean contains(String key) {
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = (char)(key.charAt(d) - Diff);
        return get(x.next[c], key, d+1);
    }    
    
}
