/*
 * Entry.java
 * 
 * Created on Dec 15, 2003
 */
package com.mjnrand.dphashing;

/**
 * Entry is the class that represents a single element within the DPHash.  It stores the value
 * of the entry, as well the data stored with the entry.  Also, the entry has a flag that
 * specifies whether the entry has been deleted from the DPHash and should be removed
 * once a rehashing occurs.
 * 
 * @author mark
 */
public class Entry {
  /** Value of this entry */
  protected int value = -1;
  
  /** Data stored with this entry */
  protected Object data = null;
  
  /** Flag telling whether this entry has been deleted and should be removed or not */
  protected boolean isDeleted = false;
  
  /**
   * Constructor.  Creates and instance of Entry with the given value.
   * 
   * @param value value of the entry being created
   */
  public Entry(int value) {
    this(value, null);
  }
  
  /**
   * Constructor.  Creates an instance of Entry with the given value and data.
   * 
   * @param value value of the entry being created
   * @param data  data stored with the entry
   */
  public Entry(int value, Object data) {
    this.value = value;
    this.data = data;
  }
  
  /**
   * Returns the value of this entry.
   * 
   * @return  value of this entry
   */
  public int getValue() {
    return this.value;
  }
  
  /**
   * Returns the data stored with this entry.
   * 
   * @return  data stored with this entry
   */
  public Object getData() {
    return this.data;
  }
  
  /**
   * Returns true if this entry has been marked for deletion and false otherwise.
   * 
   * @return  true if this entry is marked for deletion; false otherwise
   */
  public boolean isDeleted() {
    return this.isDeleted;
  }
  
  /**
   * Sets whether this entry should be marked for deletion or not.
   * 
   * @param isDeleted if true, this entry is marked for deletion
   */
  public void setDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
}
