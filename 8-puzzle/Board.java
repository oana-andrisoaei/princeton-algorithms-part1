import edu.princeton.cs.algs4.Queue;

public class Board {
	private final int dimension;
	private final int[][] blocks;
	private int blankRow;
	private int blankCol;
	
	public Board(int[][] blocks) {
		if (blocks == null)
			throw new IllegalArgumentException();
		int row = blocks.length;
		int col = blocks[0].length;
		if (row != col)
			throw new IllegalArgumentException();
		
		dimension = row;
		this.blocks = blocks;
	}

	public int dimension() {
		return dimension;
	}
	
	public int hamming() {
		int outOfPlaceBlocks = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (i == dimension - 1 && j == dimension - 1) {
					if (blocks[i][j] != 0)
						outOfPlaceBlocks++;
				}
				else if (blocks[i][j] != ((i - 1) * dimension + j + 1))
					outOfPlaceBlocks++;
			}
		}
		return outOfPlaceBlocks;
	}
	
	public int manhattan() {
		throw new IllegalArgumentException();
	}
	
	public boolean isGoal() {
		return hamming() == 0;
	}
	
	public Board twin() {
		throw new IllegalArgumentException();
	}
	
	public boolean equals(Object other) {
		if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        
        Board otherBoard = (Board) other;
        if (otherBoard.dimension() == this.dimension()) return false;
        
        for (int i = 0; i < this.dimension(); i++) {
        	for (int j = 0; j < this.dimension(); j++) {
        		if (otherBoard.blocks[i][j] != this.blocks[i][j])
        			return false;
        	}
        }
        
        return true;        
	}
	
	public Iterable<Board> neighbors() {
		Queue<Board> neighbors = new Queue<Board>();
		
		findBlankBlock();
		
		if (blankRow - 1 > 0) {
			int[][] neighbor = blocks.clone();
			swapLeft(neighbor, blankRow, blankCol);
			neighbors.enqueue(new Board(neighbor));	
		}
		
		if (blankRow + 1 < dimension) {
			int[][] neighbor = blocks.clone();
			swapRight(neighbor, blankRow, blankCol);
			neighbors.enqueue(new Board(neighbor));	
		}
		
		if (blankCol + 1 < dimension) {
			int[][] neighbor = blocks.clone();
			swapDown(neighbor, blankRow, blankCol);
			neighbors.enqueue(new Board(neighbor));	
		}
		
		if (blankCol - 1 > 0) {
			int[][] neighbor = blocks.clone();
			swapUp(neighbor, blankRow, blankCol);
			neighbors.enqueue(new Board(neighbor));	
		}
		
		return neighbors;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
	    s.append(dimension + "\n");
	    for (int i = 0; i < dimension; i++) {
	        for (int j = 0; j < dimension; j++) {
	            s.append(String.format("%2d ", blocks[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
	
	private void swapUp(int[][] tBlocks, int i, int j) {
		if (j - 1 < 0) throw new IllegalArgumentException();
		int temp = tBlocks[i][j];
		tBlocks[i][j] = tBlocks[i][j-1];
		tBlocks[i][j-1] = temp;
	}
	
	private void swapDown(int[][] tBlocks, int i, int j) {
		if (j + 1 > dimension) throw new IllegalArgumentException();
		int temp = tBlocks[i][j];
		tBlocks[i][j] = tBlocks[i][j+1];
		tBlocks[i][j+1] = temp;
	}
	
	private void swapLeft(int[][] tBlocks, int i, int j) {
		if (i - 1 < 0) throw new IllegalArgumentException();
		int temp = tBlocks[i][j];
		tBlocks[i][j] = tBlocks[i-1][j];
		tBlocks[i-1][j] = temp;
	}
	
	private void swapRight(int[][] tBlocks, int i, int j) {
		if (i + 1 > dimension) throw new IllegalArgumentException();
		int temp = tBlocks[i][j];
		tBlocks[i][j] = tBlocks[i+1][j];
		tBlocks[i+1][j] = temp;
	}
	
	private void findBlankBlock() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] == 0) {
					this.blankRow = i;
					this.blankCol = j;
					return;
				}
			}
		}
	}
	
    public static void main(String[] args) {

    }
}