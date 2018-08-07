package hopfield_network;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import square_matrix.SquareMatrix;

/**
 * This class represents a Hopfield network.
 * @author Cosmo Wang
 *
 */
public class HopfieldNetwork {
	private SquareMatrix weightMatrix;  // matrix to store weight of each connection between nodes
	private int size;  // number of nodes in the network
	private Vector<int[]> patternsTrained;
	
	// threshold needed for updating nodes in the network
	public static final int THRESHOLD = 0;
	
	/**
	 * Constructor of the class. Constructs a new Hopfield network.
	 * @param size number of nodes in the network.
	 * @requires size is greater that 0
	 * @throws IllegalArgumentException if size is less than or equal to 0
	 */
	public HopfieldNetwork(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException();
		}
		this.size = size;
		weightMatrix = new SquareMatrix(size);
		patternsTrained = new Vector<int[]>();
	}
	
	/**
	 * This method trains the network according to a given pattern. Pattern
	 * is specified in the format of a array of 1s and 0s.
	 * @requires pattern is not null
	 *           length of pattern is equal to number of nodes in the network
	 *           pattern is an array consisting only 1s and 0s
	 * @param pattern pattern to be trained into the network
	 */
	public void train(int[] pattern) {
		if (pattern == null || pattern.length != size || !checkPattern(pattern)) {
			throw new IllegalArgumentException();
		}
//		patternsTrained.addElement(pattern);
//		for (int i = 1; i <= size; i++) {
//			for (int j = i + 1; j <= size; j++) {
//				for (int k = 0; k < patternsTrained.size(); k++) {
//					int[] curPattern = patternsTrained.get(k);
//					int curWeight = (2 * curPattern[i - 1] - 1) * (2 * curPattern[j - 1] - 1);
//					weightMatrix.setEntry(i, j, curWeight);
//					weightMatrix.setEntry(j, i, curWeight);
//				}
//			}
//		}
		
		SquareMatrix temp = new SquareMatrix(size);
		// update the temporary matrix according to the formula
		for (int i = 1; i < size; i++) {
			for (int j = i + 1; j <= size; j++) {
				int curWeight = (2 * pattern[i - 1] - 1) * (2 * pattern[j - 1] - 1);
				temp.setEntry(i, j, curWeight);
				temp.setEntry(j, i, curWeight);
			}
		}
		// add the temporary matrix to the weight matrix of the network
		weightMatrix.add(temp);
	}
	
	/**
	 * Given a corrupted pattern of a letter or character, recover the pattern
	 * to an existing pattern stored in the network.
	 * @requires pattern is not null
	 *           length of pattern is equal to number of nodes in the network
	 *           pattern is an array consisting only 1s and 0s
	 * @param pattern corrupted pattern to be recovered
	 * @return one of the patterns previously trained into the network in 
	 *         format of an array
	 */
	public int[] recognize(int[] pattern) {
		if (pattern == null || pattern.length != size || !checkPattern(pattern)) {
			throw new IllegalArgumentException();
		}
		Random rand = new Random();
		int[] output = pattern.clone();
		// list of integer used to generate semi-random order
		// of updating each node in the network
		// all nodes will be updated in one step, with in 
		// this one step, nodes are chosen in random order to update
		ArrayList<Integer> options = new ArrayList<Integer>();
		// records times that a node doesn't change state after upadating
		int timeUnchanged = 0;
		// loop to start updating nodes in the network
		// exits when none of the node changes state
		// after going all of the nodes three times
		while (timeUnchanged < size * 2) {
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
				//checkValue += weightMatrix.getEntry(option + 1, i + 1) * output[i];
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
	
	/**
	 * Returns a string representation of the network, which is the weight matrix.
	 * @return string representation of the weight matrix of the network
	 */
	@Override
	public String toString() {
		return weightMatrix.toString();
	}
	
	/**
	 * Check if a given pattern is valid. A valid pattern contains only 1s and 0s.
	 * @param pattern pattern to be checked
	 * @return true if the pattern only contains 1s and 0s, otherwise false
	 */
	private boolean checkPattern(int[] pattern) {
		for (int i = 0; i < pattern.length; i++) {
			if (pattern[i] != 0 && pattern[i] != 1) {
				return false;
			}
		}
		return true;
	}
}
