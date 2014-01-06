import java.awt.Point;
import java.util.Random;
import com.ibm.ruler.*;
/**
 * This is the class that you must implement to enable your ruler within
 * the CodeRuler environment. Adding code to these methods will give your ruler
 * its personality and allow it to compete.
 * 
 * @author Rui Afonso Pereira
 * @date March/2013
 */
public class MyRuler extends Ruler
{
	/* (non-Javadoc)
	 * @see com.ibm.ruler.Ruler#getRulerName()
	 */
	public String getRulerName()
	{
		return "Comme Restus";
	}

	/* (non-Javadoc)
	 * @see com.ibm.ruler.Ruler#getSchoolName()
	 */
	public String getSchoolName()
	{
		return "201103890 RuiPereira";
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.ruler.Ruler#initialize()
	 */
	public void initialize() 
	{
		// put implementation here
	}
	
	/**
	 * Order my peasants to move. If the square is empty (does not contain a peasant, knight 
	 * or castle) and if the square is not already mine, the peasant will move to the adjacent
	 * square in order to claim the land. If the 8 possible move directions around the given 
	 * peasant are already mine, and if still there are enemies peasants left, he will move 
	 * in their direction, otherwise the given peasant will move randomly until a square that 
	 * is not owned by me is found. 
	 * 
	 * @param peasants - The peasant to give the order to
	 */
	private void movePeasants(IPeasant[] peasants)
	{		
		for(int i = 0; i < peasants.length; i++)
		{			
			IPeasant peasant = peasants[i];
			boolean didntMove = true;	// used to verify if the peasant has moved
			
			for(int j = 1; j <= 8; j++)		// 8 possible move directions
			{				
				Point p = World.getPositionAfterMove(peasant.getX(), peasant.getY(), j);
				
				if(p != null) 	// the point is not out of bounds
				{
					if(World.getObjectAt(p.x, p.y) == null)		 // the square is empty
					{
						if(World.getLandOwner(p.x, p.y) != this) // the square is not mine
						{
							move(peasant, j);
							didntMove = false; 	 // the peasant has moved
						}
					} else						
						continue; 	 // continue looking around
				}
			}			
			
			/*
			 * Sometimes when peasants are surrounded by their own (claimed) land, they get stuck.
			 * This flag will prevent that situation by pointing them enemies peasants' position,
			 * in order to claim their territory.
			 */
			if(didntMove) 
			{							
				IPeasant[] otherPeasants = World.getOtherPeasants();

				if(otherPeasants.length > 0) 	// still are enemies peasants 
				{
					/*
					 * Applying the main idea of an circular array, my peasants[i] will follow
					 * otherPeasants[i] in order to claim the territory they "leave behind."
					 * When I have more peasants than the enemy and when the end of the array 
					 * otherPeasants[i] is reached, applying the MOD (remaining) operator is
					 * possible to set the current index to "0" and as result the array 
					 * becomes a loop.  
					 */
					int direction = peasant.getDirectionTo(otherPeasants[i % otherPeasants.length].getX(),
                                                           otherPeasants[i % otherPeasants.length].getY());
					
					move(peasant, direction);
				} else
				{
					Random rand = new Random();
					move(peasant, rand.nextInt(1) + 6);
				}
			}
		}
	}

	/**
	 * Order my knights to move. First, they'll attempt to capture all castles and then, 
	 * they'll attempt to capture other knights and when there are only enemy peasants
	 * left, they'll attempt to capture them.
	 * 
	 * @param knights - The knight to give the order to
	 */
	private void moveKnights(IKnight[] knights) 
	{
		ICastle[]  otherCastles  = World.getOtherCastles();
		IKnight[]  otherKnights  = World.getOtherKnights();
		IPeasant[] otherPeasants = World.getOtherPeasants();	
		
		ICastle[] myCastles = getCastles();
			
		int j = 0;
		
		// Send one knight per castle in order to protect it
		if(otherCastles.length <= 3 || otherKnights.length < knights.length)
		{
			for(j = 0; j < myCastles.length; j++)
			{			
				protectCastle(knights[j], myCastles[j]);
			}
		}
		
		// exclude the first j knights 'cause they're assigned to protect one castle
		for(int i = j; i < knights.length; i++)
		{
			IKnight knight = knights[i];
			
			if(otherCastles.length > 0) 	// attack closest castle
			{
				// find closest enemy castle
				ICastle closestCastle = (ICastle) findClosestObject(knight,
																	otherCastles);
				
				// capture the closest castle
				moveAndCapture(knight, closestCastle);
			}
			else if(otherKnights.length > 0) 	// if all castles are mine, capture knights
			{		
				/*
				 * Applying the main idea of an circular array, my knights[i] will follow
				 * otherKnights[i] in order to capture them. When I have more knights than 
				 * the enemy and when the end of the array otherKnights[i] is reached, 
				 * applying the MOD (remaining) operator is possible to set the current 
				 * index to "0" and as result the array becomes a loop.  
				 */				
				moveAndCapture(knight, otherKnights[i % otherKnights.length]);
				
			} else if(otherPeasants.length > 0)    // finally, capture peasants
			{				
					captureObject(knight, otherPeasants[i % otherPeasants.length]);
			} else
				break;
		}
	}
	
	/**
	 * Order the specified knight to capture the given object. If there is an object
	 * (peasant, knight or castle) from an opposing ruler in an adjacent square, the 
	 * knight will attempt to capture it.
	 * 
	 * @param knight - The knight to give the order to 
	 * @param target - The object to capture
	 */
	private void moveAndCapture(IKnight knight, IObject target)
	{		
		// exit the function if the target is either null or already captured
		if(target == null || !target.isAlive())
			return;
		
		/*
		 * Before start moving in the direction of the given object in order to
		 * capture it, the given knight will take a look around and, if he finds
		 * an enemy unit, he'll attempt to capture it.
		 */
		for(int i = 1; i <= 8; i++)		// 8 possible move directions
		{		
			Point p = World.getPositionAfterMove(knight.getX(), knight.getY(), i);
			
			IObject obj = World.getObjectAt(p.x, p.y);
			
			if(p != null)
			{
				if(obj != null && obj.getRuler() != this) 	// if the object is not mine
				{
					capture(knight, i);
					return;
				}
			}
		}
		
		int direction = knight.getDirectionTo(target.getX(), target.getY());

		Point p = World.getPositionAfterMove(knight.getX(), knight.getY(),
											                direction);

		IObject obj = World.getObjectAt(p.x, p.y);
		
		/*
		 * If the square is empty, the knight keeps moving freely on his target's 
		 * direction, otherwise, if there is an object in the knight's way, that 
		 * object is mine for sure, so the solution is to move the given knight 
		 * randomly in order to unstuck them.
		 */
		if(obj == null)
		{
			move(knight, direction);			
		} else
		{
			Random rand = new Random();
			move(knight, rand.nextInt(7) + 1);
		}
	}
	
	/**
	 * Order the specified knight to capture the given object. This method is similar
	 * to moveAndCapture() method, although is quicker, in order to avoid Timeout in
	 * orderSubjects(), once the knight only focus on capturing his target.
	 * 
	 * @param knight - The knight to give the order to 
	 * @param target - The object to capture
	 */
	private void captureObject(IKnight knight, IObject target)
	{
		int direction = knight.getDirectionTo(target.getX(),
				target.getY());

		Point p = World.getPositionAfterMove(knight.getX(), knight.getY(),
		                                                    direction);
		
		IObject obj = World.getObjectAt(p.x, p.y);
		
		if(p != null) 	// the point is not out of bounds
		{
			if(obj != null && obj.getRuler() != this)  // capture if the object it's not mine
			{
				capture(knight, direction);
			} else 	// keep moving on the target's direction
			{
				move(knight, direction);
			}
		}
	}
	
	/**  
	 * Returns the nearest target object from the given unit object.
	 * 
	 * @param unit - The reference unit
	 * @param targets - The array that contains the targets to evaluate
	 * @return com.ibm.ruler.IObject[]
	 */
	private IObject findClosestObject(IObject unit, IObject[] targets)
	{
		int howFar = 0; 	// Auxiliary variable
		int nearest = 500;
		
		IObject nearestObject = null;
		
		for(IObject target : targets)
		{
			howFar = unit.getDistanceTo(target.getX(), target.getY());
			
			if(howFar < nearest)
			{
				nearest = howFar;
				nearestObject = target;
			}
		}
		
		return nearestObject;
	}
	
	/**
	 * Returns the corner point of the given castle.
	 * 
	 * @param castle - The castle that will be used to find the hiding square
	 * @return java.awt.Point
	 */
	private Point findCastleHiddingCorner(IObject castle)
	{
		// castle's position
		int coordX = castle.getX();
		int coordY = castle.getY();
		
		if(coordY > World.HEIGHT/2)
			coordY++;
		else
			coordY--;
		
		if(coordX < World.WIDTH/3)
			coordX--;
		else if(coordX > World.WIDTH/3*2)
			coordX++;
		
		return new Point(coordX, coordY);
	}
	
	/**
	 * Order the specified knight to protect the given castle. The knight will find the given
	 * castle hiding corner and he will stand and protect it, that, if the castle is "lost",
	 * they'll attempt to capture it again.
	 * 
	 * @param knight - The knight to give the order to
	 * @param castle - The castle to protect
	 */
	private void protectCastle(IKnight knight, IObject castle)
	{
		/*
		 * Before start moving in the direction of the given object in order to
		 * protect it, the given knight will take a look around and, if he finds
		 * an enemy unit, he'll attempt to capture it.
		 */
		for(int i = 1; i <= 8; i++)		// 8 possible move directions
		{		
			Point p = World.getPositionAfterMove(knight.getX(), knight.getY(), i);
			IObject obj = World.getObjectAt(p.x, p.y);
			
			if(obj != null && obj.getRuler() != this) 	// if the object is not mine
			{
				capture(knight, i);
				return;
			}
		}
		
		Point castleCorner = findCastleHiddingCorner(castle);
		int direction = knight.getDirectionTo(castleCorner.x, castleCorner.y);
		
		Point p = World.getPositionAfterMove(knight.getX(), knight.getY(), direction);
		IObject obj = World.getObjectAt(p.x, p.y);
		
		/*
		 * If the square is empty, the knight can move freely, otherwise, if there is 
		 * an object in the knight's way, that object is mine for sure, so the solution
		 * is to move the given knight randomly in order to unstuck them.
		 */
		if(obj == null)
		{
			move(knight, direction);
		} else
		{
			Random rand = new Random();
			move(knight, rand.nextInt(7) + 1);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.ruler.Ruler#orderSubjects(int)
	 */
	public void orderSubjects(int lastMoveTime)
	{
		ICastle[] myCastles   = getCastles();
		IKnight[] myKnights   = getKnights();
		IPeasant[] myPeasants = getPeasants();
		
		/*
		 * My castles' AI: units generation process.
		 */	
		if((myPeasants.length / (myKnights.length + 0.1)  < 0.5)
				                     && myPeasants.length <= 40)
		{
			for(ICastle castle : myCastles)
				createPeasants(castle);
		} else
		{
			for(ICastle castle : myCastles)
				createKnights(castle);
		}
		
		movePeasants(myPeasants);
		
		moveKnights(myKnights);
	}
}