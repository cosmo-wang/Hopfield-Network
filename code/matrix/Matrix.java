package matrix;

public class Matrix {
	private int[][] matrix;
	private int size;
	
	public static final int DEFAULT_CAPACITY = 38;
	
	public Matrix(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix.length != matrix[0].length) {
			throw new IllegalArgumentException();
		}
		this.matrix = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		for(int i = 0; i < matrix.length; i++) {
		    this.matrix[i] = matrix[i].clone();
		}
		this.size = matrix.length;
	}
	
	public Matrix(int size) {
		this.matrix = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		for (int i = 0; i < size; i++) {
			matrix[i] = new int[size];
		}
		this.size = size;
	}
	
	public int size() {
		return size;
	}
	
	public int getEntry(int i, int j) {
		return matrix[i - 1][j - 1];
	}
	
	public void add(Matrix other) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] += other.getEntry(i + 1, j + 1);
			}
		}
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < size; i++) {
			String currentRow = "";
			for (int j = 0; j < size; j++) {
				currentRow += matrix[i][j] + " ";
			}
			result += currentRow.trim() + "\n";
		}
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Matrix)) {
			return false;
		}
		Matrix otherMatrix = (Matrix) other;
		if (this.size() != otherMatrix.size()) {
			return false;
		} else {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (matrix[i][j] != otherMatrix.matrix[i][j]) {
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
