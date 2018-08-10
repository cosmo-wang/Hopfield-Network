package client;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import hopfield_network.HopfieldNetwork;

public class HopfieldNetworkClient {
	
	public static final String[] NUMBERS = {"1111111111\r\n" + 
			"1100000011\r\n" + 
			"1100000011\r\n" + 
			"1100000011\r\n" + 
			"1100000011\r\n" + 
			"1100000011\r\n" + 
			"1111111111", "0000110000\r\n" + 
					"0011110000\r\n" + 
					"0000110000\r\n" + 
					"0000110000\r\n" + 
					"0000110000\r\n" + 
					"0000110000\r\n" + 
					"1111111111", "1111111111\r\n" + 
							"0000000011\r\n" + 
							"0000000011\r\n" + 
							"1111111111\r\n" + 
							"1100000000\r\n" + 
							"1100000000\r\n" + 
							"1111111111", "1111111111\r\n" + 
									"0000000011\r\n" + 
									"0000000011\r\n" + 
									"1111111111\r\n" + 
									"0000000011\r\n" + 
									"0000000011\r\n" + 
									"1111111111", "1100000011\r\n" + 
											"1100000011\r\n" + 
											"1100000011\r\n" + 
											"1111111111\r\n" + 
											"0000000011\r\n" + 
											"0000000011\r\n" + 
											"0000000011", "1111111111\r\n" + 
													"1100000000\r\n" + 
													"1100000000\r\n" + 
													"1111111111\r\n" + 
													"0000000011\r\n" + 
													"0000000011\r\n" + 
													"1111111111", "1111111111\r\n" + 
															"1100000000\r\n" + 
															"1100000000\r\n" + 
															"1111111111\r\n" + 
															"1100000011\r\n" + 
															"1100000011\r\n" + 
															"1111111111", "1111111111\r\n" + 
																	"0000000011\r\n" + 
																	"0000000011\r\n" + 
																	"0000000011\r\n" + 
																	"0000000011\r\n" + 
																	"0000000011\r\n" + 
																	"0000000011", "1111111111\r\n" + 
																			"1100000011\r\n" + 
																			"1100000011\r\n" + 
																			"1111111111\r\n" + 
																			"1100000011\r\n" + 
																			"1100000011\r\n" + 
																			"1111111111", "1111111111\r\n" + 
																					"1100000011\r\n" + 
																					"1100000011\r\n" + 
																					"1111111111\r\n" + 
																					"0000000011\r\n" + 
																					"0000000011\r\n" + 
																					"1111111111"};

	public static void main(String[] args) {
		HopfieldNetwork hn = new HopfieldNetwork(70);

		hn.train(parseToArray(NUMBERS[0]));
		hn.train(parseToArray(NUMBERS[1]));
		hn.train(parseToArray(NUMBERS[2]));
		hn.train(parseToArray(NUMBERS[3]));
		hn.train(parseToArray(NUMBERS[4]));
		hn.train(parseToArray(NUMBERS[5]));
		hn.train(parseToArray(NUMBERS[6]));
		hn.train(parseToArray(NUMBERS[7]));
		hn.train(parseToArray(NUMBERS[8]));
		hn.train(parseToArray(NUMBERS[9]));
		
		String noiseZero = addNoise(NUMBERS[0], 70, 10);
		System.out.println(noiseZero);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseZero)));
		
		String noiseOne = addNoise(NUMBERS[1], 70, 10);
		System.out.println(noiseOne);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseOne)));
		
		String noiseTwo = addNoise(NUMBERS[2], 70, 10);
		System.out.println(noiseTwo);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseTwo)));
		
		String noiseThree = addNoise(NUMBERS[3], 70, 10);
		System.out.println(noiseThree);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseThree)));
		
		String noiseFour = addNoise(NUMBERS[4], 70, 10);
		System.out.println(noiseFour);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseFour)));
		
		String noiseFive = addNoise(NUMBERS[5], 70, 10);
		System.out.println(noiseFive);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseFive)));
		
		String noiseSix = addNoise(NUMBERS[6], 70, 10);
		System.out.println(noiseSix);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseSix)));
		
		String noiseSeven = addNoise(NUMBERS[7], 70, 10);
		System.out.println(noiseSeven);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseSeven)));
		
		String noiseEight = addNoise(NUMBERS[8], 70, 10);
		System.out.println(noiseEight);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseEight)));
		
		String noiseNine = addNoise(NUMBERS[9], 70, 10);
		System.out.println(noiseNine);
		System.out.println();
		System.out.println(hn.recover(parseToArray(noiseNine)));

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
