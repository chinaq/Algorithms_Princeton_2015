import junit.framework.TestCase;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class TestKdTree extends TestCase {
    
    ////////////////////测试 isEmpty() /////////////////////
    
    public void testIsEmpty_Is() {
        //arrange
        KdTree set = new KdTree();                
        //action
        boolean result = set.isEmpty();        
        //assert
        assertEquals(true, result);        
    }

        
    public void testIsEmpty_Not() {
        //arrange
        KdTree set = new KdTree();                
        set.insert(new Point2D(1, 2));
        //action
        boolean result = set.isEmpty();        
        //assert
        assertEquals(false, result);        
    }
    
    
    
    
    ////////////////////// 测试 size() ////////////////////
    
    public void testSize_0() {
        //arrange
        KdTree set = new KdTree();                
        //action
        int result = set.size();        
        //assert
        assertEquals(0, result);                
    }
    
        
    public void testSize_1() {
        //arrange
        KdTree set = new KdTree();                
        set.insert(new Point2D(1, 2));
        //action
        int result = set.size();        
        //assert
        assertEquals(1, result);               
    }
    
    
    public void testSize_2() {
        //arrange
        KdTree set = new KdTree();                
        set.insert(new Point2D(1, 2));
        set.insert(new Point2D(2, 3));
        //action
        int result = set.size();        
        //assert
        assertEquals(2, result);               
    }
    
    
    
        //////////////////// 测试contains() ////////////////
    
    public void testContains_false() {
        //arrange
        KdTree set = new KdTree();                
        //action
        boolean result = set.contains(new Point2D(0.1, 0.2));        
        //assert
        assertFalse(result);   
    }
    
    
    public void testContains_false_insertDiff() {
        //arrange
        KdTree set = new KdTree();  
        set.insert(new Point2D(0.05, 0.01));
        //action
        boolean result = set.contains(new Point2D(0.1, 0.2));        
        //assert
        assertFalse(result);   
   }
    
    
   public void testContains_true() {
        //arrange
        KdTree set = new KdTree();  
        set.insert(new Point2D(0.05, 0.01));
        //action
        boolean result = set.contains(new Point2D(0.05, 0.01));        
        //assert
        assertTrue(result);   
   }
    
    
   
      ///////////////////  测试 range() ///////////////////
   
      public void testRange_0() {
          //arrange
          KdTree set = new KdTree();  
          //action
          Iterable<Point2D> result = set.range(new RectHV(0.1, 0.1, 0.2, 0.2));        
          int total = 0;
          for(Point2D p: result) {
              total++;
          }        
          //assert
          assertEquals(0, total);
      }
   
      
      public void testRange_1() {
          //arrange
          KdTree set = new KdTree();
          set.insert(new Point2D(0.15, 0.15));
          //action
          Iterable<Point2D> result = set.range(new RectHV(0.1, 0.1, 0.2, 0.2));        
          int total = 0;
          for(Point2D p: result) {
              assertEquals(new Point2D(0.15, 0.15), p);
              total++;
          }        
          //assert
          assertEquals(1, total);
      }
   
   
      public void testRange_2() {
          //arrange
          KdTree set = new KdTree();
          set.insert(new Point2D(0.15, 0.15));
          set.insert(new Point2D(0.16, 0.16));
          SET<Point2D> expected = new SET<Point2D>();
          expected.add(new Point2D(0.15, 0.15));
          expected.add(new Point2D(0.16, 0.16));
          
          //action
          Iterable<Point2D> result = set.range(new RectHV(0.1, 0.1, 0.2, 0.2));        
          int total = 0;          
          for(Point2D p: result) {
              expected.contains(p);
              total++;
          }        
          //assert
          assertEquals(2, total);
      }
      
      
      
            ////////////////////// 测试 nearest() //////////////
      
      public void testNearest() {
          //arrange
          KdTree set = new KdTree();
          set.insert(new Point2D(0.15, 0.15));
          set.insert(new Point2D(0.16, 0.16));          
          set.insert(new Point2D(0.17, 0.17));
          //action
          Point2D aim = new Point2D(0.161, 0.161);
          Point2D result = set.nearest(aim);
          //assert
          assertEquals(new Point2D(0.16, 0.16), result);
      }
      
}
