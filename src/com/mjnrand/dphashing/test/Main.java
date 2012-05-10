/*
 * Main.java
 * 
 * Created on Dec 17, 2003
 */
package com.mjnrand.dphashing.test;

import com.mjnrand.dphashing.dynamicscale.DPHashDynamicImpl;
import com.mjnrand.dphashing.staticscale.DPHashStaticImpl;

/**
 * Main is the application that tests the dynamic perfect hash.
 * 
 * @author mark
 */
public class Main {
  protected static int UNIVERSE_SIZE = 10000;
  
  protected static int PRIME =  10007;
  
  /**
   * Main test driver program.
   * 
   * @param args  command line arguments
   */
  public static void main(String args[]) {
    testStaticImpl();
    testDynamicImpl(false);
    testDynamicImpl(true);
  }

  /**
   * Test the static dynamic perfect hash using sequential data.  This scenario tests the
   * static implementation using 5 different scale factors, and runs each test 5 times to
   * get an average running time for each step of the test.
   */
  public static void testStaticImpl() {
    int[] testSizes = { 1000, 2500, 5000, 7500, 10000 };
    double[] scales = { 1.5, 1.75, 2.0, 2.25, 2.5 }; 
    int numTests = 5;
    long start, end;
    
    System.out.println("STARTING STATIC SCALE TEST - SEQUENTIAL DATA\n");
    
    for (int l=0; l < scales.length; l++) {
      System.out.println("Static scale test: Scale = " + scales[l]);
      
      for (int m=0; m < numTests; m++) {
        System.out.println("========== Test run #" + (m + 1) + " ==========\n");
        
        for (int test=0; test < testSizes.length; test++) {
          DPHashStaticImpl hash = new DPHashStaticImpl(Main.UNIVERSE_SIZE, Main.PRIME, scales[l]);
          
          System.out.println("Test size: " + testSizes[test]);
          
          System.out.println("Inserting elements into hash...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test]; i++) {
            hash.insert(i, null);
          }
          end = System.currentTimeMillis();
          System.out.println("Finished inserting elements: " + (end - start) + " ms");
          
          System.out.println("Querying elements that should be in hash...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test]; i++) {
            if (!hash.lookup(i)) {
              System.out.println("Couldn't find " + i + " but should have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that should be in hash: " + (end-start) + " ms");
          
          System.out.println("Querying elements that should not be in hash...");
          start = System.currentTimeMillis();
          for (int j=testSizes[test]; j < 2 * testSizes[test]; j++) {
            if (hash.lookup(j)) {
              System.out.println("Found " + j + " but shouldn't have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that should not be in hash: " + (end-start) + " ms");
          
          System.out.println("Deleting half the elements...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test] / 2; i++) {
            hash.delete(i);
          }
          end = System.currentTimeMillis();
          System.out.println("Finished deleting elements: " + (end-start) + " ms");
          
          System.out.println("Querying elements that were just deleted...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test] / 2; i++) {
            if (hash.lookup(i)) {
              System.out.println("Found " + i + " but shouldn't have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that were deleted:" + (end-start) + " ms");
          
          System.out.println("Querying elements that should exist...");
          start = System.currentTimeMillis();
          for (int i=testSizes[test] / 2; i < testSizes[test]; i++) {
            if (!hash.lookup(i)) {
              System.out.println("Couldn't find " + i + " but should have.");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying existing elements: " + (end-start) + " ms");
      
          System.out.println("Re-insert previously deleted elements...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test] / 2; i++) {
            hash.insert(i, null);
          }
          end = System.currentTimeMillis();
          System.out.println("Finished re-inserting elements: " + (end-start) + " ms");   
      
          System.out.println("Querying all elements that should be in the hash...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test]; i++) {
            if (!hash.lookup(i)) {
              System.out.println("Couldn't find " + i + " but should have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that should be in the hash: " + (end-start) + " ms");
          
          System.out.println("Deleting all the elements...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test]; i++) {
            hash.delete(i);
          }
          end = System.currentTimeMillis();
          System.out.println("Finished deleting elements: " + (end - start) + " ms");
          
          System.out.println("Querying elements that should not be in hash...");
          start = System.currentTimeMillis();
          for (int j=0; j < testSizes[test]; j++) {
            if (hash.lookup(j)) {
              System.out.println("Found " + j + " but shouldn't have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that should not be in hash: " + (end - start) + " ms");
        }
      }
    }   
  }
  
  /**
   * Tests the dynamic bin implementation of the dynamic perfect hashing data structure.
   * This scenario tests 5 different bin scaling decrements, and it runs each test 5 times
   * in order to get an average for each step of the test.
   */
  public static void testDynamicImpl(boolean increment) {
    int[] testSizes = { 1000, 2500, 5000, 7500, 10000 };
    double[] steps = { .05, .1, .15, .2, .25 };
    int numTests = 5;
    long start, end;
    
    System.out.println("STARTING DYNAMIC SCALE TEST - SEQUENTIAL DATA\n");
    
    for (int l=0; l < steps.length; l++) {
      System.out.println("Dynamic scale test: Decrement = " + steps[l]);
      
      for (int m=0; m < numTests; m++) {
        System.out.println("========== Test run #" + (m + 1) + " ==========\n");
        
        for (int test=0; test < testSizes.length; test++) {
          DPHashDynamicImpl hash = new DPHashDynamicImpl(Main.UNIVERSE_SIZE, Main.PRIME, steps[l], increment);
          
          System.out.println("Test size: " + testSizes[test]);
          
          System.out.println("Inserting elements into hash...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test]; i++) {
            hash.insert(i, null);
          }
          end = System.currentTimeMillis();
          System.out.println("Finished inserting elements: " + (end-start) + " ms");
          
          System.out.println("Querying elements that should be in hash...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test]; i++) {
            if (!hash.lookup(i)) {
              System.out.println("Couldn't find " + i + " but should have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that should be in hash: " + (end-start) + " ms");
          
          System.out.println("Querying elements that should not be in hash...");
          start = System.currentTimeMillis();
          for (int j=testSizes[test]; j < 2 * testSizes[test]; j++) {
            if (hash.lookup(j)) {
              System.out.println("Found " + j + " but shouldn't have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that should not be in hash: " + (end-start) + " ms");
          
          System.out.println("Deleting half the elements...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test] / 2; i++) {
            hash.delete(i);
          }
          end = System.currentTimeMillis();
          System.out.println("Finished deleting elements: " + (end-start) + " ms");
          
          System.out.println("Querying elements that were just deleted...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test] / 2; i++) {
            if (hash.lookup(i)) {
              System.out.println("Found " + i + " but shouldn't have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that were deleted: " + (end-start) + " ms");
          
          System.out.println("Querying elements that should exist...");
          start = System.currentTimeMillis();
          for (int i=testSizes[test] / 2; i < testSizes[test]; i++) {
            if (!hash.lookup(i)) {
              System.out.println("Couldn't find " + i + " but should have.");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying existing elements: " + (end-start) + " ms");
      
          System.out.println("Re-insert previously deleted elements...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test] / 2; i++) {
            hash.insert(i, null);
          }
          end = System.currentTimeMillis();
          System.out.println("Finished re-inserting elements: " + (end-start) + " ms");   
      
          System.out.println("Querying all elements that should be in the hash...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test]; i++) {
            if (!hash.lookup(i)) {
              System.out.println("Couldn't find " + i + " but should have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that should be in the hash: " + (end-start) + " ms");
          
          System.out.println("Deleting all the elements...");
          start = System.currentTimeMillis();
          for (int i=0; i < testSizes[test]; i++) {
            hash.delete(i);
          }
          end = System.currentTimeMillis();
          System.out.println("Finished deleting elements: " + (end-start) + " ms");
          
          System.out.println("Querying elements that should not be in hash...");
          start = System.currentTimeMillis();
          for (int j=0; j < testSizes[test]; j++) {
            if (hash.lookup(j)) {
              System.out.println("Found " + j + " but shouldn't have");
            }
          }
          end = System.currentTimeMillis();
          System.out.println("Finished querying elements that should not be in hash: " + (end-start) + " ms");
        }
      }
    }
  }
}
