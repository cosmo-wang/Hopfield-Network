package hopfield_network;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class HopfieldNetworkControl {
	public static final int TEST_TIMES = 5;
	
	public static void main(String[] args) throws IOException {
		System.out.println("This program uses Hopfield Network for letter and number recognition.");
		while (true) {
			@SuppressWarnings("resource")
			Scanner console = new Scanner(System.in);
			Map<String, String> data = null;
			System.out.println("Enter \"start\" to start the program. Any other key to quit.");
			String operation = console.nextLine();
			if (operation.equals("start")) {
				System.out.println("Please specify the data file you want to use: ");
				System.out.println("\tnumbers - file contains numbers"
						+ "\n\tletters - file contains letters"
						+ "\n\tcustom - for customized file");
				while (true) {
					String command = console.nextLine();
					if (command.equals("numbers")) {
						data = parseFile("../data/Numbers.txt");
						break;
					} else if (command.equals("letters")) {
						data = parseFile("../data/Letters.txt");
						break;
					} else if (command.equals("custom")) {
						System.out.println("Please enter name of the file you want to use: ");
						String fileName = console.nextLine();
						data = parseFile("../data/" + fileName + ".txt");
						break;
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
				while (true) {
					System.out.println("Please specify the next command:");
					System.out.println("(Test should NOT be performed before training!)");
					System.out.println("\ttrain - train the network on all patterns"
							+ "\n\ttest - run accuracy test for pattern recognition"
							+ "\n\tcustom - customized oprations"
							+ "\n\tword - word mode"
							+ "\n\tquit - quit or choose different pattern file");
					String command = console.nextLine();
					if (command.equals("train")) {
						for (String key: data.keySet()) {
							hn.train(parseToArray(data.get(key)));
						}
						System.out.println("The network has been trained on all existing patterns.");
					} else if (command.equals("test")) {
						accuracyTest(hn, data, size);
					} else if (command.equals("word")) {
						wordMode(hn, size);
					} else if (command.equals("custom")) {
						hn.clear();
						while (true) {
							System.out.println("Pattern to be trained in the data file: ");
							int count = 0;
							for (String key: data.keySet()) {
								count++;
								System.out.print(key + "\t");
								if (count % 5 == 0) {
									System.out.println();
								}
							}
							System.out.println();
							System.out.println("Please specify the pattern you want to train on: ");
							String pattern = data.get(console.nextLine());
							hn.train(parseToArray(pattern));
							System.out.println("The trained pattern is: ");
							System.out.println(pattern);
							System.out.println("Do you want to train more patterns? (y for yes, any other key for no)");
							String answer = console.nextLine();
							if (!answer.equals("y")) {
								break;
							}
						}
						while (true) {
							System.out.println("Please enter the pattern you want to add noise on:");
							String key = console.nextLine();
							System.out.println("Please specify the percentage of noise you want to add on:");
							double noiseLevel = Double.parseDouble(console.nextLine());
							String noised = addNoise(data.get(key), size, noiseLevel);
							System.out.println("The noised pattern is: ");
							System.out.println(noised);
							System.out.println("Do you want to try to recover the pattern? "
									+ "\n(y for yes, any other key for no)");
							String answer = console.nextLine();
							if (answer.equals("y")) {
								System.out.println("The recovered pattern is: ");
								System.out.println(hn.recover(parseToArray(noised)));
							}
							System.out.println("Do you want to try other pattern or noise level? "
									+ "\n(y for yes, any other key for no)");
							if (!console.nextLine().equals("y")) {
								break;
							}
						}
					} else if (command.equals("quit")) {
						hn = null;
						break;
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
	
	/**
	 * Execute word mode on a Hopfield Network.
	 * @param hn network to be tested on
	 * @param size size of the network
	 */
	public static void wordMode(HopfieldNetwork hn, int size) {
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		hn.clear();
		Map<String, String> data = parseFile("../data/Letters.txt");
		data.remove("format");
		for (String key: data.keySet()) {
			hn.train(parseToArray(data.get(key)));
		}
		System.out.println("Welcome to the word mode! All letters have been\n"
				+ "automatically trained into the network!");
		System.out.println("In this mode, you may enter a single word containing letters. \n"
				+ "Than you may specify a noise level. Noise will be added to each letter in \n"
				+ "the word. The network will try to recover the word.");
		while (true) {
			System.out.println("First, please enter a word: ");
			String word = console.nextLine().toUpperCase();
			System.out.println("Then specify percentage of noise you want to add:");
			double noiseLevel = Double.parseDouble(console.nextLine());
			System.out.println("Recovery started.");
			String result = "";
			for (int i = 0; i < word.length(); i++) {
				System.out.println("Recovering pattern: ");
				String pattern = data.get(word.charAt(i) + "");
				System.out.println(pattern.trim() + "\n");
				String noised = addNoise(pattern, size, noiseLevel);
				System.out.println("Recovered pattern: ");
				String recovered = hn.recover(parseToArray(noised));
				System.out.println(recovered.trim() + "\n");
				String recoveredKey = getKey(data, recovered + "\n");
				result += recoveredKey;
			}
			System.out.println("Result:");
			System.out.println("Input Word: " + word);
			System.out.println("Recovered word: " + result);
			System.out.println("Do you want to try again?\n(y for yes, any other key for no)");
			if (!console.nextLine().equals("y")) {
				break;
			}
		}
	}
	
	/**
	 * Performs accuracy test on a Hopfield Network.
	 * @param hn network to be tested on
	 * @param data maps abstract value of a pattern to its string representation
	 * @param size size of the network
	 * @throws IOException if the file is not found
	 */
	public static void accuracyTest(HopfieldNetwork hn, Map<String, String> data, int size) throws IOException {
		System.out.println(data);
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		System.out.println("Recognition accuracy test will be performed on the network.");
		System.out.println("For each trained pattern in the network, certern percentage of noise"
				+ " will be added to the pattern.");
		System.out.println("The noised pattern will be fed back to the network to recognize.");
		System.out.println("The result will be compared against the abstract value of the original pattern.");
		System.out.println("This process will be repeated certain times. "
				+ "After the repetition, a statistical result will be printed.");
		System.out.println("Test may take seconds to minutes to complete.");
		System.out.println("Please specify the percentage of noise you want to test on:");
		System.out.println("(0.0 < percentage < 100.0)");
		double noiseLevel = Double.parseDouble(console.nextLine());
		System.out.println("Please specify time of repetitions for the test:");
		int testTimes = Integer.parseInt(console.nextLine());
		double totalAccuracy = 0.0;
		for (String key: data.keySet()) {
			System.out.println("Testing recognition on " + key);
			String noised = addNoise(data.get(key), size, noiseLevel);
			double correct = 0.0;
			for (int i = 0; i < testTimes; i++) {
				String recognizedPattern = hn.recover(parseToArray(noised));
				String recognizedKey = getKey(data, recognizedPattern + "\n");
				if (recognizedKey != null && recognizedKey.equals(key)) {
					correct++;				
				}
			}
			System.out.println("Accuracy: " + correct + "/" + testTimes + "\n");
			totalAccuracy += correct / (double)testTimes;
		}
		double accuracy = totalAccuracy / data.size();
		String summary = "Test Summary:\n"
				+ "Number of Nodes: " + size
				+ "\nNumber of Patterns: " + data.size()
				+ "\nNoise Level: " + noiseLevel + "%\n"
				+ "Repetition Times: " + testTimes
				+ "\nOverall Recognition Accuracy: " + String.format("%.2f", accuracy * 100.0) + "%\n";
		System.out.println("Do you want to print the test result to a seperate file?"
				+ "\n(y for yes, any other key for no)");
		System.out.println("The result will also be printed to the console.");
		String answer = console.nextLine();
		if (answer.equals("y")) {
			System.out.println("Please enter the name of the file you want to print the result: ");
			System.out.println("If file already exists, result will be appended to the file.");
			String filename = console.nextLine();
			PrintWriter writer = new PrintWriter(new FileWriter("../test/" + filename + ".txt", true));
			writer.println(summary);
			writer.close();
		}
		System.out.println(summary);
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
