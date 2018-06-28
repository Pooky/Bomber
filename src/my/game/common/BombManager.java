package my.game.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import my.game.Bomb;
import my.game.Enemy;

public class BombManager {

	private List<Bomb> bombs;

	public BombManager(){
		bombs = new ArrayList<Bomb>();
	}
	
	public void addBomb(Bomb bomb){
		
		if(this.bombs.size() < 3)
			this.bombs.add(bomb);
	}
	

	public void renderBombs() {

		Iterator<Bomb> itr = bombs.iterator();
		while(itr.hasNext()){
			Bomb bomb = itr.next();
			bomb.render();
			
			if(bomb.canBeDeleted()){
				itr.remove();
			}
		}
		
	}

	public void clear() {
		this.bombs.clear();
	}

	public List<Bomb> getBombs() {
		return bombs;
	}

	
}
