/*
 * Created on Dec 17, 2003
 */
package com.mjnrand.dphashing.dynamicscale;

import com.mjnrand.dphashing.Bin;

/**
 * @author mark
 */
public class DynamicBin extends Bin {
  protected static double MAX_SCALE = 2.5;
  protected static double MIN_SCALE = 1.5;
  
  public double scale = 2;
  private boolean increment = false;
  private double step = 0.25;
  
  public DynamicBin(double scale, double step, boolean increment) {
    this.scale = scale;
    this.step = step;
  }
  
  public void update() {
    // determine wheter we are incrementing or decrementing...
    if (increment) {
      // only increase scale value to max value of MAX_SCALE
      if (this.scale < MAX_SCALE) {
        if ((this.scale + this.step) > MAX_SCALE) {
          this.scale = MAX_SCALE;
        } else {
          this.scale += this.step;
        }
      }
    } else {
      // only reduce scale value to min value of MIN_SCALE
      if (this.scale > MIN_SCALE) {
        if ((this.scale - this.step) < MIN_SCALE) {
          this.scale = MIN_SCALE;
        } else {
          this.scale -= this.step;
        }
      }
    }
  }
}
