package client;

import hopfield_network.HopfieldNetwork;

public class HopfieldNetworkClient {

	public static void main(String[] args) {
//		int[][] matrix = {{1, 2},{3, 4}};
//		Matrix m = new Matrix(matrix);
//		System.out.println(m);
//		System.out.println(m.getEntry(1, 1));
//		System.out.println(m.getEntry(2, 2));
//		m.setEntry(1, 2, 8);
//		System.out.println(m);
		
		HopfieldNetwork hn = new HopfieldNetwork(5);
		int[] pattern  = {0, 1, 1, 0, 1};
		System.out.println(hn);
		hn.train(pattern);
		System.out.println(hn);
		int[] pattern2 = {1, 0, 1, 0, 1};
		hn.train(pattern2);
		System.out.println(hn);
	}

}
