/*
 * Bin.java
 * 
 * Created on Dec 15, 2003
 */
package com.mjnrand.dphashing;

/**
 * Bin is the class that represents a single level-2 bin of the DPHash.  Each bin is
 * indexed into the DPHash according to the result of hashing the value being
 * inserted, deleted or queried using the hash function of the DPHash.  The result
 * of the hash function is the location of the bin where the element is to be located.
 * 
 * Each bin maintains it's own hash function that is used to once again hash the
 * value being inserted, deleted or queried into the proper location of the bin.
 * 
 * @author mark
 */
public class Bin {
  /** Scale factor in which the size of the bin grows when full */
  public static int SCALE_FACTOR = 2;
  
  /** Hash function currently in use by this bin. */
  public HashFunction h = null;
  
  /** Number of values currently stored within this bin. */
  public int b = 0;
  
  /** Number of elements permitted within this bin */
  public int m = 0;
  
  /** Space allocated to this bin */
  public int s = 0;
  
  /** Array representation of the bin that stores all entries stored in the bin. */
  public Entry[] bin = null;
  
  /**
   * Constructor.  Creats an instance of Bin with the given universal family of hash
   * functions that is used to randomly generate hash functions.
   * 
   * @param H universal family of hash functions
   */
  public Bin() {
  }
}
