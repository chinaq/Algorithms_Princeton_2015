import junit.framework.TestCase;
import java.util.Iterator;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class TestBaseballElimination extends TestCase {
    
    /**
     * A test method.
     * (Replace "X" with a name describing the test.  You may write as
     * many "testSomething" methods in this class as you wish, and each
     * one will be called when running JUnit over this class.)
     */
    public void testNumberOfTeams() {
        //arrange
        BaseballElimination be = new BaseballElimination("teams4.txt");        
        //action
        int result = be.numberOfTeams();        
        //assert
        assertEquals(4, result);
    }    
    
    
    public void testTeams() {
        //arrange
        String[] expected = new String[4];
        expected[0] = "Atlanta";
        expected[1] = "Philadelphia";  
        expected[2] = "New_York";      
        expected[3] = "Montreal";   
        BaseballElimination be = new BaseballElimination("teams4.txt"); 
        
        //action
        Iterable<String> result = be.teams(); 
        
        //assert
        int i = 0;
        for (String team: result) {
            assertEquals(expected[i], team);
            i++;
        }        
    }  
    
    public void testWins() {
        //arrange
        BaseballElimination be = new BaseballElimination("teams4.txt");        
        //action
        int result = be.wins("Philadelphia");        
        //assert
        assertEquals(80, result);
    }    

    public void testLoss() {
        //arrange
        BaseballElimination be = new BaseballElimination("teams4.txt");        
        //action
        int result = be.losses("New_York");        
        //assert
        assertEquals(78, result);
    }    

    public void testRemainings() {
        //arrange
        BaseballElimination be = new BaseballElimination("teams4.txt");        
        //action
        int result = be.remaining("Montreal");        
        //assert
        assertEquals(3, result);
    }   

    
    public void testAgainst() {
        //arrange
        BaseballElimination be = new BaseballElimination("teams4.txt");        
        //action
        int result = be.against("Atlanta", "New_York");        
        //assert
        assertEquals(6, result);
    }  
    
}
