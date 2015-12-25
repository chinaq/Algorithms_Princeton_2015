import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MSDsuf {
    private static final int R             = 256;   // extended ASCII alphabet size
    private static final int CUTOFF        =  15;   // cutoff to insertion sort

    // do not instantiate
    private MSDsuf() { } 


    public static void sort(String a, int[] index) {
        int N = index.length;
        int[] aux = new int[N];
        sort(a, index, 0, N-1, 0, aux);
    }
    
    
    private static void sort (String a, 
         int[] index, int lo, int hi, int d, int[] aux) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(a, index, lo, hi, d);
            return;
        }

        // compute frequency counts
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a, index[i], d);
            count[c+2]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a, index[i], d);
            aux[count[c+1]++] = index[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++) 
            index[i] = aux[i - lo];


        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++)
            sort(a, index, lo + count[r], lo + count[r+1] - 1, d+1, aux);
    }
    
    
    
    // return dth character of s, -1 if d = length of string
    private static int charAt(String s, int startPos, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt((startPos + d) % s.length());
    }
    
    
    
        // insertion sort a[lo..hi], starting at dth character
    private static void insertion (String a, 
         int[] index, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a, index[j], index[j-1], d); j--)
                exch(a, index, j, j-1);
    }

    
    
    // is v less than w, starting at character d
    private static boolean less(String s, int startA, int startB, int d) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < s.length(); i++) {
            if (charAt(s, startA, i) < (charAt(s, startB, i))) return true;
            if (charAt(s, startA, i) > (charAt(s, startB, i))) return false;
        }
        return false;
    }
    
    
        // exchange a[i] and a[j]
    private static void exch(String a, int[] index, int i, int j) {
        int temp = index[i];
        index[i] = index[j];
        index[j] = temp;
    }
    
}