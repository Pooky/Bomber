package my.game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import java.util.Date;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import my.game.common.AbstractShape2D;

public class Bomb extends AbstractShape2D {

	public int size;
	private Texture texture;
	private Date timeCreated;
	private Texture textureExploded;
	
	private boolean exploded;
	private static final int explodedSize = 120;
	
	
	public Bomb(){
		init();
		this.timeCreated = new Date();
		this.exploded = false;
	}


	@Override
	public void init() {
		
		this.size = 40;
		
        try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/bomb.png"));
			textureExploded = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/explode.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isExploded(){
		return this.exploded;
	}

	/**
	 * Exploded
	 * @return
	 */
	public boolean canBeDeleted(){
		
		if((new Date().getTime() - timeCreated.getTime()) > 4000 && exploded){
			return true;
		}
		return false;
	}
	
	@Override
	public void render() {
		
		if(!isExploded()){
			this.exploded = explode();
		}
		if(this.exploded && this.size < explodedSize){
			this.size += 1;	
		}
		
    	glEnable(GL_TEXTURE_2D);	
        Color.white.bind();
		
		if(isExploded()){
			textureExploded.bind();
		}else{
			texture.bind();
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
	
	public boolean explode(){
		
		if((new Date().getTime() - timeCreated.getTime()) > 3000){
			return true;
		}
		return false;
		
	}

}
