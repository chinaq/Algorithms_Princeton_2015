import junit.framework.TestCase;
import java.util.Arrays;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class TestPoint extends TestCase {
    
 
    //    测试slopeTo    /////////////////////////////////////
    
    public void test_slopeTo_Normal() {
        //0.Arrange
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);                
        //1.Action
        double slope = p1.slopeTo(p2);        
        //2.Assert
        assertEquals(1.0, slope);
    }
    
    public void test_slopeTo_Normal_2() {
        //0.Arrange
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 6);                
        //1.Action
        double slope = p1.slopeTo(p2);        
        //2.Assert
        assertEquals(6.0, slope);
    }
    
    
    public void test_slopeTo_horizontal() {
        //0.Arrange
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 1);                
        //1.Action
        double slope = p1.slopeTo(p2);        
        //2.Assert
        assertEquals(0.0, slope);
    }
    
    
    public void test_slopeTo_horizontal_2() {
        //0.Arrange
        Point p1 = new Point(2, 1);
        Point p2 = new Point(1, 1);                
        //1.Action
        double slope = p1.slopeTo(p2);        
        //2.Assert
        assertEquals(0.0, slope);
    }
        
    public void test_slopeTo_vertical() {
        //0.Arrange
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 2);                
        //1.Action
        double slope = p1.slopeTo(p2);        
        //2.Assert
        assertEquals(Double.POSITIVE_INFINITY, slope);
    }
        
    
    public void test_slopeTo_samePoint() {
        //0.Arrange
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);                
        //1.Action
        double slope = p1.slopeTo(p2);        
        //2.Assert
        assertEquals(Double.NEGATIVE_INFINITY, slope);
    }
    
    
    
    
    // 测试compareTo        ////////////////////////////////////////
    
    public void test_compareTo_normal_Less() {
        //0.Arrange
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);                
        //1.Action
        int result = p1.compareTo(p2);        
        //2.Assert
        assertEquals(-1, result);
    }
    
    public void test_compareTo_normal_Great() {
        //0.Arrange
        Point p1 = new Point(2, 2);
        Point p2 = new Point(1, 1);                
        //1.Action
        int result = p1.compareTo(p2);        
        //2.Assert
        assertEquals(1, result);
    }
    
    public void test_compareTo_normal_Equal() {
        //0.Arrange
        Point p1 = new Point(2, 2);
        Point p2 = new Point(2, 2);                
        //1.Action
        int result = p1.compareTo(p2);        
        //2.Assert
        assertEquals(0, result);
    }
    
    public void test_compareTo_onlyY_Less() {
        //0.Arrange
        Point p1 = new Point(2, 3);
        Point p2 = new Point(1, 5);                
        //1.Action
        int result = p1.compareTo(p2);        
        //2.Assert
        assertEquals(-1, result);
    }
    
    public void test_compareTo_onlyX_Less() {
        //0.Arrange
        Point p1 = new Point(1, 5);
        Point p2 = new Point(2, 5);                
        //1.Action
        int result = p1.compareTo(p2);        
        //2.Assert
        assertEquals(-1, result);
    }
    
    public void test_compareTo_onlyY_Greater() {
        //0.Arrange
        Point p1 = new Point(1, 5);
        Point p2 = new Point(1, 3);                
        //1.Action
        int result = p1.compareTo(p2);        
        //2.Assert
        assertEquals(1, result);
    }
    
    public void test_compareTo_onlyX_Greater() {
        //0.Arrange
        Point p1 = new Point(2, 5);
        Point p2 = new Point(1, 5);                
        //1.Action
        int result = p1.compareTo(p2);        
        //2.Assert
        assertEquals(1, result);
    }
    
    
    
    // 测试 sloperOrder        ///////////////////////
    
    public void test_sloperOrder_TwoPoints() {
        //0.Arrange
        Point p = new Point(0, 0);
        Point[] ps = new Point[2];
        ps[0] = new Point(1, 6);                
        ps[1] = new Point(1, 5);
        //1.Action
        Arrays.sort(ps, p.slopeOrder());        
        //2.Assert
        assertEquals(5.0, ps[0].slopeTo(p));
        assertEquals(6.0, ps[1].slopeTo(p));
    }
        //0.Arrange
        //1.Action
        //2.Assert
}
