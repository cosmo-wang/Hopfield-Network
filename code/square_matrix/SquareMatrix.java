package square_matrix;

/**
 * SquareMatrix class presents a mathematical SquareMatrix. 
 * @author Cosmo Wang
 *
 */
public class SquareMatrix {
	private int[][] SquareMatrix;
	private int size;
	
	public static final int DEFAULT_CAPACITY = 38;
	
	public SquareMatrix(int[][] SquareMatrix) {
		if (SquareMatrix == null || SquareMatrix.length == 0 || SquareMatrix.length != SquareMatrix[0].length) {
			throw new IllegalArgumentException();
		}
		this.SquareMatrix = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		for(int i = 0; i < SquareMatrix.length; i++) {
		    this.SquareMatrix[i] = SquareMatrix[i].clone();
		}
		this.size = SquareMatrix.length;
	}
	
	public SquareMatrix(int size) {
		this.SquareMatrix = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		for (int i = 0; i < size; i++) {
			SquareMatrix[i] = new int[size];
		}
		this.size = size;
	}
	
	public int size() {
		return size;
	}
	
	public int getEntry(int i, int j) {
		return SquareMatrix[i - 1][j - 1];
	}
	
	public void setEntry(int i, int j, int value) {
		if (i <= size && j <= size) {
			SquareMatrix[i - 1][j - 1] = value;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public void add(SquareMatrix other) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				SquareMatrix[i][j] += other.getEntry(i + 1, j + 1);
			}
		}
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < size; i++) {
			String currentRow = "";
			for (int j = 0; j < size; j++) {
				if (SquareMatrix[i][j] < 0) {
					currentRow += SquareMatrix[i][j] + "   ";
				} else {
					currentRow += " " + SquareMatrix[i][j] + "   ";
				}
			}
			result += currentRow.substring(0, currentRow.length() - 2) + "\n";
		}
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SquareMatrix)) {
			return false;
		}
		SquareMatrix otherSquareMatrix = (SquareMatrix) other;
		if (this.size() != otherSquareMatrix.size()) {
			return false;
		} else {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (SquareMatrix[i][j] != otherSquareMatrix.SquareMatrix[i][j]) {
						return false;
					}
				}
			}
			return true;
		}
	}
	
	@Override
	public int hashCode() {
		return this.hashCode();
	}
}
