import junit.framework.TestCase;
import java.util.ArrayList;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class TestBoard extends TestCase {
    
    ////////////// 测试 Dimension() ///////////////
    public void testDimension() {
        Board board = new Board(new int[][]{{0, 1}, {2, 3}});
        assertEquals(2, board.dimension());
    }
    
    /////////////// 测试 Hamming() ////////////////////
    public void testHamming() {
        Board board = new Board(new int[][]{{0, 1}, {2, 3}});
        assertEquals(3, board.hamming());
    }
    
    public void testHamming_0() {
        Board board = new Board(new int[][]{{1, 2}, {3, 0}});
        assertEquals(0, board.hamming());
    }
    
    
    //////////// manhattan() ////////////////////
    public void testManhattan() {
        Board board = new Board(new int[][]{{0, 1}, {2, 3}});
        assertEquals(4, board.manhattan());
    }
    
    public void testManhattan_0() {
        Board board = new Board(new int[][]{{1, 2}, {3, 0}});
        assertEquals(0, board.manhattan());
    }
    
    /////////// 测试isGoal ///////////////////
    public void testIsGoal() {
        Board board = new Board(new int[][]{{1, 2}, {3, 0}});
        assertTrue(board.isGoal());
    }
    
    public void testIsGoal_false() {
        Board board = new Board(new int[][]{{2, 1}, {3, 0}});
        assertFalse(board.isGoal());
    }
    
    
    
    ///////////// 测试twin /////////////
    public void testTwin() {
        Board board = new Board(new int[][]{{1, 2}, {3, 0}});
        Board twin = board.twin();
        assertEquals(2, twin.manhattan());
    }
    
    
    public void testTwin_middle_0() {
        Board board = new Board(new int[][]{{1, 0, 3}, {4, 5, 6}, {7, 8, 2}});
        Board expected = new Board(new int[][]{{1, 0, 3}, {5, 4, 6}, {7, 8, 2}});
        Board twin = board.twin();
        assertTrue(twin.toString(), expected.equals(twin));
    }
    
    
    
    ////////////// 测试equals ////////
    public void testEquals() {
        Board board1 = new Board(new int[][]{{1, 2}, {3, 0}});
        Board board2 = new Board(new int[][]{{1, 2}, {3, 0}});
        assertTrue(board1.equals(board2));
    }
    
    public void testEquals_false() {
        Board board1 = new Board(new int[][]{{1, 2}, {3, 0}});
        Board board2 = new Board(new int[][]{{2, 1}, {3, 0}});
        assertFalse(board1.equals(board2));
    }
        
        
    ////////// 测试neighbors ////////////
    public void testNeighbors() {
        //Arrange
        Board board = new Board(new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 8, 5}});
        Iterable<Board> neighbors= board.neighbors();
        ArrayList<Board> expected = new ArrayList<Board>();
        expected.add(new Board(new int[][]{{1, 0, 3}, {4, 2, 6}, {7, 8, 5}}));
        expected.add(new Board(new int[][]{{1, 2, 3}, {4, 8, 6}, {7, 0, 5}}));
        expected.add(new Board(new int[][]{{1, 2, 3}, {0, 4, 6}, {7, 8, 5}}));
        expected.add(new Board(new int[][]{{1, 2, 3}, {4, 6, 0}, {7, 8, 5}}));
        
        //Action
        int count = 0;
        for (Board b: neighbors) {
            //Assert
            assertTrue(expected.contains(b));
            count++;
        }
        assertEquals(4, count);
    }
    
    
    public void testNeighbors_up() {
        //Arrange
        Board board = new Board(new int[][]{{1, 0, 3}, {4, 5, 6}, {7, 8, 2}});
        Iterable<Board> neighbors= board.neighbors();
        ArrayList<Board> expected = new ArrayList<Board>();
        expected.add(new Board(new int[][]{{0, 1, 3}, {4, 5, 6}, {7, 8, 2}}));        
        expected.add(new Board(new int[][]{{1, 5, 3}, {4, 0, 6}, {7, 8, 2}}));               
        expected.add(new Board(new int[][]{{1, 3, 0}, {4, 5, 6}, {7, 8, 2}}));
        
        //Action
        int count = 0;
        for (Board b: neighbors) {
            //Assert
            assertTrue(expected.contains(b));
            count++;
        }
        assertEquals(3, count);
    }
    
    
    public void testNeighbors_down() {
        //Arrange
        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}});
        Iterable<Board> neighbors= board.neighbors();
        ArrayList<Board> expected = new ArrayList<Board>();
        expected.add(new Board(new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}}));        
        expected.add(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}}));               
        expected.add(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}));
        
        //Action
        int count = 0;
        for (Board b: neighbors) {
            //Assert
            assertTrue(expected.contains(b));
            count++;
        }
        assertEquals(3, count);
    }
    
    
    public void testNeighbors_left() {
        //Arrange
        Board board = new Board(new int[][]{{1, 2, 3}, {0, 5, 6}, {7, 8, 4}});
        Iterable<Board> neighbors= board.neighbors();
        ArrayList<Board> expected = new ArrayList<Board>();
        expected.add(new Board(new int[][]{{0, 2, 3}, {1, 5, 6}, {7, 8, 4}})); 
        expected.add(new Board(new int[][]{{1, 2, 3}, {7, 5, 6}, {0, 8, 4}}));        
        expected.add(new Board(new int[][]{{1, 2, 3}, {5, 0, 6}, {7, 8, 4}})); 
        
        //Action
        int count = 0;
        for (Board b: neighbors) {
            //Assert
            assertTrue(expected.contains(b));
            count++;
        }
        assertEquals(3, count);
    }
    
}
