package com.ibm.ruler;

import java.awt.Point;
/**
 * This class allows you to get information about the CodeRuler environment.
 */
public class World {
	/**
	 * The maximum number of turns in each match.
	 */
	public static final int MAX_TURNS = 500;

	/**
	 * The width of the "world" in units.
	 */
	public static final int WIDTH = 72;
	
	/**
	 * The height of the "world" in units.
	 */
	public static final int HEIGHT = 64;

	/**
	 * Returns the number of turns that have occurred so far during this match.
	 *
	 * @return int
	 */
	public static int getCurrentTurn() {
		return 0;
	}

	/**
	 * Returns an array of all the other rulers that you are currently competing
	 * against in this match. This array will not include your own ruler, and will
	 * always return the rulers in the same order.
	 *
	 * @return com.ibm.ruler.IRuler[]
	 */
	public static IRuler[] getOtherRulers() {
		return null;
	}

	/**
	 * Returns an array of all the peasants from other rulers that are currently in this match.
	 * This array will not include your own peasants, will always return the peasants in the same
	 * order, and will return an empty array if there are no other peasants.
	 *
	 * @return com.ibm.ruler.IPeasant[]
	 */
	public static IPeasant[] getOtherPeasants() {
		return null;
	}
	
	/**
	 * Returns an array of all the knights from other rulers that are currently in this match.
	 * This array will not include your own knights, will always return the knights in the same
	 * order, and will return an empty array if there are no other knights.
	 *
	 * @return com.ibm.ruler.IKnight[]
	 */
	public static IKnight[] getOtherKnights() {
		return null;
	}

	/**
	 * Returns an array of all the castles from other rulers that are currently in this match.
	 * This array will not include your own castles, will always return the castles in the same
	 * order, and will return an empty array if there are no other castles.
	 *
	 * @return com.ibm.ruler.ICastle[]
	 */
	public static ICastle[] getOtherCastles() {
		return null;
	}

	/**
	 * Returns the peasant, knight, or castle that is at the given location, or null if there is
	 * nothing at the given location or it is out of bounds.
	 *
	 * @param x - An x position
	 * @param y - A y position
	 * @return com.ibm.ruler.IObject
	 */
	public static IObject getObjectAt(int x, int y) {
		return null;
	}

	/**
	 * Returns the ruler that has claimed the land at the given position, or null if the
	 * position is out of bounds or no ruler has claimed the land.
	 *
	 * @param x - An x position
	 * @param y - A y position
	 * @return com.ibm.ruler.IRuler
	 */
	public static IRuler getLandOwner(int x, int y) {
		return null;
	}

	/**
	 * Returns the coordinates of the square in the given direction from the given point.
	 * Returns null if the square is out of bounds.
	 * 
	 * @param x - An x position
	 * @param y - A y position
	 * @param direction - The direction in which to return the position
	 * @return java.awt.Point
	 */
	public static Point getPositionAfterMove(int x, int y, int direction) {
		return null;
	}
}