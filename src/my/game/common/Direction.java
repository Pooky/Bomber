package my.game.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Direction {
	RIGHT, LEFT, UP, DOWN;
	
	 private static final List<Direction> VALUES =
	    Collections.unmodifiableList(Arrays.asList(values()));
	  private static final int SIZE = VALUES.size();
	  private static final Random RANDOM = new Random();

	  public static Direction randomDirection(){
		  return VALUES.get(RANDOM.nextInt(SIZE));
	  }
	  public static Direction randomDirection(Direction exclude)  {
		  
		  Direction x;
		  do{
			  x = randomDirection();
		  }while(x.equals(exclude));
		
		  return x;
	  }
}


