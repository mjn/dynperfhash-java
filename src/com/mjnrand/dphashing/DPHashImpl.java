/*
 * Created on Dec 17, 2003
 */
package com.mjnrand.dphashing;

/**
 * @author mark
 */
public abstract class DPHashImpl implements DPHash {
  /** Maximum size of the universe, U, that can be stored in the DPHash */ 
  public static int MAX_UNIVERSE_SIZE = 0;
  
  /** Prime number >= MAX_UNIVERSE_SIZE */
  public static int P = 0;
  
  /** Constant factor in which the top level of the DPHash grows */
  public static int C = 2;
  
  /** Number of elements accomadated within hash */
  public static int M = 0;
  
  /** Number of sets that top level hash partitions S into */
  public static int SM = 0;

  /** Hash function currently being used by the DPHash */
  protected HashFunction h = null;
  
  /** Number of updates performed on this DPHash */
  protected int count = 0;
  
  /**
   * @see com.mjnrand.dphashing.DPHash#insert(int, java.lang.Object)
   */
  public abstract void insert(int x, Object data);
  
  /**
   * @see com.mjnrand.dphashing.DPHash#delete(int)
   */
  public abstract void delete(int x);
  
  /**
   * @see com.mjnrand.dphashing.DPHash#lookup(int)
   */
  public abstract boolean lookup(int x);
}
