package my.game.common;

/*
 * Pohyblivý objekt
 */
public abstract class AbstractMoveAbleShape extends AbstractShape2D {

	protected float newX;
	protected float newY;
	protected float size;
	
	protected boolean colision;

	
	@Override
	public void setLocation(float x, float y) {
		this.newX = x;
		this.newY = y;
		super.setLocation(x, y);
	}
	
	public void move(){
		
		if(this.newX > 0)
			this.x = this.newX;
		if(this.newY > 0)
			this.y = this.newY;
		
	}

	public float getNewX() {
		return newX;
	}

	public void setNewX(float newX) {
		this.newX = newX;
	}

	public float getNewY() {
		return newY;
	}

	public void setNewY(float newY) {
		this.newY = newY;
	}

	public void setColision(boolean colision) {
		this.colision = colision;
	}

	public boolean hasColision() {
		return this.colision;
	}
	
	public String toString(){
		return this.getClass() + " = x:" + this.x + " y: " + this.y + " newX:" + this.newX + " newY:" + this.newY;
	}
	
	public void resetMove(){
		// reset newX and newY
		this.newX = x;
		this.newY = y;
	}
	
	public abstract void tryMove();
	public abstract void handleColision();
	
}
