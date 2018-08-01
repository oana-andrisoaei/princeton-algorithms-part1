import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    public class SearchNode implements Comparable<SearchNode>{
        private Board board;
        private SearchNode previous;
        private int moves;
        private int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = moves + board.manhattan();
        }
        
        public int compareTo(SearchNode that) {
        	if (this.priority < that.priority) return -1;
        	else if (this.priority > that.priority) return 1;
        	else return 0;
        }
    }

    private MinPQ<SearchNode> PQ;
    private MinPQ<SearchNode> twinPQ;
    
    private SearchNode goal;
    
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        
        PQ = new MinPQ<SearchNode>();
        twinPQ = new MinPQ<SearchNode>();
        
        PQ.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial, 0, null));
        
        SearchNode min = PQ.delMin();
        SearchNode twinMin = twinPQ.delMin();

        while(!min.board.isGoal() && !twinMin.board.isGoal()) {
            for (Board board : min.board.neighbors()) {      
                if (min.previous != null || !board.equals(min.board)) {
                    SearchNode node = new SearchNode(board, min.moves + 1, min);
                    PQ.insert(node);
                }
            }
            
            for (Board board : twinMin.board.neighbors()) {
                if (twinMin.previous != null ||!board.equals(twinMin.board)) {
                    SearchNode node = new SearchNode(board, twinMin.moves + 1, twinMin);
                    twinPQ.insert(node);
                }
            }
             
             min = PQ.delMin();
             twinMin = twinPQ.delMin();
         }
        if (min.board.isGoal())  
        	goal = min;
        else                     
        	goal = null; 
    }     

    public boolean isSolvable() {
    	return goal != null;
    }


    public int moves() {
        return goal.moves;
    }

    public Iterable<Board> solution() {
        if(!isSolvable()) return null;
        Stack<Board> stack = new Stack<Board>();
        
        SearchNode temp = goal;
        while (temp.previous != null) {
        	stack.push(temp.board);
        	temp = temp.previous;
        }
        
        temp = null;
        return stack;
    }  
}