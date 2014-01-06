package com.ibm.ruler;
/**
 * This class is the superclass of the Ruler that you implement. This
 * class contains helper methods to find out about the state of the world.
 */
public abstract class Ruler implements IRuler {
	public final static int MOVE_NONE = 0; // do nothing (used to cancel a move)
	public final static int MOVE_N  = 1; // north
	public final static int MOVE_NE = 2; // northeast
	public final static int MOVE_E  = 3; // east
	public final static int MOVE_SE = 4; // south east
	public final static int MOVE_S  = 5; // south
	public final static int MOVE_SW = 6; // south west
	public final static int MOVE_W  = 7; // west
	public final static int MOVE_NW = 8; // north west

	public Ruler() { }

	/* (non-Javadoc)
	 * @see com.ibm.ruler.IRuler#getPoints()
	 */
	public final int getPoints() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.ibm.ruler.IRuler#getCastles()
	 */
	public final ICastle[] getCastles() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.ruler.IRuler#getPeasants()
	 */
	public final IPeasant[] getPeasants() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.ruler.IRuler#getKnights()
	 */
	public final IKnight[] getKnights() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.ruler.IRuler#getOwnedLandCount()
	 */
	public final int getOwnedLandCount() {
		return 0;
	}

	/**
	 * Order the specified peasant to move in the given direction. If the square is empty
	 * (does not contain a peasant, knight, or castle), the peasant will move to the
	 * adjacent square. If the square that it moves into is not already claimed
	 * by your ruler, the peasant will claim the land.
	 * 
	 * @param peasant - The peasant to give the order to
	 * @param direction - The direction that the peasant should move
	 */
	public final void move(IPeasant peasant, int direction) {	}

	/**
	 * Order the specified knight to move in the given direction. If the square is empty
	 * (does not contain a peasant, knight, or castle), the knight will move to the
	 * adjacent square. Knights do not claim land like peasants.
	 *
	 * @param knight - The knight to give the order to
	 * @param direction - The direction that the knight should move
	 */
	public final void move(IKnight knight, int direction) { }

	/**
	 * Order the specified knight to capture in the given direction. If there is a peasant, knight
	 * or castle from an opposing ruler in the square in the specified direction, the knight will
	 * attempt to capture it. If the square is empty, the knight will not do anything. The knight's
	 * location will not change in either case.
	 * 
	 * @param knight - The knight to give the order to
	 * @param direction - The direction the knight should capture in
	 */
	public final void capture(IKnight knight, int direction) { }

	/**
	 * Order the specified castle to start producing new peasants. The castle will
	 * produce peasants faster when the ruler has more land.
	 * 
	 * @param castle - The castle to give the order to
	 */
	public final void createPeasants(ICastle castle) { }

	/**
	 * Order the specified castle to start producing new knights. The castle will
	 * produce knights faster when the ruler has more land.
	 * 
	 * @param castle - The castle to give the order to
	 */
	public final void createKnights(ICastle castle) { }

	/**
	 * Returns the name of your ruler.
	 * 
	 * @return java.lang.String
	 */
	public final String getName() {
		return null;
	}

	/**
	 * Returns the organization of the ruler.
	 * 
	 * @return java.lang.String
	 */
	public final String getOrganization() {
		return null;
	}

	/* ----- Methods to implement in MyRuler ----- */

	/**
	 * Called to give you a chance to do initialization. This method
	 * will be called at the beginning of each match, and you will
	 * have a limited amount of time to do initialization. All of your
	 * objects and your opponents' objects will already exist.
	 */
	public abstract void initialize();

	/**
	 * This method is called each turn to allow you to give orders to your peasants,
	 * knights, and castles. The parameter specifies the length of time (in ms) that
	 * the last call to orderSubjects() took.
	 * (The first time that this method is called, lastMoveTime is -1)
	 *
	 * @param int lastMoveTime
	 */
	public abstract void orderSubjects(int lastMoveTime);
}