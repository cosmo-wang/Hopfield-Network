package hopfield_network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import square_matrix.SquareMatrix;

/**
 * This class represents a Hopfield network.
 * @author Cosmo Wang
 *
 */
public class HopfieldNetwork {
	// set of weight matrix that stores weight matrix of each pattern
	private Set<SquareMatrix> weightMatrices;
	private int size;  // number of nodes in the network
	private int stringWidth;  // width of string representation of patterns in this network
	
	// threshold needed for updating nodes in the network
	public static final int THRESHOLD = 0;
	
	/**
	 * Constructor of the class. Constructs a new Hopfield network.
	 * @param size number of nodes in the network.
	 * @requires size is greater that 0
	 * @throws IllegalArgumentException if size is less than or equal to 0
	 */
	public HopfieldNetwork(int size, int stringWidth) {
		if (size <= 0) {
			throw new IllegalArgumentException();
		}
		this.size = size;
		this.stringWidth = stringWidth;
		weightMatrices = new HashSet<SquareMatrix>();
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
		SquareMatrix currentMatrix = new SquareMatrix(size);
		// update the temporary matrix according to the formula
		for (int i = 1; i < size; i++) {
			for (int j = i + 1; j <= size; j++) {
				int curWeight = (2 * pattern[i - 1] - 1) * (2 * pattern[j - 1] - 1);
				currentMatrix.setEntry(i, j, curWeight);
				currentMatrix.setEntry(j, i, curWeight);
			}
		}
		// add the current weight matrix to the set of weight matrices of the network
		weightMatrices.add(currentMatrix);
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
	public String recover(int[] pattern) {
		String result = null;
		// map to record count of each recovered pattern
		Map<String, Integer> counts = new HashMap<String, Integer>();
		// recover for 30 times to get an accurate recovery
		for (int i = 0; i < 30; i ++) {
			int[] current = recoverHelper(pattern);
			String key = parseToString(current, stringWidth);
			if (!counts.containsKey(key)) {
				counts.put(key, 1);
			} else {
				counts.put(key, counts.get(key) + 1);
			}
		}
		// look for pattern appears the most
		int maxCount = 0;
		for (String currentPattern: counts.keySet()) {
			if (counts.get(currentPattern) > maxCount) {
				result = currentPattern;
				maxCount = counts.get(currentPattern);
			}
		}
		return result;
	}
	
	/**
	 * Helper method for recover method. Recover the given pattern once
	 * and returns the recovered pattern.
	 * @requires pattern is not null
	 *           length of pattern is equal to number of nodes in the network
	 *           pattern is an array consisting only 1s and 0s
	 * @param pattern corrupted pattern to be recovered
	 * @return one of the patterns previously trained into the network in 
	 *         format of an array after one recovery
	 */
	private int[] recoverHelper(int[] pattern) {
		if (pattern == null || pattern.length != size || !checkPattern(pattern)) {
			throw new IllegalArgumentException();
		}
		Random rand = new Random();
		// list of integer used to generate semi-random order
		// of updating each node in the network
		// all nodes will be updated in one step, with in 
		// this one step, nodes are chosen in random order to update
		ArrayList<Integer> options = new ArrayList<Integer>();
		int[] result = null;
		// records times that a node doesn't change state after updating
		int timeUnchanged = 0;
		// minimum number of operation for recovering pattern using one
		// of the stored weight matrix
		int minOperation = Integer.MAX_VALUE;
		// for each stored weight matrix, try to recover the pattern and
		// record number of operations
		for (SquareMatrix current: weightMatrices) {
			int[] output = pattern.clone();
			// current operation count
			int n = 0;
			// loop to start updating nodes in the network
			// exits when none of the node changes state
			// after going all of the nodes
			while (timeUnchanged < size) {
				// increment operation count
				n++;
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
					checkValue += current.getRow(option + 1)[i] * output[i];
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
			timeUnchanged = 0;
			// compares against the minimum number of operations
			if (n < minOperation) {
				result = output;
				minOperation = n;
			}
		}
		return result;
	}
	
	/**
	 * Clears the weight matrices to obtain a fresh empty network.
	 */
	public void clear() {
		weightMatrices.clear();
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
	
	private String parseToString(int[] pattern, int n) {
		String output = "";
		for (int i = 0; i < pattern.length; i = i + n) {
			for (int j = i; j < i + n; j++) {
				output += pattern[j];
			}
			output += "\n";
		}
		return output;
	}
}