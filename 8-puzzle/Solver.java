import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.ArrayList;

public class Solver {

    private final Board goal;
    private final ArrayList<Board> boards;
    
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        MinPQ<SearchNode> pQ = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        boards = new ArrayList<Board>();

        pQ.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));

        while (!pQ.min().board.isGoal() && !twinPq.min().board.isGoal()) {
            SearchNode node = pQ.delMin();
            for (Board board : node.board.neighbors()) {
                if (!wasAlreadyFound(node, board))
                pQ.insert(new SearchNode(board, node.moves + 1, node));
            }

            SearchNode twinNode = twinPq.delMin();
            for (Board board : twinNode.board.neighbors()) {
                if (!wasAlreadyFound(twinNode, board))
                    twinPq.insert(new SearchNode(board, twinNode.moves + 1, twinNode));
            }
        }
        SearchNode node = pQ.delMin();
        boards.add(node.board);
        while ((node = node.previous) != null) 
            boards.add(0, node.board);

        goal = boards.get(boards.size() - 1);
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
    	return goal.isGoal();
    }

    public int moves() {
        return boards.size() - 1;
    }

    public Iterable<Board> solution() {
        if (isSolvable())
            return boards;
        else return null;
    }

    private boolean wasAlreadyFound(SearchNode node, Board board) {
        while (node.previous != null) {
            if (node.previous.board.equals(board))
                return true;
            node = node.previous;
        }
        return false;
}
}