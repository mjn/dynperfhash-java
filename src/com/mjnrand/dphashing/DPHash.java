/*
 * DPHash.java
 * 
 * Created on Dec 17, 2003
 */
package com.mjnrand.dphashing;

/**
 * DPHash is the interface that all implementations of the dynamic perfect hashing data
 * structure must implement.  It declares the methods that must be supported by
 * any dynamic perfect hashing data structure.
 * 
 * @author mark
 */
public interface DPHash {
  /**
   * Insert the element with value x into the hash, and store the given data with
   * the entry.
   * 
   * @param x value of the element to be stored
   * @param data  data to be stored with the inserted element
   */
  public void insert(int x, Object data);
  
  /**
   * Delete the element with the value given from the hash, if it exists.
   * 
   * @param x value of the element to be deleted
   */
  public void delete(int x);
  
  /**
   * Returns true if the element with the value given is found in the hash and false
   * otherwise.
   * 
   * @param x value to be queried
   * @return  true if element with the given value is found; false otherwise
   */
  public boolean lookup(int x);
}
