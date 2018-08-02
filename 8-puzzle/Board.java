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
	    this.blocks = new int[row][col];
	    for (int i = 0; i < row; i++) {
	        for (int j = 0; j < col; j++) {
	        	this.blocks[i][j] = blocks[i][j];
	        }
	    }
	}
	
	public int dimension() {
	    return dimension;
	}
	
	public int hamming() {
	    int outOfPlaceBlocks = 0;
		int k = 1;
		for (int i = 0; i < dimension; i++) {
	        for (int j = 0; j < dimension; j++) {
	        	if (blocks[i][j] != k && !(i == dimension - 1 && j == dimension - 1)) outOfPlaceBlocks++;
	        	k++;
	        }
		}
	   return outOfPlaceBlocks;
	}
	
	public int manhattan() {
        int sum = 0;
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
                if (blocks[i][j] != 0 && blocks[i][j] != j + i * dimension() + 1)
                    sum += calculateDistance(i, j, blocks[i][j]);
        return sum;
	}
	
	public boolean isGoal() {
	    return hamming() == 0;
	}
	
	public Board twin() {
	    for (int i = 0; i < dimension; i++) {
	        for (int j = 0; j < dimension; j++) {
	            if (blocks[i][j] != 0 && blocks[i][j+1] != 0) {
	                int[][] twinBoard = getClone();
	                swapDown(twinBoard, i, j);
	                return new Board(twinBoard);
	            }
	        }
	    }
	
	    throw new RuntimeException();
	}
	
	public boolean equals(Object other) {
	    if (other == this) return true;
	    if (other == null) return false;
	    if (other.getClass() != this.getClass()) return false;
	    
	    Board otherBoard = (Board) other;
	    if (otherBoard.dimension() != this.dimension()) return false;
	    
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
	    
	    if (blankRow - 1 >= 0) {
	        int[][] neighbor = getClone();
	        swapLeft(neighbor, blankRow, blankCol);
	        neighbors.enqueue(new Board(neighbor));	
	    }
	    
	    if (blankRow + 1 < dimension) {
	        int[][] neighbor = getClone();
	        swapRight(neighbor, blankRow, blankCol);
	        neighbors.enqueue(new Board(neighbor));	
	    }
	    
	    if (blankCol + 1 < dimension) {
	        int[][] neighbor = getClone();
	        swapDown(neighbor, blankRow, blankCol);
	        neighbors.enqueue(new Board(neighbor));	
	    }
	    
	    if (blankCol - 1 >= 0) {
	        int[][] neighbor = getClone();
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
	
	private int[][] getClone() {
	    int[][] clone = new int[dimension][dimension];
	    for (int i = 0; i < dimension; i++) {
	        for (int j = 0; j < dimension; j++) {
	            clone[i][j] = blocks[i][j];
	        }
	    }
	    return clone;
	}
	
    private int calculateDistance(int i, int j, int square) {
        square--;
        int horizontal = Math.abs(square % dimension() - j);
        int vertical = Math.abs(square / dimension() - i);
        return horizontal + vertical;
    }
}
