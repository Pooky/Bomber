package my.game.common;

/*
 * Abstraktní třída pro implementaci základních metod
 */
public abstract class AbstractShape2D implements Shape2D {
	
	protected float x;
	protected float y;

	public float getX() { return x; }
	public float getY() { return y; }
	
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    
    public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
    }
    
	public abstract void init();
	public abstract void destroy();
	public abstract void render();
	
	
}
