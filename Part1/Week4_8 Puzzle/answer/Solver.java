import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
    
    
    private class Node implements Comparable<Node> {
        private Board board;
        private Node last;
        private boolean isTwin;
        private int moves;
        private int hamming;
        private int manhattan;
        private int priority;
        
        public Node(Board board, Node last, boolean isTwin, int moves) {
            this.board = board;
            this.last = last;
            this.isTwin = isTwin;
            this.moves = moves;
            this.hamming = board.hamming();
            this.manhattan = board.manhattan();
            this.priority = manhattan + moves;
        }
        
        public int compareTo(Node that) {
            if (this.priority < that.priority) return -1;
            if (this.priority > that.priority) return 1;
            if (this.hamming < that.hamming) return -1;
            if (this.hamming > that.hamming) return 1;
            return 0;
        }                
        
    }
    
    
    private MinPQ<Node> pq;
    private int mvs;
    private boolean isSolved;
    private Node lastNode;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();        
        pq = new MinPQ<Node>();
        Node first = new Node(initial, null, false, 0);
        Node firstTwin = new Node(initial.twin(), null, true, 0);
        pq.insert(first);
        pq.insert(firstTwin);
        
        findSolution();
    }
    
    
    private void findSolution() {        
        while (true) {
            Node node = pq.delMin();
            boolean isgoal = node.board.isGoal();
            if (isgoal) {
                if (node.isTwin) {
                    isSolved = false;
                    mvs = -1;
                } else {
                    isSolved = true;
                    mvs = node.moves;
                    lastNode = node;
                } 
                break;
            } else {
                for (Board b: node.board.neighbors()) {                    
                    if (node.last == null || !b.equals(node.last.board)) {
                        pq.insert(new Node(b, node, node.isTwin, node.moves+1));
                    }
                }
            }
        }
    }
    
    
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolved;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return mvs;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> boards = new Stack<Board>();
        Node node = lastNode;
        while (node != null) {
            boards.push(node.board);
            node = node.last;
        }
        if (boards.size() == 0)
            return null;
        return boards;
    }      
    
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}