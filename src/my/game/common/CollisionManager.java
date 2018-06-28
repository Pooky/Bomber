package my.game.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import my.game.Game;
import my.game.Player;
import my.game.Bomb;
import my.game.Enemy;

/**
 * Colision manager
 * @author martin.klima
 *
 */
public class CollisionManager {
	
	List<AbstractMoveAbleShape> objects = new ArrayList<AbstractMoveAbleShape>();
	BombManager bombManager;
	
	public CollisionManager(){
		
	}
	
	public void addObject(AbstractMoveAbleShape object){
		objects.add(object);
	}
	public void setBombManager(BombManager bombManager){
		this.bombManager = bombManager;
	}
	/**
	 * Kontrola kolize
	 */
	public void checkColisions(){
		
		// between objects
		for(AbstractMoveAbleShape objectToCheck : objects){
			
			//System.out.println(objectToCheck.toString());
			
			// check max windows size
			if(colisionWithWindow(objectToCheck)){
				objectToCheck.setColision(true);
			}
			
			// other objects
			for(AbstractMoveAbleShape object : objects){
				if(objectToCheck == object)
					continue;
				
				if(colision(objectToCheck, object)){
					objectToCheck.setColision(true);
					object.setColision(true);
				}
			}
		}
		
	}
	
	public void checkBombColisions() {

		Iterator<AbstractMoveAbleShape> itr = objects.iterator();
		while(itr.hasNext()){
			
			AbstractMoveAbleShape box = itr.next();
			for(Bomb bomb : bombManager.getBombs()){
				// bomba explodovala
				if(bomb.isExploded()){
					boolean collision = CollisionManager.colision(box.x, bomb.x, box.y, bomb.y, box.size, bomb.size);
					if(collision){
						itr.remove();
					}
				}
			}
		}
		
	}

	private boolean colisionWithWindow(AbstractMoveAbleShape objectToCheck) {
		
		float objectToCheckOuterX = objectToCheck.newX + objectToCheck.size;
		float objectToCheckOuterY = objectToCheck.newY + objectToCheck.size;
		
		if(objectToCheckOuterX > (Game.windowWidth - 40) || objectToCheckOuterY > (Game.windowHeight-40) ){
			return true;
		}
		if(objectToCheck.newX < 40  || objectToCheck.newY < 40){
			return true;
		}
		return false;
		
	}

	/**
	 * Controla kolize mezi dvěma boxíky
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	private boolean colision(AbstractMoveAbleShape obj1, AbstractMoveAbleShape obj2) {

		return colision(obj1.newX, obj2.newX, obj1.newY, obj2.newY, obj1.size, obj2.size);
		/*
		float objectToCheckOuterX = obj1.newX + obj1.size;
		float objectToCheckOuterY = obj1.newY + obj1.size;
		float objectOuterX = obj2.newX + obj2.size;
		float objectOuterY = obj2.newY + obj2.size;

		
		//if(object instanceof Player){
			
			System.out.println(objectToCheck.getClass() + " vs " + object.getClass());
			System.out.println(objectToCheck.newX + " < " + objectOuterX + " && "
			+ objectToCheckOuterX + " > " + object.newX + " && "
			+ objectToCheck.newY + " < " + objectOuterY + " && " 
			+ objectToCheckOuterY + " > " + object.newY );
			
		//}
		
		if (obj1.newX < objectOuterX &&
			objectToCheckOuterX  > obj2.newX &&
			obj1.newY < objectOuterY &&
			objectToCheckOuterY > obj2.newY) {
			
			
			/*System.out.println(objectToCheck.newX + " < " + objectOuterX + "and"
			+ objectToCheckOuterX + " > " + object.newX + "&&"
			+ objectToCheck.newY + " < " + objectOuterY + "&&" 
			+ objectToCheckOuterY + " > " + object.newY );
			return true;
		}
		return false;*/

	}
	/**
	 * Contorla kolize
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @param size1
	 * @param size2
	 * @return
	 */
	public static boolean colision(float x1, float x2, float y1, float y2, float size1, float size2){
		
		if (x1 < x2 + size2 &&
				x1 + size1  > x2 &&
				y1 < y2 + size2 &&
				y1 + size1 > y2) 
		{
			return true;
		}
		return false;
	}
	
	public void addObjects(List<Enemy> listOfBoxes) {
		this.objects.addAll(listOfBoxes);
	}

	public List<AbstractMoveAbleShape> getObjects() {
		return this.objects;
	}

	public void resetColissions() {
		for(AbstractMoveAbleShape object : objects){
			object.setColision(false);
		}
	}

	public void clear() {
		this.objects.clear();
	}

	public List<Enemy> getEnemies() {
		List<Enemy> enemies = new ArrayList<Enemy>();
		for(AbstractMoveAbleShape object : objects){
			if(object instanceof Enemy){
				enemies.add((Enemy)object);
			}
		}
		return enemies;
	}
	
	
	
	
}
