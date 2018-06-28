package my.game.common;

/*
 * Interface pro 2D objekty
 */
public interface Shape2D {

	public float getX();
	public float getY();
	
    public void setX(float x);
    public void setY(float y);
    
    public void setLocation(float x, float y);
	
    public void init(); // init
	public void destroy(); // destroy
	public void render(); // render or draw
	
}
