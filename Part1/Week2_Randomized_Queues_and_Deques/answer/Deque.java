import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    
    private class Node {
        private Item item;
        private Node next;
        private Node pre;
    }
    
    
    private class MyIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext() { 
            return current != null;
        }
        
        public Item next() { 
            if (current == null)
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove() { 
            throw new UnsupportedOperationException();
        }
    }    
    
    
    private int sz = 0;
    private Node first = null;
    private Node last = null;
    
    
    // construct an empty deque
    public Deque() {
       
    }
        
    // is the deque empty?
    public boolean isEmpty() {
        return sz == 0;
    }
        
    // return the number of items on the deque
    public int size() {
        return sz;
    }                        
        
    // add the item to the front
    public void addFirst(Item item) {
        checkItem(item);
        
        Node temp = new Node();
        temp.item = item;                
        
        if (sz == 0) {            
            last = temp;
            first = temp;
        } else {
            Node oldFirst = first;  
            oldFirst.pre = temp;
            first = temp;
            first.next = oldFirst;                   
        }                
        sz++;
    }
        
    // add the item to the end
    public void addLast(Item item) {
        checkItem(item);
        
        Node temp = new Node();
        temp.item = item;        
        
        if (sz == 0) {
            last = temp;
            first = temp;
        } else {
            last.next = temp;
            temp.pre = last;
            last = last.next;
        }                
        sz++;
    }    
     
    
    
    
    // remove and return the item from the front
    public Item removeFirst() {
        checkIsOkToRemove();        
        sz--;        
        Item item = first.item;
        if (!needAndHadCleanFirstLast()) {
            first = first.next;    
            first.pre = null;
        }        
        return item;            
    }                
        
    // remove and return the item from the end
    public Item removeLast() {
        checkIsOkToRemove();
        sz--;
        Item item = last.item;
        if (!needAndHadCleanFirstLast()) {                    
            last = last.pre; 
            last.next = null;            
        }
        return item;   
    }                 
    
    
    // return an iterator over items in order from front to end    
    public Iterator<Item> iterator() {
        return new MyIterator();
    }
    
    
    
    
    
    
    
    private void checkItem(Item item) {
        if (item == null)
            throw new NullPointerException();
    }
    
    
    private void checkIsOkToRemove() {
        if (isEmpty())
            throw new NoSuchElementException();
    }
    
    
    private boolean needAndHadCleanFirstLast() {
        if (sz == 0) {
            first = null;
            last = null;
            return true;
        }
        return false;
    }    
    
}