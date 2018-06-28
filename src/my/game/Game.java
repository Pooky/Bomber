package my.game;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import my.game.common.AbstractMoveAbleShape;
import my.game.common.BombManager;
import my.game.common.CollisionManager;
import my.game.common.State;

import org.newdawn.slick.Color;


import static org.lwjgl.opengl.GL11.*;

/**
 * Incializace hry
 * @author martin.klima
 *
 */
public class Game {
	
	public final static int windowWidth = 800;
	public final static int windowHeight = 600; 
	
	// stav hry
	private static State state = State.INTRO;
	private List<Enemy> listOfBoxes;
	private Texture background;
	
	// manager
	private CollisionManager colisionManager;
	private BombManager bombManager;
	
	
	// player 
	private boolean directionRight;
	private boolean directionLeft;
	private boolean directionUp;
	private boolean directionDown;
	private Player player;
	private boolean winner;
	private Texture winnerTexture;
	
    public void start() {
    	
    	initGL(windowWidth, windowHeight);
    	
    	colisionManager = new CollisionManager();
    	bombManager = new BombManager();
    	colisionManager.setBombManager(bombManager);
    	winner = false;
   
    	initTextures();
	    initShapes();
	 
		// init OpenGL here
		while (!Display.isCloseRequested()) {
			
			handleInput();
			
			if(winner){
				showWinner();
			}else{
				render();
			}
		
	
		    // render OpenGL here
            Display.update();
            Display.sync(100);
		}
		
		Display.destroy();
		System.exit(0);
    }
    
    private void showWinner() {
		
    	glClear(GL_COLOR_BUFFER_BIT);
    	
    	glEnable(GL_TEXTURE_2D);	
        Color.cyan.bind();
        winnerTexture.bind();
        
	    glBegin(GL_QUADS);
	    	glTexCoord2f(0, 0);
	        glVertex2f(0, 0);
	        glTexCoord2f(0, 1);
	        glVertex2f(0, 600);
	        glTexCoord2f(1,1);
	        glVertex2f(800, 600);
	        glTexCoord2f(1,0);
	        glVertex2f(800, 0);
	    glEnd();
	    glDisable(GL_TEXTURE_2D);
		
	}

	/**
     * Incializace textury
     */
    private void initTextures() {

    	try {
            // load texture from PNG file
          background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background-edited.png"));
          winnerTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/winner.png"));
          
        } catch (IOException e) {
            e.printStackTrace();
        }

		
	}
	/**
     * Incializace tvarů
     */
    private void initShapes(){
    	
    	colisionManager.clear();
    	
    	for(int i = 1; i < 9; i++){
    		colisionManager.addObject(new Enemy(40, 50 * (i), 40, 1f));
    	}
    	
    	player = new Player();
    	player.setLocation(windowWidth/2,400);
    	
    	// přidání do colission manageru
    	colisionManager.addObject(player);
    	
    	bombManager.clear();
    	
    }
    
    /**
     * Initialise the GL display
     * 
     * @param width The width of the display
     * @param height The height of the display
     */
    private void initGL(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        // barva pozadí
        GL11.glClearColor(1f, 1f, 1f, 0.2f);          
         
            // enable alpha blending
           	GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         
            GL11.glViewport(0,0,width,height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
 
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    /**
     * Funkce pro rendrování
     */
    private void render(){
    	
    	// vyčištění bufferu
    	glClear(GL_COLOR_BUFFER_BIT);
    	
    	// render pozadí
    	glEnable(GL_TEXTURE_2D);	
        Color.white.bind();
        background.bind(); // or GL11.glBind(texture.getTextureID());
         
        glBegin(GL11.GL_QUADS);
            glTexCoord2f(0,0);
            glVertex2f(0,0);
            glTexCoord2f(1,0);
            glVertex2f(background.getTextureWidth(),0);
            glTexCoord2f(1,1);
            glVertex2f(background.getTextureWidth(),background.getTextureHeight());
            glTexCoord2f(0,1);
            glVertex2f(0,background.getTextureHeight());
        glEnd();
        glDisable(GL_TEXTURE_2D);
    	
    	// renderovani baoxiku
        for(AbstractMoveAbleShape object : colisionManager.getObjects()){
        	object.render();
        }
    	bombManager.renderBombs();
    	
    }
    
    /**
     * Vyřešení inputu myši
     */
    private void handleInput(){
    	
    		directionDown = false; directionUp = false; directionRight = false; directionLeft = false;

    		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
    			directionDown = true;
    		}
    		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
    			directionUp = true;
    		}
    		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
    			directionRight = true;
    		}
    		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
    			directionLeft = true;
    		}   
    		
    		while(Keyboard.next()){
    			if(Keyboard.getEventKey() == Keyboard.KEY_SPACE){
    				
        			if(!Keyboard.getEventKeyState()){
            			// přidáme bombu
            			Bomb bomb = new Bomb();
            			bomb.setLocation(player.getX(), player.getY());
            			bombManager.addBomb(bomb);
        			}

    			}
    			
    			if(Keyboard.getEventKey() == Keyboard.KEY_RETURN){
    				if(!Keyboard.getEventKeyState() && winner){
    					resetAll();
    				}
    			}
    		}
    		moveObjects();
    	
    }
    /**
     * Reset hry
     */
    private void resetAll() {
    	
    	winner = false;
    	initShapes();
		
	}

	private void moveObjects() {

    	colisionManager.resetColissions();
    	// move player
    	if(directionDown){
    		player.tryMove(Player.DOWN);
    	}
    	if(directionUp){
    		player.tryMove(Player.UP);
    	}
    	if(directionRight){
    		player.tryMove(Player.RIGHT);
    	}
    	if(directionLeft){
    		player.tryMove(Player.LEFT);
    	}
		
    	for(Enemy enemy : colisionManager.getEnemies()){
    		enemy.tryMove();
    	}
    	// colission
    	colisionManager.checkColisions();
    	colisionManager.checkBombColisions();
    	
    	if(colisionManager.getEnemies().isEmpty()){
    		winner = true;
    	}
    	
    	for(AbstractMoveAbleShape object : colisionManager.getObjects()){
    		if(!object.hasColision()){
    			object.move();
    		}else{
    			object.resetMove();
    			object.handleColision();
    		}
    	}
    	
    	
	}
    
    
    /**
     * Main loop
     * @param argv
     */
    public static void main(String[] argv) {
    	
        Game displayExample = new Game();
        displayExample.start();
        
    }

}
