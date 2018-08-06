package square_matrix;

/**
 * SquareMatrix class presents a mathematical square matrix.
 * @author Cosmo Wang
 *
 */
public class SquareMatrix {
	private int[][] matrix;  // 2D array used to store entries of the matrix.
	private int size;  // size of the matrix, i.e. number of rows and columns of the matrix
	
	// initial default capacity of the matrix
	public static final int DEFAULT_CAPACITY = 100;
	
	/**
	 * Constructor of the class. Constructs a new square matrix.
	 * @param matrix A 2D array that stores the entries to be added 
	 *               into the newly constructed matrix
	 * @effects constructs a new square matrix
	 */
	public SquareMatrix(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix.length != matrix[0].length) {
			throw new IllegalArgumentException();
		}
		this.matrix = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		for(int i = 0; i < matrix.length; i++) {
		    this.matrix[i] = matrix[i].clone();
		}
		this.size = matrix.length;
	}
	
	/**
	 * Constructor of the class. Constructs a new square matrix.
	 * @param size Specifies the size of the matrix.
	 * @effects constructs a new square matrix with given size
	 */
	public SquareMatrix(int size) {
		this.matrix = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		for (int i = 0; i < size; i++) {
			matrix[i] = new int[size];
		}
		this.size = size;
	}
	
	/**
	 * Returns the size of the matrix.
	 * @return size of the matrix
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns the given indexed row of the matrix using 1 based indexing.
	 * @param i index of the row wanted
	 * @return the ith row of the matrix
	 */
	public int[] getRow(int i) {
		int[] row = new int[size];
		row = matrix[i - 1].clone();
		return row;
	}
	
	/**
	 * Get a certain entry from the matrix using 1 based indexing.
	 * @param i row of the wanted entry
	 * @param j column of the wanted entry
	 * @throws IllegalArgumentException if given index exceeds size of the matrix
	 * @return A certain entry from the matrix
	 */
	public int getEntry(int i, int j) {
		if (i > size || j > size) {
			throw new IllegalArgumentException();
		}
		return matrix[i - 1][j - 1];
	}
	
	/**
	 * Set a certain entry from the matrix using 1 based indexing to given value.
	 * @param i row of the entry to be changed
	 * @param j column of the entry to be changed
	 * @param value value that the entry will be set to
	 * @throws IllegalArgumentException if given index exceeds size of the matrix
	 */
	public void setEntry(int i, int j, int value) {
		if (i <= size && j <= size) {
			matrix[i - 1][j - 1] = value;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Adds the current matrix to another matrix.
	 * @param other another matrix to be added
	 * @throws IllegalArgumentException if given matrix has different size with 
	 *         current matrix
	 */
	public void add(SquareMatrix other) {
		if (other.size != size) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] += other.getEntry(i + 1, j + 1);
			}
		}
	}
	
	/**
	 * Returns a string representation of the matrix.
	 * @return string representation of the matrix
	 */
	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < size; i++) {
			String currentRow = "";
			for (int j = 0; j < size; j++) {
				if (matrix[i][j] < 0) {
					currentRow += matrix[i][j] + "   ";
				} else {
					currentRow += " " + matrix[i][j] + "   ";
				}
			}
			result += currentRow.substring(0, currentRow.length() - 2) + "\n";
		}
		return result;
	}
	
	/**
	 * Compares if the current matrix is equal to another matrix. Two matrix are 
	 * considered equal if they have the same size and same corresponding entry.
	 * @param other another matrix to be compared
	 * @return true if two matrix are equal, otherwise false
	 */
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
					if (matrix[i][j] != otherSquareMatrix.matrix[i][j]) {
						return false;
					}
				}
			}
			return true;
		}
	}
	
	/**
	 * Returns a hash code for the matrix.
	 * @return hash code for the matrix
	 */
	@Override
	public int hashCode() {
		return this.hashCode();
	}
}