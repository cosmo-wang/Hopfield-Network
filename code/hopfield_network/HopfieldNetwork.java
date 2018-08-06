package hopfield_network;

import square_matrix.SquareMatrix;

public class HopfieldNetwork {
	private SquareMatrix weightMatrix;  // matrix to store weight of each connection between nodes
	private int size;  // number of nodes in the network
	
	public HopfieldNetwork(int size) {
		this.size = size;
		weightMatrix = new SquareMatrix(size);
	}
	
	public void train(int[] pattern) {
		if (pattern.length != size) {
			throw new IllegalArgumentException();
		}
		SquareMatrix temp = new SquareMatrix(size);
		for (int i = 1; i < size; i++) {
			for (int j = i + 1; j <= size; j++) {
				int curWeight = (2 * pattern[i - 1] - 1) * (2 * pattern[j - 1] - 1);
				temp.setEntry(i, j, curWeight);
				temp.setEntry(j, i, curWeight);
			}
		}
		weightMatrix.add(temp);
	}
	
	@Override
	public String toString() {
		return weightMatrix.toString();
	}
}
