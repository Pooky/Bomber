package my.game;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import my.game.common.AbstractMoveAbleShape;
import my.game.common.AbstractShape2D;
import my.game.common.Direction;
/**
 * Čtverec
 * @author martin.klima
 *
 */
public class Enemy extends AbstractMoveAbleShape
{
	
	private Direction direction;
	private float speed;
	private Texture textureRight;
	private Texture textureLeft;
	private Texture textureUp;
	
	
	/**
	 * Kostička
	 * @param x
	 * @param y
	 * @param size
	 */
	public Enemy(float x,float y, float size) {
		
		this.setLocation(x, y);
		this.size = size;
		this.direction = Direction.RIGHT;
		init();
	    
	}
	
	public Enemy(float x,float y, float size, float speed) {
		this(x, y, size);
		this.speed = speed;
	}

	public void tryMove(){
		
		if(direction.equals(Direction.RIGHT)){
			this.newX += this.speed;
		}else if(direction.equals(Direction.LEFT)){
			this.newX -= this.speed;
		}else if(direction.equals(Direction.UP)){
			this.newY += this.speed;
		}else if(direction.equals(Direction.DOWN)){
			this.newY -= this.speed;
		}
		
		
	}
	
	public void render(){
		
    	glEnable(GL_TEXTURE_2D);	
        Color.white.bind();
        if(direction.equals(Direction.LEFT)){
        	textureLeft.bind(); // or GL11.glBind(texture.getTextureID());
        }else if(direction.equals(Direction.RIGHT)){
        	textureRight.bind();
        }else{
        	textureUp.bind();
        }
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

	@Override
	public void init() {

        try {
			textureRight = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ghost-right2.png"));
			textureLeft = TextureLoader.getTexture("GIF", ResourceLoader.getResourceAsStream("res/ghost-left.gif"));
			textureUp = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ghost-up.png"));
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
	public void handleColision() {
	
		// change direciton
		this.direction = Direction.randomDirection();
			
	}
	


}
