import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.DirectedCycle;
import java.util.ArrayList;


public class WordNet {

    private SAP sap;
    private Digraph G;
    private LinearProbingHashST<String, ArrayList<Integer>> st;
    private String[] keys;
    
   // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        ifNullThrowException(synsets);
        ifNullThrowException(hypernyms);        
        
        st = new LinearProbingHashST<String,  ArrayList<Integer>>();
        In in = new In(synsets);
        
        int len = 0;
        int No;
        String[] words;
        ArrayList<String> tempKeys = new ArrayList<String>();
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(",");
            tempKeys.add(a[1]);
            
            No = Integer.parseInt(a[0]);
            words = a[1].split(" ");
            for (String word: words) {            
                if (st.contains(word)) {
                    st.get(word).add(No);
                } else {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(No);
                    st.put(word, list);
                }   
            }
            len++;
        }
        
        keys = new String[len];
        tempKeys.toArray(keys);
           
        G = new Digraph(len);
        in = new In(hypernyms);
                
        while (in.hasNextLine()) {
            String temp = in.readLine(); 
            String[] a = temp.split(",");            
            int v = Integer.parseInt(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = Integer.parseInt(a[i]);
                G.addEdge(v, w);
            }
        }
        
        ifNotDagThrowException(G);        
        sap = new SAP(G);
    }
    

   // returns all WordNet nouns
    public Iterable<String> nouns() {
        return st.keys();
    }

   // is the word a WordNet noun?
    public boolean isNoun(String word) {
        ifNullThrowException(word);        
        return st.contains(word);
    }

   // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {   
        ifNullThrowException(nounA);
        ifNullThrowException(nounB);     
        ifNounNotInThrowException(nounA);
        ifNounNotInThrowException(nounB);
        return sap.length(st.get(nounA), st.get(nounB));
    }

    
    
   // a synset (second field of synsets.txt)
   // that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        ifNullThrowException(nounA);
        ifNullThrowException(nounB);            
        ifNounNotInThrowException(nounA);
        ifNounNotInThrowException(nounB);
        int No = sap.ancestor(st.get(nounA), st.get(nounB));
        return keys[No];
    }

    
    private void ifNullThrowException(Object ob) {
        if (ob == null) 
            throw new NullPointerException();
    }
    
    
    private void ifNounNotInThrowException(String word) {
        if (!isNoun(word)) 
            throw new IllegalArgumentException();
    }
    
    
    private void ifNotDagThrowException(Digraph g) {
        DirectedCycle cycle = new DirectedCycle(g);
        if (cycle.hasCycle() || !isOnlyOneRoot(g))
            throw new IllegalArgumentException();
    }
    
    private boolean isOnlyOneRoot(Digraph g) {
        int total = 0;
        for (int i = 0; i < g.V(); i++) {
            if (g.outdegree(i) == 0)
                total++;
        }
        
        return total == 1;
    }
    
    
//   // do unit testing of this class
//    public static void main(String[] args) {
//        WordNet wordNet = new WordNet(args[0], args[1]);
//    }
    
}