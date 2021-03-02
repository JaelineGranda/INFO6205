/**
 * Original code:
 * Copyright © 2000–2017, Robert Sedgewick and Kevin Wayne.
 * <p>
 * Modifications:
 * Copyright (c) 2017. Phasmid Software
 */
package edu.neu.coe.info6205.union_find;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Height-weighted Quick Union using depth
 */
public class Alternative1 implements UF {
    /**
     * Ensure that site p is connected to site q,
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     */
    public void connect(int p, int q) {
        if (!isConnected(p, q)) union(p, q);
    }

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n               the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public Alternative1(int n) {
        count = n;
        parent = new int[n];
        depth = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            depth[i] = 1;
        }
    }

    public void show() {
        for (int i = 0; i < parent.length; i++) {
            System.out.printf("%d: %d, %d\n", i, parent[i], depth[i]);
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int components() {
        return count;
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        int root = p;
        while (root != parent[root]) {
        	root = parent[root];
        }
        return root;
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        // CONSIDER can we avoid doing find again?
        mergeComponents(find(p), find(q));
        count--;
    }

    @Override
    public int size() {
        return parent.length;
    }

    @Override
    public String toString() {
        return "UF_HWQUPC:" + "\n  count: " + count +
                "\n  parents: " + Arrays.toString(parent) +
                "\n  depths: " + Arrays.toString(depth);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    private void updateParent(int p, int x) {
        parent[p] = x;
    }

    private void updateHeight(int p, int x) {
        depth[p] += depth[x];
    }

    /**
     * Used only by testing code
     *
     * @param i the component
     * @return the parent of the component
     */
    private int getParent(int i) {
        return parent[i];
    }

    private final int[] parent;   // parent[i] = parent of i
    private final int[] depth;   // depth[i] = depth of subtree rooted at i
    private int count;  // number of components

    private void mergeComponents(int i, int j) {
    	int root1 = find(i); 	
    	int root2 = find(j);
    	
    	if ( root1 == root2) {
    		return;
    	}
    	
        if (depth[i] < depth[j]) {
        	parent[i] = j;
        }
        else if (depth[i] > depth[j]) {
        		parent[j] = i;
        	}
        	else {
        		parent[j] = i;
        		depth[i]++;
        	}
  
    }

    
    public int count(int n) {
    	Random r = new Random();
    	int result = 0;
    	while(count != 1) {
    		int rand1 = r.nextInt(n);
    		int rand2 = r.nextInt(n);
    		connect(rand1, rand2);
    		result++;
    	}
    	return result;
    }
    
    public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
        System.out.print("Enter a value for n: ");
        int n = input.nextInt();
        int trials = 5;
        int connections = 0;
        for (int i = 0; i < trials; i++) {
        	Alternative1 alt1 = new Alternative1(n);
        	connections = connections + alt1.count(n);
        }
        System.out.println("Number of connections: " + connections/trials);
    }
    
}