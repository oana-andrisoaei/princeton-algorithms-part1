import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private final SearchNode goal;
    
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        
        MinPQ<SearchNode> pQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>();
        
        pQ.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));
        
        SearchNode min;
        SearchNode twinMin;
        
        while (true) {
        	min = pQ.delMin();
        	twinMin = twinPq.delMin();

            for (Board board : min.board.neighbors()) {      
                if (min.previous != null || !board.equals(min.board)) {
                    SearchNode node = new SearchNode(board, min.moves + 1, min);
                    if (node.board.isGoal()) {
                    	goal = node;
                    	return;
                    }
                    pQ.insert(node);
                }
            }
            
            for (Board board : twinMin.board.neighbors()) {
                if (twinMin.previous != null || !board.equals(twinMin.board)) {
                    SearchNode node = new SearchNode(board, twinMin.moves + 1, twinMin);
                    if (node.board.isGoal()) {
                    	goal = null;
                    	return;
                    }
                    twinPq.insert(node);
                }
            }
             
         }
    }     

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previous;
        private final int moves;
        private final int priority;

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
    
    public boolean isSolvable() {
    	return goal != null;
    }

    public int moves() {
        return goal.moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> stack = new Stack<Board>();
        
        SearchNode temp = goal;
        while (temp.previous != null) {
        	stack.push(temp.board);
        	temp = temp.previous;
        }
        
        return stack;
    }
}