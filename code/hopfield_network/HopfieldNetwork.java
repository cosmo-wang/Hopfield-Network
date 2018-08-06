package hopfield_network;

import java.util.ArrayList;
import java.util.Random;

import square_matrix.SquareMatrix;

public class HopfieldNetwork {
	private SquareMatrix weightMatrix;  // matrix to store weight of each connection between nodes
	private int size;  // number of nodes in the network
	
	public static final int THRESHOLD = 0;
	
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
	
	public int[] recognize(int[] pattern) {
		Random rand = new Random();
		int[] output = pattern.clone();
		if (pattern.length != size) {
			throw new IllegalArgumentException();
		}
		// list of integer used to generate semi-random order
		// of updating each node in the network
		// all nodes will be updated in one step, with in 
		// this one step, nodes are chosen in random order to update
		// this semi-random order avoids bad pseudo-random
		// generator from favoring one of the nodes
		ArrayList<Integer> options = new ArrayList<Integer>();
		// records times that a node doesn't change state after upadating
		int timeUnchanged = 0;
		// loop to start updating nodes in the network
		// exits when none of the node changes state
		// after going all of the nodes three times
		while (timeUnchanged < size * 3) {
			// if options is empty, it means every node has been updated once
			// reset the list to start another update step
			if (options.isEmpty()) {
				for (int i = 0; i < size; i++) {
					options.add(i, i);
				}
			}
			// randomly generate a number, which is the node to be updated
			int option = options.remove(rand.nextInt(options.size()));
			// value that will be checked against the threshold
			int checkValue = 0;
			// evaluate the state of each node in the pattern using the formula
			for (int i = 0; i < size; i++) {
				checkValue += weightMatrix.getRow(option + 1)[i] * output[i];
			}
			// check against the threshold
			// update the pattern
			// increment timeUnchanged if the state of the node doesn't change
			if (checkValue >= THRESHOLD) {
				timeUnchanged = (output[option] == 1)? timeUnchanged + 1 : 0;
				output[option] = 1;
			} else {
				timeUnchanged = (output[option] == 0)? timeUnchanged + 1 : 0;
				output[option] = 0;
			}
		}
		return output;
	}
	
	@Override
	public String toString() {
		return weightMatrix.toString();
	}
}
