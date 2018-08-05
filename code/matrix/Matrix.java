package matrix;

import java.util.Arrays;

public class Matrix {
	private double[][] matrix;
	private int size;
	
	public static final int DEFAULT_CAPACITY = 38;
	
	public Matrix(double[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix.length != matrix[0].length) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < matrix.length; i++ ) {
			this.matrix[i] = Arrays.copyOf(matrix[i], DEFAULT_CAPACITY);
		}
		this.size = matrix.length;
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
}
