import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    
    private WordNet wordNet;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }         
   
    
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDist = -1;
        String result = "";
        for (String noun: nouns) {            
            int tempDist = 0;
            for (String A: nouns) {
                wordNet.distance(noun, A);
                int dist = wordNet.distance(noun, A);
                tempDist += dist;
            }
            if (tempDist > maxDist) {
                maxDist = tempDist;
                result = noun;
            }
        }
        return result;
    }   
   
    
    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}