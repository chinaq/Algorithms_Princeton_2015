import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class TestRotater extends TestCase {
    
    /**
     * A test method.
     * (Replace "X" with a name describing the test.  You may write as
     * many "testSomething" methods in this class as you wish, and each
     * one will be called when running JUnit over this class.)
     */
    public void testContraRotate_3X5() {
        //arrange
        int lenX = 3;
        int lenY = 5;
        int[][] oInts = new int[lenX][lenY];
        
        int count = 0;
        for(int i = 0; i < lenX; i++) {
            for(int j = 0; j < lenY; j++) {
                oInts[i][j] = count++;
            }
        } 
        
        Rotater r = new Rotater(lenX, lenY);
        int[][] rInts = new int[lenY][lenX];
        
        //action
        Position pos;
        for(int i = 0; i < lenX; i++) {
            for(int j = 0; j < lenY; j++) {
                pos = r.contraRotate(i, j);
                rInts[pos.getX()][pos.getY()] = oInts[i][j];
            }
        }

        //assert
        assertEquals(4, rInts[0][0]); assertEquals(9, rInts[0][1]); assertEquals(14, rInts[0][2]);        
        assertEquals(3, rInts[1][0]); assertEquals(8, rInts[1][1]); assertEquals(13, rInts[1][2]);
        assertEquals(2, rInts[2][0]); assertEquals(7, rInts[2][1]); assertEquals(12, rInts[2][2]);        
        assertEquals(1, rInts[3][0]); assertEquals(6, rInts[3][1]); assertEquals(11, rInts[3][2]);    
        assertEquals(0, rInts[4][0]); assertEquals(5, rInts[4][1]); assertEquals(10, rInts[4][2]);
    }
    
    
    public void testContraRotate_5X3() {
        //arrange
        int lenX = 5;
        int lenY = 3;
        int[][] oInts = new int[lenX][lenY];
        
        int count = 0;
        for(int i = 0; i < lenX; i++) {
            for(int j = 0; j < lenY; j++) {
                oInts[i][j] = count++;
            }
        } 
        
        Rotater r = new Rotater(lenX, lenY);
        int[][] rInts = new int[lenY][lenX];
        
        //action
        Position pos;
        for(int i = 0; i < lenX; i++) {
            for(int j = 0; j < lenY; j++) {
                pos = r.contraRotate(i, j);
                rInts[pos.getX()][pos.getY()] = oInts[i][j];
            }
        }

        //assert
        assertEquals(2, rInts[0][0]); assertEquals(5, rInts[0][1]); assertEquals(8, rInts[0][2]); assertEquals(11, rInts[0][3]); assertEquals(14, rInts[0][4]);      
        assertEquals(1, rInts[1][0]); assertEquals(4, rInts[1][1]); assertEquals(7, rInts[1][2]); assertEquals(10, rInts[1][3]); assertEquals(13, rInts[1][4]); 
        assertEquals(0, rInts[2][0]); assertEquals(3, rInts[2][1]); assertEquals(6, rInts[2][2]); assertEquals( 9, rInts[2][3]); assertEquals(12, rInts[2][4]); 
    }
    
    
    
    
    
    
    
    
    
    
    
     public void testRotate_3X5() {
        //arrange
        int lenX = 3;
        int lenY = 5;
        int[][] oInts = new int[lenX][lenY];
        
        int count = 0;
        for(int i = 0; i < lenX; i++) {
            for(int j = 0; j < lenY; j++) {
                oInts[i][j] = count++;
            }
        } 
        
        Rotater r = new Rotater(lenX, lenY);
        int[][] rInts = new int[lenY][lenX];
        
        //action
        Position pos;
        for(int i = 0; i < lenX; i++) {
            for(int j = 0; j < lenY; j++) {
                pos = r.rotate(i, j);
                rInts[pos.getX()][pos.getY()] = oInts[i][j];
            }
        }

        //assert
        assertEquals(10, rInts[0][0]); assertEquals(5, rInts[0][1]); assertEquals(0, rInts[0][2]);        
        assertEquals(11, rInts[1][0]); assertEquals(6, rInts[1][1]); assertEquals(1, rInts[1][2]);
        assertEquals(12, rInts[2][0]); assertEquals(7, rInts[2][1]); assertEquals(2, rInts[2][2]);        
        assertEquals(13, rInts[3][0]); assertEquals(8, rInts[3][1]); assertEquals(3, rInts[3][2]);    
        assertEquals(14, rInts[4][0]); assertEquals(9, rInts[4][1]); assertEquals(4, rInts[4][2]);
    }
    
    
     
    public void testRotate_5X3() {
        //arrange
        int lenX = 5;
        int lenY = 3;
        int[][] oInts = new int[lenX][lenY];
        
        int count = 0;
        for(int i = 0; i < lenX; i++) {
            for(int j = 0; j < lenY; j++) {
                oInts[i][j] = count++;
            }
        } 
        
        Rotater r = new Rotater(lenX, lenY);
        int[][] rInts = new int[lenY][lenX];
        
        //action
        Position pos;
        for(int i = 0; i < lenX; i++) {
            for(int j = 0; j < lenY; j++) {
                pos = r.rotate(i, j);
                rInts[pos.getX()][pos.getY()] = oInts[i][j];
            }
        }

        //assert
        assertEquals(12, rInts[0][0]); assertEquals( 9, rInts[0][1]); assertEquals(6, rInts[0][2]); assertEquals(3, rInts[0][3]); assertEquals(0, rInts[0][4]);      
        assertEquals(13, rInts[1][0]); assertEquals(10, rInts[1][1]); assertEquals(7, rInts[1][2]); assertEquals(4, rInts[1][3]); assertEquals(1, rInts[1][4]); 
        assertEquals(14, rInts[2][0]); assertEquals(11, rInts[2][1]); assertEquals(8, rInts[2][2]); assertEquals(5, rInts[2][3]); assertEquals(2, rInts[2][4]); 
    }
}
