/*
 * DPHash.java
 * 
 * Created on Dec 15, 2003
 */
package com.mjnrand.dphashing.staticscale;

import java.util.ArrayList;
import java.util.List;

import com.mjnrand.dphashing.Bin;
import com.mjnrand.dphashing.DPHashImpl;
import com.mjnrand.dphashing.Entry;
import com.mjnrand.dphashing.UniversalHashFunction;

/**
 * DPHash is an implementation of Dynamic Perfect Hashing.  It allows for worst-case time
 * of O(1) for querying whether an element exists within the hash, and amortized time of
 * O(1) for inserting and deleting elements within the hash.
 * 
 * This implementation is based on the work done by Dietzfelbinger et. al in their paper
 * <i>Dyanamic Perfect Hashing: Upper and Lower Bounds</i> and uses the standard
 * doubling scheme for dynamically allocating the size of the table and bins.
 * 
 * @author mark
 */
public class DPHashStaticImpl extends DPHashImpl {
	/** Amount that the subtables should be scaled by */
	public static double SCALE = 2;

	/** Array representation of the DPHash that contains all bins within the hash */
	protected Bin[] dphash = null;
	
	/**
	 * Default constructor.  Creates an instance of DPHash using the given maximum
	 * universe size and prime number p that is >= |U|.
	 * 
	 * @param universeSize	maximum value that may be inserted into the hash
	 * @param p	prime number that is >= universeSize
	 * @param c	constant factor that DPHash.M is bigger than n
	 */
	public DPHashStaticImpl(int universeSize, int p, double scale) {
		DPHashImpl.MAX_UNIVERSE_SIZE = universeSize;
		DPHashImpl.P = p;
		DPHashStaticImpl.SCALE = scale;
		this.dphash = new Bin[DPHashImpl.SM];
		
		// setup the hash
		this.rehash(-1);
	}
	
	/**
	 * Insert the value x into the hash with the given data.
	 * 
	 * @param x	value to be inserted into the hash
	 * @param data	data to be stored with value
	 */
	public void insert(int x, Object data) {
		this.count++;
		
		if (this.count > DPHashImpl.M) {
			this.rehash(x);
		} else {
			int j = this.h.hash(x);
			int location = -1 ;
			
			if (this.dphash[j].m > 0) {
				location = this.dphash[j].h.hash(x);
			}
			
			if ((location != -1) && 
					(this.dphash[j].bin[location] != null) && 
					(this.dphash[j].bin[location].getValue() == x)) {
				if (this.dphash[j].bin[location].isDeleted()) {
					this.dphash[j].bin[location].setDeleted(false);
				}
			} else {
				this.dphash[j].b++;
				
				if (this.dphash[j].b <= this.dphash[j].m) {
					if (this.dphash[j].bin[location] == null) {
						this.dphash[j].bin[location] = new Entry(x, data);
					} else {
						List l = new ArrayList();
						
						for (int i=0; i < this.dphash[j].s; i++) {
							if (this.dphash[j].bin[i] != null && !this.dphash[j].bin[i].isDeleted()) {
								l.add(this.dphash[j].bin[i]);
							}
							
							// clean up references 
							this.dphash[j].bin[i] = null;							
						}
						
						l.add(new Entry(x, data));
						
						boolean injective = false;
						
						while (!injective) {
							injective = true;
							this.dphash[j].bin = null;
							this.dphash[j].bin = new Entry[this.dphash[j].s];
							this.dphash[j].h = UniversalHashFunction.generateHashFunction(this.dphash[j].s);
							
							for (int i=0; i < l.size(); i++) {
								Entry e = (Entry) l.get(i);
								int y = this.dphash[j].h.hash(e.getValue());
						
								if (this.dphash[j].bin[y] != null) {
									injective = false;
									break;
								}
						
								this.dphash[j].bin[y] = e;
							}
						}
						
						l.clear();
					}
				} else {
					this.dphash[j].m = (int) (DPHashStaticImpl.SCALE * Math.max(this.dphash[j].m, 1));
					
					// just a slight modification for the case when DPHash.SCALE < 2
					if (this.dphash[j].m == 1) {
						this.dphash[j].m++;
					}
					
					this.dphash[j].s = 2 * (this.dphash[j].m) * (this.dphash[j].m - 1);
					
					if (this.verify()) {
						List l = new ArrayList();
						
						if (this.dphash[j].bin != null) {
							for (int i=0; i < this.dphash[j].bin.length; i++) {
								if ((this.dphash[j].bin[i] != null) && (!this.dphash[j].bin[i].isDeleted())) {
									l.add(this.dphash[j].bin[i]);
								}
								
								// clean up references 
								this.dphash[j].bin[i] = null;							
							}
						}
						
						l.add(new Entry(x, data));
						
						boolean injective = false;
						
						while (!injective) {
							injective = true;
							this.dphash[j].bin = null;
							this.dphash[j].bin = new Entry[this.dphash[j].s];
							this.dphash[j].h = UniversalHashFunction.generateHashFunction(this.dphash[j].s);
							
							for (int i=0; i < l.size(); i++) {
								Entry e = (Entry) l.get(i);								
								int y = this.dphash[j].h.hash(e.getValue());
						
								if (this.dphash[j].bin[y] != null) {
									injective = false;
									break;
								}
								
								this.dphash[j].bin[y] = e;
							}
						}
						
						l.clear();
					} else {
						this.rehash(x);
					}
				}
			}
		}
	}
	
