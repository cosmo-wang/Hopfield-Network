package client;

import java.util.Arrays;

import hopfield_network.HopfieldNetwork;

public class HopfieldNetworkClient {

	public static void main(String[] args) {
		System.out.println();
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
