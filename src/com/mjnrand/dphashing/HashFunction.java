/*
 * HashFunction.java
 * 
 * Created on Dec 15, 2003
 */
package com.mjnrand.dphashing;

/**
 * @author mark
 */
public class HashFunction {
	/** Variables of the hash function */
	protected int a = -1;
	protected int b = -1;
	protected int m = -1;
	
	/**
	 * Constructor.  Creates an instance of HashFunction that is used for hashing a value.
	 * 
	 * @param a	(DPHash.P-1) >= a >= 1
	 * @param b	(DPHash.P-1) >= b >= 0
	 * @param m	size of the table that this hash function sends elements to
	 */
	public HashFunction(int a, int b, int m) {
		this.a = a;
		this.b = b;
		this.m = m;
	}
	
	/**
	 * Hash the given value and return the result.
	 * 
	 * @param x	value to be hashed
	 * @return	result of hasing the given value using this hash function
	 */
	public int hash(int x) {
		int result = (((a * x + b) % DPHashImpl.P) % this.m);
		
		return result;
	}
	
	public String toString() {
		return "a = " + a + ", b = " + b;
	}
}
