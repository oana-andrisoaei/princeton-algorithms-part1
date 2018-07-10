import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private static final boolean OPEN = true;

	private final WeightedQuickUnionUF qu;
	private final WeightedQuickUnionUF quTopOnly;
	private boolean[][] network;
	private final int networkSize;
	private int openSites;
		
	public Percolation(int size) {
		if (size <= 0)
			throw new java.lang.IllegalArgumentException();
		openSites = 0;
		networkSize = size;
		qu = new WeightedQuickUnionUF(size * size + 2);
		quTopOnly = new WeightedQuickUnionUF(size * size + 1);
		network = new boolean[size + 1][size + 1];
	}
	
	public void open(int row, int col) {
		if (row == 1)
		{
			qu.union(0, col);
			quTopOnly.union(0,  col);
		}
		
		if (row == networkSize) {
			qu.union(networkSize * networkSize + 1, getQuIndex(row, col));
		}
		
		if (!isOpen(row, col)) {
			network[row][col] = OPEN;
			openSites++;
		}
		
		// NORTH
		if (row - 1 > 0 && isOpen(row - 1, col)) {
			qu.union(getQuIndex(row, col), getQuIndex(row - 1, col));
			quTopOnly.union(getQuIndex(row, col), getQuIndex(row - 1, col));
		}
		// WEST
		if (col - 1 > 0 && isOpen(row, col - 1)) {
			qu.union(getQuIndex(row, col), getQuIndex(row,  col - 1));
			quTopOnly.union(getQuIndex(row, col), getQuIndex(row,  col - 1));
		}
		// SOUTH
		if (row +1 <= networkSize && isOpen(row + 1, col)) {
			qu.union(getQuIndex(row, col), getQuIndex(row + 1,  col));
			quTopOnly.union(getQuIndex(row, col), getQuIndex(row + 1,  col));
		}
		// EAST
		if (col + 1 <= networkSize && isOpen(row, col + 1)) {
			qu.union(getQuIndex(row, col), getQuIndex(row,  col + 1));
			quTopOnly.union(getQuIndex(row, col), getQuIndex(row,  col + 1));
		}
	}
	
	public boolean isOpen(int row, int col) {
		validateSite(row, col);
		return network[row][col] == OPEN;
	}
	   
	public boolean isFull(int row, int col) {
		validateSite(row, col);
		return quTopOnly.connected(getQuIndex(row, col), 0);
	}
	
	public int numberOfOpenSites() {
		return openSites;
	}
	   
	public boolean percolates() {
		return qu.connected(0, networkSize * networkSize + 1);
	}
	
	private void validateSite(int row, int col) {
		if (row < 1 || row > networkSize || col < 1 || col > networkSize)
			throw new java.lang.IllegalArgumentException();
	}
	
	private int getQuIndex(int row, int col) {
		return (row - 1) * networkSize + col;
	}
	
	public static void main(String[] args) {

	}
}
