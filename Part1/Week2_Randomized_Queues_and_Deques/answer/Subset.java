import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
    
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);        
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        
        String[] strs = StdIn.readAllStrings();
        StdRandom.shuffle(strs);
        
        for (int i = 0; i < k; i++) {
            rq.enqueue(strs[i]);
        }
               
        
        
//        int count = 0;
//        while (!StdIn.isEmpty()) {
//            count++;
//            String item = StdIn.readString();            
//            if (count > k && count > 1) {
//                int i = StdRandom.uniform(k+2);
//                if (i <= k) {                    
//                    rq.dequeue();
//                    rq.enqueue(item);
//                }
//            }else{
//                rq.enqueue(item);
//            }
//        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}