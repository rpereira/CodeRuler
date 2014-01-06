package com.ibm.ruler;
/**
 * This interface is implemented by all rulers.
 */
public interface IRuler {
	/**
	 * Returns the name of the ruler.
	 * 
	 * @return java.lang.String
	 */
	public String getName();

	/**
	 * Returns the name of the organization.
	 * 
	 * @return java.lang.String
	 */
	public String getOrganization();

	/**
	 * Returns the number of points that this ruler has accumulated
	 * during this match.
	 *
	 * @return int
	 */
	public int getPoints();

	/**
	 * Returns the castles owned by this ruler. If the ruler has no castles, an empty array will
	 * be returned.
	 * 
	 * @return com.ibm.ruler.ICastle[]
	 */
	public ICastle[] getCastles();

	/**
	 * Returns this ruler's peasants. If the ruler has no peasants, an empty array will be returned.
	 * 
	 * @return com.ibm.ruler.IPeasant[]
	 */
	public IPeasant[] getPeasants();

	/**
	 * Returns this ruler's knights. If the ruler has no knights, an empty array will be returned.
	 * 
	 * @return com.ibm.ruler.IKnight[]
	 */
	public IKnight[] getKnights();

	/**
	 * Returns the number of squares of land this ruler currently owns.
	 * 
	 * @return int
	 */
	public int getOwnedLandCount();
}