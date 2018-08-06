package client;

import hopfield_network.HopfieldNetwork;

public class HopfieldNetworkClient {

	public static void main(String[] args) {
		HopfieldNetwork hn = new HopfieldNetwork(70);
		hn.train(parseToArray("1111111111\r\n" + 
				"1100000011\r\n" + 
				"1100000011\r\n" + 
				"1100000011\r\n" + 
				"1100000011\r\n" + 
				"1100000011\r\n" + 
				"1111111111"));
		hn.train(parseToArray("0000110000\r\n" + 
				"0011110000\r\n" + 
				"0000110000\r\n" + 
				"0000110000\r\n" + 
				"0000110000\r\n" + 
				"0000110000\r\n" + 
				"1111111111"));
		System.out.println("0000110000\r\n" + 
				"0011110000\r\n" + 
				"0000110000\r\n" + 
				"0011000000\r\n" + 
				"0000110000\r\n" + 
				"0000110011\r\n" + 
				"1101111111");
		System.out.println();
		System.out.println(parseToString(hn.recognize(parseToArray("0000110000\r\n" + 
				"0011110000\r\n" + 
				"0000110000\r\n" + 
				"0011000000\r\n" + 
				"0000110000\r\n" + 
				"0000110011\r\n" + 
				"1101111111")), 10));
		
		System.out.println("1111010111\r\n" + 
				"0000000011\r\n" + 
				"1100000011\r\n" + 
				"1100110011\r\n" + 
				"1100001011\r\n" + 
				"1101000011\r\n" + 
				"1101110011");
		System.out.println();
		System.out.println(parseToString(hn.recognize(parseToArray("1111010111\r\n" + 
				"0000000011\r\n" + 
				"1100000011\r\n" + 
				"1100110011\r\n" + 
				"1100001011\r\n" + 
				"1101000011\r\n" + 
				"1101110011")), 10));
	}
	
	public static int[] parseToArray(String pattern) {
		pattern = pattern.replace("\n", "");
		pattern = pattern.replace("\r", "");
		int[] output = new int[pattern.length()];
		for (int i = 0; i < pattern.length(); i++) {
			output[i] = Integer.parseInt(pattern.charAt(i) + "");
		}
		return output;
	}

	public static String parseToString(int[] pattern, int n) {
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
