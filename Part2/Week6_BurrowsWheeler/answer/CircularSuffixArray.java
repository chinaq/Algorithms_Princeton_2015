import edu.princeton.cs.algs4.StdOut;


public class CircularSuffixArray {
    
    private int[] index;
    private int oLen;
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new java.lang.NullPointerException();
        
        if (s.length() == 0) {
            this.oLen = 0;
            return;
        }
            
        this.oLen = s.length();        
        index = new int[s.length()];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        } 
        //LSDsuf.sort(s, index);
        MSDsuf.sort(s, index);
    }  
        
    // length of s
    public int length() {
        return oLen;
    }                
    
    // returns index of ith sorted suffix
    public int index(int i) {
        if ( i < 0 || i >= oLen)
            throw new java.lang.IndexOutOfBoundsException();
        return index[i];
    }
    
    
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String str = "BABBBABBBB";
        CircularSuffixArray cs = new CircularSuffixArray(str);
        for (int i = 0; i < str.length(); i++) {
            StdOut.println(cs.index[i]);
        }
    }
}