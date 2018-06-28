package my.game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import my.game.common.AbstractMoveAbleShape;
import my.game.common.AbstractShape2D;

/**
 * Object hráče
 * @author martin.klima
 *
 */
public class Player extends AbstractMoveAbleShape {
	
	public static final String DOWN = "down";
	public static final String UP = "up";
	public static final String RIGHT = "right";
	public static final String LEFT = "left";

	private String direction;
	private Texture texture;

	public Player(){
		init();
	}

	@Override
	public void init() {
		this.size = 40;
		
        try {
			texture = TextureLoader.getTexture("GIF", ResourceLoader.getResourceAsStream("res/bomberman.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render() {

		if(hasColision()){
			glColor4f(1, 0, 0, 1);
		}else{
			glColor4f(1, 1, 0, 1);
		}
		
    	glEnable(GL_TEXTURE_2D);	
        Color.white.bind();
        texture.bind();
		
	    glBegin(GL_QUADS);
	    	glTexCoord2f(0, 0);
	        glVertex2f(x, y);
	        glTexCoord2f(0,1);
	        glVertex2f(x, y+size);
	        glTexCoord2f(1,1);
	        glVertex2f(x+size, y+size);
	        glTexCoord2f(1,0);
	        glVertex2f(x+size, y);
	    glEnd();
	    glDisable(GL_TEXTURE_2D);
		
	}
	
	/**
	 * Pohyb hráče
	 * @param direction
	 */
	public void tryMove(String direction) {
		this.direction = direction;
		tryMove();
	}
	
	/**
	 * Skutečné posunutí, pokud nemáme kolizi
	 */
	public void move(){

		this.x = this.newX;
		this.y = this.newY;
		
	}
	
	public float getOuterX(){
		return newX + this.size;
	}
	public float getOuterY(){
		return newY + this.size;
	}
	
	@Override
	public void tryMove() {
		
		this.newX = this.x;
		this.newY = this.y;
		
		if(direction.equals(Player.DOWN)){
			this.newY += 10;
		}else if(direction.equals(Player.UP)){
			this.newY -= 10;
		}else if(direction.equals(Player.RIGHT)){
			this.newX += 10;
		}else if(direction.equals(Player.LEFT)){
			this.newX -= 10;
		}
		
		
	}

	@Override
	public void handleColision() {
		//this.size -= 0.5;	
		

		
	}
	
	

}
