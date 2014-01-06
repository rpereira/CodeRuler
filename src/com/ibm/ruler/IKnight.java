package com.ibm.ruler;
/**
 * This interface is implemented by all knights. It defines only one new method. See the IObject
 * interface for information on inherited methods.
 */
public interface IKnight extends IObject {
	/**
	 * Returns the strength left in this knight. The initial strength of all knights is
	 * 100, and they lose between 15 and 30 strength when they are attacked, and gain
	 * 20 for each knight that they defeat.  
	 *
	 * @return int
	 */
	public int getStrength();
}