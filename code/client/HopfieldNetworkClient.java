package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import hopfield_network.HopfieldNetwork;

public class HopfieldNetworkClient {
	public static final int TEST_TIMES = 50;
	
	public static void main(String[] args) throws IOException {
		System.out.println("This program uses Hopfield Network for letter and nunber recognition.");
		while (true) {
			Scanner console = new Scanner(System.in);
			Map<String, String> data = null;
			System.out.println("Enter \"start\" to start the program. Any other key to quit.");
			String operation = console.nextLine();
			if (operation.equals("start")) {
				System.out.println("Please specify the data file you want to use:");
				System.out.println("\tnumbers - file contains numbers"
						+ "\n\tletters - file contains letters"
						+ "\n\tcustom - for customized file");
				boolean invalidCommand = true;
				while (invalidCommand) {
					String command = console.nextLine();
					if (command.equals("numbers")) {
						data = parseFile("data/Numbers.txt");
						invalidCommand = false;
					} else if (command.equals("letters")) {
						data = parseFile("data/Letters.txt");
						invalidCommand = false;
					} else if (command.equals("custom")) {
						String fileName = console.nextLine();
						data = parseFile("data/" + fileName + ".txt");
						invalidCommand = false;
					} else {
						System.out.println("Unknown Command. Please try again.");
					}
				}
				String[] tokens = data.get("format").split("\t");
				data.remove("format");
				int size = Integer.parseInt(tokens[0]);
				int width = Integer.parseInt(tokens[1]);
				HopfieldNetwork hn = new HopfieldNetwork(size, width);
				System.out.println("The network has been constructed.");
				invalidCommand = true;
				while (invalidCommand) {
					System.out.println("Please specify the next command:");
					System.out.println("\ttrain - train the network on all patterns"
							+ "\n\ttest - test pattern recognition"
							+ "\n\tcustom - customized oprations"
							+ "\n\tquit - quit or choose different pattern file");
					String command = console.nextLine();
					if (command.equals("train")) {
						for (String key: data.keySet()) {
							hn.train(parseToArray(data.get(key)));
						}
						System.out.println("The network has been trained on all existing patterns.");
					} else if (command.equals("test")) {
						accuracyTest(hn, data, size);
					} else if (command.equals("custom")) {
						
					} else if (command.equals("quit")) {
						invalidCommand = false;
					} else {
						System.out.println("Unknown Command: " + command + ". Please try again.");
					}
				}
			} else {
				System.out.println("Thank you!");
				console.close();
				return;
			}
		}
	}
	
	public static void accuracyTest(HopfieldNetwork hn, Map<String, String> data, int size) throws IOException {
		Scanner console = new Scanner(System.in);
		PrintWriter writer = new PrintWriter(new FileWriter("./data/test.txt", true));
		System.out.println("Recognition accuracy test will be performed on the network.");
		System.out.println("For each trained pattern in the network, certern percentage of noise"
				+ " will be added to the pattern.");
		System.out.println("The noised pattern will be fed back to the network to recognize.");
		System.out.println("The result will be compared against the abstract value of the original pattern.");
		System.out.println("This process will be repeated 20 times. "
				+ "After the repetition, a statistical result will be printed.");
		System.out.println("Test may take seconds to minutes to complete.");
		System.out.println("Please specify the percentage of noise you want to test on:");
		System.out.println("(0.0 < percentage < 100.0)");
		double noiseLevel = console.nextDouble();
		double totalAccuracy = 0.0;
		Date d = new Date();
		for (String key: data.keySet()) {
			System.out.println("Testing recognition on " + key);
			String noised = addNoise(data.get(key), size, noiseLevel);
			double correct = 0.0;
			for (int i = 0; i < TEST_TIMES; i++) {
				String recognizedPattern = hn.recover(parseToArray(noised));
				String recognizedKey = getKey(data, recognizedPattern + "\n");
				if (recognizedKey != null && recognizedKey.equals(key)) {
					correct++;				
				}
			}
			System.out.println("Accuracy: " + correct + "/" + TEST_TIMES + "\n");
			totalAccuracy += correct / (double)TEST_TIMES;
		}
		double time = (new Date()).getTime() - d.getTime();
		double accuracy = totalAccuracy / data.size();
		writer.println("Test Summary:");
		writer.println("Number of Nodes: " + size);
		writer.println("Number of Patterns: " + data.size());
		writer.println("Noise Level: " + noiseLevel + "%");
		writer.println("Test Duration: " + time / 100.0 + "s");
		writer.println("Overall Recognition Accuracy: " + String.format("%.2f", accuracy * 100.0) + "%\n");
		writer.close();
	}
	
	/**
	 * Get a key from a one-to-one map with the value it maps to.
	 * @param data map we are searching in
	 * @param value value that the desired key maps to
	 * @return the key which maps to the given value
	 */
	public static String getKey(Map<String, String> data, String value) {
		for (String key: data.keySet()) {
			if (data.get(key).trim().equals(value.trim())) {
				return key;
			}
		}
		return null;
	}
	
	/**
	 * Parse a file containing patters into a map of the abstract value
	 * of the pattern to its string representation, with an additional
	 * key "format" mapping to information of the patterns.
	 * @param filename name of the file containing patterns
	 * @return a map of the abstract value of the 
	 *         pattern to its string representation
	 */
	public static Map<String, String> parseFile(String filename) {
		Scanner reader = null;
		Map<String, String> output = new HashMap<String, String>();
		try {
			reader = new Scanner(new File(filename));
			String inputLine = reader.nextLine();
			output.put("format", inputLine);
			while (reader.hasNextLine()) {
				inputLine = reader.nextLine();
				String key= null;
				String pattern = "";
				if (inputLine.length() == 1) {
					key = inputLine;
				}
				while (reader.hasNextLine() && !inputLine.equals("")) {
					inputLine = reader.nextLine();
					pattern += inputLine + "\n";
				}
				output.put(key, pattern);
			}
		} catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            reader.close();
	        }
	    }
		return output;
	}
	
	/**
	 * Parse a string with 1s and 0s and line breaks into an array
	 * of 1s and 0s and ignore line breaks.
	 * @param pattern string of pattern needs to be parsed into an array
	 * @return an array of 1s and 0s
	 */
	public static int[] parseToArray(String pattern) {
		pattern = pattern.replace("\n", "");
		pattern = pattern.replace("\r", "");
		int[] output = new int[pattern.length()];
		for (int i = 0; i < pattern.length(); i++) {
			output[i] = Integer.parseInt(pattern.charAt(i) + "");
		}
		return output;
	}
	
	/**
	 * Add noise to a string representation of a pattern.
	 * @param pattern pattern to be added noise
	 * @param size number of nodes in the pattern
	 * @param percentage noise percentage needs to be added
	 * @return noised string representation of a pattern
	 */
	public static String addNoise(String pattern, int size, double percentage) {
		int n = (int) ((Math.round(percentage) / 100.0) * size);
		Random rand = new Random();
		Set<Integer> chosen = new HashSet<Integer>();
		char[] p = pattern.toCharArray();
		for (int i = 0; i < n; i++) {
			int pos = rand.nextInt(pattern.length());
			if (!chosen.contains(pos)) {
				chosen.add(pos);
				if (p[pos] == '1') {
					p[pos] = '0';
				} else if (p[pos] == '0') {
					p[pos] = '1';
				} else {
					i--;
				}
			} else {
				i--;
			}
			
		}
		return new String(p);
	}
}
