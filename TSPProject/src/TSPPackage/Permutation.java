// Permutation.java - Find permutation of integers {1,...,N}
// EE324 Project 2
// C.McArdle, DCU, 2017
//
// THERE IS NO NEED TO EDIT THIS FILE
////////////////////////////////////////////

package TSPPackage;

import java.util.ArrayList;

public class Permutation {

	// Compute all permutations of the integers {1,...,N}
	// Returns a list of arrays, each array is one permutation, of length N
	public static ArrayList<int[]> permute(final int N) {
		ArrayList<int[]> perms = new ArrayList<int[]>(); // list of arrays
		perms.add(new int[]{1}); // first permutation
		ArrayList<int[]> extended = new ArrayList<int[]>();
		for (int i=0; i<N-1; i++) {
			extended.clear();
			for (int[] p : perms) {
				int[][] extPerms = extendPerm(p);
				for (int j=0; j<extPerms.length; j++) {
					extended.add(extPerms[j]);
				}
			}
			perms = new ArrayList<int[]>(extended); // copy
		}
		return perms;
	}

	// Extend a permutation using combinations with a new integer
	// This method is called by permute()
	private static int[][] extendPerm(int[] perm) {
		int N = perm.length;
		int[][] ext = new int[N+1][N+1];
		for (int i=0; i<N+1; i++) {
			for (int j=0; j<N+1; j++) {
				if (i == j) // diagonal entries
					ext[i][j] = N+1;
				else {
					if (i>j)  ext[i][j] = perm[j];
					else      ext[i][j] = perm[j-1];
				}
			}
		}
		return ext;
	}

	// TEST CLIENT //
	public static void main(String args[]) {
		// find and print all permutations of {1,2,3,4}
		ArrayList<int[]> perms = permute(4);
		for(int[] p : perms) {
			for (int i=0; i<p.length; i++)
				System.out.print(p[i] + " ");
			System.out.println();
		}
	}
}
