package com.ibm.ruler;
/**
 * This interface is implemented by all objects in the game. It allows
 * you to get the location of any object, and to determine the distance
 * and heading to or from the object.
 */
public interface IObject {
	/**
	 * Returns the id of this object. No two objects have the same id.
	 * 
	 * @return int
	 */
	public int getId();

	/**
	 * Returns the X position of the object.
	 * 
	 * @return int
	 */
	public int getX();

	/**
	 * Returns the Y position of the object.
	 * 
	 * @return int
	 */
	public int getY();

	/**
	 * Returns the ruler that commands this object.
	 * 
	 * @return com.ibm.ruler.IRuler
	 */
	public IRuler getRuler();

	/**
	 * Returns true if this object is alive, and false otherwise. All objects returned
	 * from the API methods will always be alive. This method only exists so that you
	 * can find out if any objects that you have cached between moves are still alive.
	 * 
	 * @return boolean
	 */
	public boolean isAlive();

	/**
	 * Returns the minimum number of squares (units) between this object and the given position.
	 * This is also the minimum number of moves that would be required to move to the given
	 * position.
	 * 
	 * @param int x
	 * @param int y
	 * @return int
	 */
	public int getDistanceTo(int x, int y);

	/**
	 * Returns the approximate direction (the closest of the 8 possible move directions)
	 * to the given position from this object.
	 *
	 * @param int x
	 * @param int y
	 * @return int
	 */
	public int getDirectionTo(int x, int y);
}