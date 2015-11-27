public class Toplogic {
    private Iterable<Position> order;
    
    public Toplogic(double[][] energies, int row, int col){
        DepthFirst dfs = new DepthFirst(energies, row, col);
            order = dfs.reversePost();
    }
    
    public Iterable<Position> order() {
        return order;
    }
}