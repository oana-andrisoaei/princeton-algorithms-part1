import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Tester {
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In("grid4x4.txt");
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);
	    StdOut.println(initial);
	    StdOut.println("Neighbors: ");
	    for(Board an : initial.neighbors()) {
		    StdOut.println(an);
	    }
	}
}
