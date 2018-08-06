package client;

import matrix.Matrix;

public class HopfieldNetworkClient {

	public static void main(String[] args) {
		int[][] matrix = {{1, 2},{3, 4}};
		Matrix m = new Matrix(matrix);
		Matrix m2 = new Matrix(matrix);
		Matrix m3 = new Matrix(5);
		System.out.println(m);
		m.add(m2);
		System.out.println(m);
		System.out.println(m3);
	}

}