	/**
	 * Delete the value x from the hash, if it exists.
	 * 
	 * @param x	value to be deleted from the hash
	 */
	public void delete(int x) {
		this.count++;
		
		int j = this.h.hash(x);
		
		if (this.dphash[j] != null) {
			int location = this.dphash[j].h.hash(x);
			
			if (this.dphash[j].bin[location] != null) {
				this.dphash[j].bin[location].setDeleted(true);
			}
		}
		
		if (this.count >= DPHashStaticImpl.M) {
			this.rehash(-1);
		}
	}
	
	/**
	 * Returns true if the value x is stored in the hash, and false otherwise.
	 * 
	 * @param x	value to be searched for in the hash
	 * @return	true if the value is stored in the hash; false otherwise
	 */
	public boolean lookup(int x) {
		int j = this.h.hash(x);
		
		if ((this.dphash[j] != null) && (this.dphash[j].b > 0)) {
			int location = this.dphash[j].h.hash(x);
			
			if ((this.dphash[j].bin[location] != null) && 
					(this.dphash[j].bin[location].getValue() == x) &&
					(!this.dphash[j].bin[location].isDeleted())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Rehashes the entire table as a collision occurred, or the table grew too big.
	 * 
	 * @param x	value that has been inserted that caused rehashing; -1 if deletion caused rehashing
	 */
	protected void rehash(int x) {
		List l = new ArrayList();
		
		// gather all current entries in hash
		for (int i=0; i < DPHashImpl.SM; ++i) {
			if (this.dphash[i] != null && this.dphash[i].bin != null) {
				for (int j=0; j < this.dphash[i].bin.length; ++j) {
					if ((this.dphash[i].bin != null) && (this.dphash[i].bin[j] != null)) {
						if (!this.dphash[i].bin[j].isDeleted()) {
							l.add(this.dphash[i].bin[j]);
						}
						// clean up any references so that garbace collector can do it's job
						this.dphash[i].bin[j] = null;
					}
				}
				
				this.dphash[i].h = null;
				this.dphash[i].bin = null;
				this.dphash[i] = null;
			}
		}
		
		// get rid of old header table since it is gone now...
		this.dphash = null;
		
		// add value x if it is in U
		if (x != -1) {
			l.add(new Entry(x));
		}
		
		// set count to be the number of elements in the hash
		this.count = l.size();
		
		// set value of DPHash.M to be max(count, 4) * (1 + DPHash.C) and allocate header table
		DPHashStaticImpl.M = (1 + DPHashImpl.C) * Math.max(this.count, 4);
		DPHashStaticImpl.SM = DPHashImpl.M * 2;
		this.dphash = new Bin[DPHashImpl.SM];
		
		// rehash all elements using a new hash function until we meet the necessary conditions
		List[] sublists = new ArrayList[DPHashImpl.SM];
		
		do {
			this.h = UniversalHashFunction.generateHashFunction(DPHashImpl.SM);

			// clean up the sublists before we start this procedure
			for (int k=0; k < DPHashImpl.SM; ++k) {
				if (sublists[k] != null) {
					sublists[k].clear();
				} else {
					sublists[k] = new ArrayList();
				}
			}

			for (int i=0; i < l.size(); ++i) {
				Entry e = (Entry) l.get(i);
				int bucket = this.h.hash(e.getValue());
				
				sublists[bucket].add(e);
			}
			
			for (int j=0; j < DPHashImpl.SM; ++j) {
				if (sublists[j] == null) {
					// create empty list for those buckets that didn't get any elements
					sublists[j] = new ArrayList();
				}
				
				this.dphash[j] = null;
				this.dphash[j] = new Bin();
				this.dphash[j].b = sublists[j].size();
				this.dphash[j].m =  (int) (DPHashStaticImpl.SCALE * this.dphash[j].b);
				
				// special case for when DPHash.SCALE < 2
				if (this.dphash[j].m == 1) {
					this.dphash[j].m++;
				}
				
				this.dphash[j].s = 2 * (this.dphash[j].m) * (this.dphash[j].m -  1);
				this.dphash[j].h = UniversalHashFunction.generateHashFunction(this.dphash[j].s);
			}			
		} while (!this.verify());
		
		l.clear();
		
		// condition now holds so insert the elements into the appropriate bins
		for (int j=0; j < DPHashImpl.SM; ++j) {
			if ((sublists[j] != null) && (sublists[j].size() != 0)) {
				boolean injective = false;
				
				while (!injective) {
					this.dphash[j].bin = null;
					this.dphash[j].bin = new Entry[this.dphash[j].s];
					this.dphash[j].h = UniversalHashFunction.generateHashFunction(this.dphash[j].s);
				
					injective = true;
						
					for (int i=0; i < sublists[j].size(); ++i) {
						Entry e = (Entry) sublists[j].get(i);
						int location = this.dphash[j].h.hash(e.getValue());
						
						if (this.dphash[j].bin[location] != null) {
							injective = false;
							break;
						}
						
						this.dphash[j].bin[location] = e;
					}
				}
			}
			
			sublists[j].clear();
		}
	}
	
	/**
	 * Verify that the following condition holds true:
	 * 
	 * 	Sum of all dphash[j].s <= 32 * M^2 / SM + 4M
	 * 
	 * @return	true if the condition holds, false otherwise
	 */
	protected boolean verify() {
		int total = 0;
		int condition = (32 * (DPHashImpl.M^2)) / DPHashImpl.SM + 4 * DPHashImpl.M;
		
		for (int i=0; i < this.dphash.length; i++) {
			if (this.dphash[i] != null) {
				total += this.dphash[i].s;
			}
			
			// small optimization:  if we have already broken the condition, there is no need to keep going...
			if (total > condition) {
				return false;
			}
		}
							
		return (total <= condition);
	}
}
