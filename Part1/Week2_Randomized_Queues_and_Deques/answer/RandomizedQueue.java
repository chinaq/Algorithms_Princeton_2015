import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private class RandomInterator implements Iterator<Item>
    {
        private int i;
        private int[] poses;
        
        public RandomInterator() {
            if (sz > 1) {               
               poses = new int[sz];
               for (int j = 0; j < sz; j++)
                   poses[j] = j;
               StdRandom.shuffle(poses, 0, sz -1);
            }
            i = sz -1;           
        }
        
        
        public boolean hasNext() { 
            return i >= 0;
        }
        
        public Item next() { 
            if (!hasNext())
                throw new NoSuchElementException();
            return items[poses[i--]];
        }
        
        public void remove() { 
            throw new UnsupportedOperationException();
        }
    }    
    
    private Item[] items;
    private int sz = 0;
    
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }                 
    
    // is the queue empty?
    public boolean isEmpty() {
        return sz == 0;
    }


    // return the number of items on the queue
    public int size() {
        return sz;
    }                        
    
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (sz == items.length)
            resize(2*sz);
        items[sz++] = item;
    }



    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        
        setLastOneRandmom();
            
        Item temp = items[sz - 1];
        items[sz - 1] = null;
        sz--;
        
        if (sz > 0 && sz == items.length/4) 
            resize(items.length/2);
        return temp;
    }


    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int i = StdRandom.uniform(sz);
        return items[i];
    }                     
   
    
    public Iterator<Item> iterator() {
        return new RandomInterator();
    }
    
    
    
    
    
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < sz; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }
    
    
    private void setLastOneRandmom() {
        if (sz <= 1)
            return;
        int r = StdRandom.uniform(sz);
        Item temp = items[sz - 1];
        items[sz - 1] = items[r];
        items[r] = temp;        
    }
    
    
    
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) rq.enqueue(item);
            else if (!rq.isEmpty()) StdOut.print(rq.sample() + " ");
        }
        StdOut.println();
        
        for (String s: rq) {
            StdOut.print(s + " ");
            StdOut.println();
            
            for (String s2: rq)
                StdOut.print(s2 + " ");
            StdOut.println();        
        }
        
        StdOut.println("(" + rq.size() + " left on rQueue)");
    }
    
}