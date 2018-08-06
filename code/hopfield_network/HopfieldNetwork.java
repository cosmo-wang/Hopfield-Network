package hopfield_network;

import matrix.Matrix;

public class HopfieldNetwork {
	private Matrix weightMatrix;  // matrix to store weight of each connection between nodes
	private int size;  // number of nodes in the network
	
	public HopfieldNetwork(int size) {
		this.size = size;
		weightMatrix = new Matrix(size);
	}
}
