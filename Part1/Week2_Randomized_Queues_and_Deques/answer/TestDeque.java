import junit.framework.TestCase;
import java.util.NoSuchElementException;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class TestDeque extends TestCase {
    
    /**
     * A test method.
     * (Replace "X" with a name describing the test.  You may write as
     * many "testSomething" methods in this class as you wish, and each
     * one will be called when running JUnit over this class.)
     */
    
    
    ////////////测试size/////////////    
    
    public void test_AddFirst_GetSize() {
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addFirst(1);                

        //Assert
        assertEquals(1, dq.size());
    }
    
    
    public void test_AddFirst_AddLast_GetSize() {
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addFirst(1);                
        dq.addLast(2);
        
        //Assert
        assertEquals(2, dq.size());
    }
    
    
    public void test_AddFirst_removeFirst_GetSize() {
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();   
        assertEquals(0, dq.size());
        
        //Action
        dq.addFirst(1);                
        dq.removeFirst();
        
        //Assert
        assertEquals(0, dq.size());
    }
    
    
    public void test_AddFirst_removeLast_GetSize() {
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addFirst(1);                
        dq.removeLast();
        
        //Assert
        assertEquals(0, dq.size());
    }
    
    
    
    public void test_AddLast_removeFirst_GetSize() {
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addLast(1);                
        dq.removeFirst();
        
        //Assert
        assertEquals(0, dq.size());
    }
    
    
    public void test_AddLast_removeLast_GetSize() {
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addLast(1);                
        dq.removeLast();
        
        //Assert
        assertEquals(0, dq.size());
    }

/////////////测试iterator////////
    public void test_iterator() { 
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addLast(3);                
        dq.addFirst(2);
        dq.addLast(4);                
        dq.addFirst(1);
        
        //Assert
        int expected = 0;
        for(int i: dq)  {
            expected++;
            assertEquals(expected, i);
        }
    }

    
    public void test_iterator_with_move() { 
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addLast(3);                
        dq.addFirst(2);
        dq.addLast(4);                
        dq.addFirst(1);
        dq.removeLast();                
        dq.removeFirst();
        
        //Assert
        int expected = 1;
        for(int i: dq)  {
            expected++;
            assertEquals(expected, i);
        }
    }
    
    
    public void test_iterator_with_null() { 
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addLast(3);                
        dq.addFirst(2);
        dq.addLast(4);                
        dq.addFirst(1);
        dq.removeLast();                
        dq.removeFirst();        
        dq.removeLast();                
        dq.removeFirst();
        
        //Assert
        for(int i: dq)  {
            fail();
        }
    }
    
    
    
    
    //////////remove的exception
    
    public void test_remove_exception() { 
        //Arrange
        Deque<Integer> dq = new Deque<Integer>();        
        
        //Action
        dq.addLast(3);                
        dq.addFirst(2);
        dq.addLast(4);                
        dq.addFirst(1);
        dq.removeLast();                
        dq.removeFirst();        
        dq.removeLast();                
        dq.removeFirst();
        
        //Assert
        try {
            dq.removeFirst();
            fail();
        } catch (NoSuchElementException e){}             
    }
    
}


        //Arrange
        //Action
        //Assert