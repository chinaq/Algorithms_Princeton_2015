import junit.framework.TestCase;
import java.util.NoSuchElementException;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class TestRandomizedQueue extends TestCase {
    
    /**
     * A test method.
     * (Replace "X" with a name describing the test.  You may write as
     * many "testSomething" methods in this class as you wish, and each
     * one will be called when running JUnit over this class.)
     */
    
    
    //////////测试add和size
    
    public void test_add_size() { 
        //Arrange
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();        
        
        //Action //Assert
        rq.enqueue(1);
        assertEquals(1, rq.size());
        rq.enqueue(1);
        assertEquals(2, rq.size());
        rq.enqueue(1);
        assertEquals(3, rq.size());
        rq.enqueue(1);
        assertEquals(4, rq.size());
        rq.enqueue(1);
        assertEquals(5, rq.size());
        rq.enqueue(1);
        assertEquals(6, rq.size());
        rq.enqueue(1);
        assertEquals(7, rq.size());
        rq.enqueue(1);
        assertEquals(8, rq.size());
        rq.enqueue(1);
        assertEquals(9, rq.size());
    }
    
    
    
        //////////测试add和size
    
    public void test_dequeque_size() { 
        //Arrange
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();        
        
        //Action 
        for(int i = 0; i < 10; i++){
            rq.enqueue(1);    
        }
        
        //Assert
        assertEquals(10, rq.size());
        rq.dequeue();
        assertEquals(9, rq.size());
        rq.dequeue();
        assertEquals(8, rq.size());
        rq.dequeue();
        assertEquals(7, rq.size());
        rq.dequeue();
        assertEquals(6, rq.size());
        rq.dequeue();
        assertEquals(5, rq.size());
        rq.dequeue();
        assertEquals(4, rq.size());
        rq.dequeue();
        assertEquals(3, rq.size());
        rq.dequeue();
        assertEquals(2, rq.size());
        rq.dequeue();
        assertEquals(1, rq.size());
        rq.dequeue();
        assertEquals(0, rq.size());
        try {
            rq.dequeue();
            fail();
        } catch (NoSuchElementException e) {}
    }
    
    
        
        //////////测试add和sample
    
    public void test_add_sample() { 
        //Arrange
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();        
        
        //Action 
        for(int i = 0; i < 10; i++){
            rq.enqueue(1);    
        }
        
        //Assert
        for(int i = 0; i < 10; i++){
            assertEquals(1, (int)rq.sample());
        }
    }
    
    
    
    
            
        //////////测试add和dequeue
    
    public void test_add_dqueue() { 
        //Arrange
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();        
        
        //Action 
        for(int i = 0; i < 10; i++){
            rq.enqueue(1);    
        }
        
        //Assert
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue()); 
        assertEquals(1, (int)rq.dequeue());         
    }
    
    
    
    
            /////////////测试iterator
    
    public void test_iterator_with_empty() { 
        //Arrange
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();        
        
        //Action
        int total = 0;
        for (int i: rq)
            total++;
        
        //Assert
        assertEquals(0, total);
        
    }
    
    
    
        //Arrange
        //Action
        //Assert
    
}
