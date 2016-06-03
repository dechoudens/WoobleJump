/*
 * Antoine de Choudens
 */
package ch.hesge.wooblejump;

import domain.*;
import outils.ImageMenu;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Main extends BasicGame{
    private final int PLAYER_WIDTH = 10;
    private final int PLAYER_HEIGHT = 30;
    private final int PLATEFORM_WIDTH = 60;
    private final int PLATEFORM_HEIGHT = 15;
    private final int PLATEFORM_MAX = 40;
    private final float Y_VELOCITY_VALUE = 8;
    private final float X_VELOCITY_VALUE = 0.1f;
    
    private Input input;
    private ImageMenu replay;
    
    private static int width, height, screenWidth, screenHeight;
    private int index, meter;
    private float gravity;
    
    private Random rand;
    
    private Player player;
    private ArrayList plateformes;
    
    public Main(){
        super("WoobleJump");
    }
    
    @Override
    public void init(GameContainer container) throws SlickException{
        width = container.getWidth();
        height = container.getHeight();
        screenWidth = container.getScreenWidth();
        screenHeight = container.getScreenHeight();
        index = -1;
        gravity = 0.2f;
        meter = 0;

        player = new Player(width/2, height-PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
        player.setY_velocity(-(float)Y_VELOCITY_VALUE);
        
        rand = new Random();
        
        replay = new ImageMenu("replay.png", width/2, height/2);
        
        plateformes = new ArrayList();
        for (int i = 0; i < PLATEFORM_MAX; i++) {
            plateformes.add(new Plateforme(rand.nextInt(width)-PLATEFORM_WIDTH/2, i*height/PLATEFORM_MAX, PLATEFORM_WIDTH, PLATEFORM_HEIGHT));
            float xi = ((Plateforme)plateformes.get(i)).getX()+(float)rand.nextInt(((Plateforme)plateformes.get(i)).getWidth()-15);
            float yi = ((Plateforme)plateformes.get(i)).getY()-15;
            if (rand.nextInt(10) == 0) {
                ((Plateforme)plateformes.get(i)).setPower(new Power(xi, yi, 20, 15));
            }
        }
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException{
        if(!player.isLose()){
            plateformes.stream().forEach((p) -> {
                ((Plateforme)p).display(g);
            });
            
            player.display(g);
        }
        else{
            replay.display(g);
        }
        
        g.setColor(Color.white);
        g.drawString("Score: "+meter/10+" [m]", 0, 0);
    }
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException{  
        input = container.getInput();
        if(!player.isLose()){  
            if(index != -1){
                player.setY_velocity(-Y_VELOCITY_VALUE);
                index = -1;
            }
            if(input.isKeyPressed(Input.KEY_LEFT)){
                player.setX_velocity(-1);
            }
            if(input.isKeyDown(Input.KEY_LEFT)){
                player.updateX_velocity(-X_VELOCITY_VALUE);
            }
            if(input.isKeyPressed(Input.KEY_RIGHT)){
                player.setX_velocity(1);
            }
            if(input.isKeyDown(Input.KEY_RIGHT)){
                player.updateX_velocity(X_VELOCITY_VALUE);
            }
            if(input.isKeyDown(Input.KEY_ESCAPE)){
                container.exit();
            }

            player.update();
            
            for (Object p : plateformes) {
                Power power = ((Plateforme)p).getPower();
                if(power != null && player.falling()){
                    if (player.getX() > power.getX() && player.getX() < power.getRec().getMaxX() &&
                        player.getRec().getMaxX() > power.getX() && player.getRec().getMaxX() < power.getRec().getMaxX() &&
                        player.getRec().getMaxY() > power.getY() && player.getRec().getMaxY() < power.getRec().getMaxY() ){
                        player.setY_velocity(-power.getBoost());
                    }
                }
            } 
            
            if (player.getY_velocity() < 0) {
                meter -= (int)player.getY_velocity();               
            }


            if(index == -1){
                index = player.onPlateform(plateformes, player.falling(), PLAYER_WIDTH);
            }

            if(index != -1){
                player.setX_velocity(player.getX_velocity()/2);
            }
            else{
                player.updateY_velocity(gravity);
            }

            player.collision(width, height, PLAYER_WIDTH);

            if(player.getY_velocity() < 0 && player.getY() == height/2){
                updatePlateforme(-player.getY_velocity());
            } 
            
            if (player.onGround(height)) {
                player.setLose(true);
            }
        }
        else{
            if (input.isKeyPressed(Input.KEY_ENTER)) {
                container.reinit();
            }
            else if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (input.getMouseX() > replay.getWidth() && input.getMouseX() < replay.getX()+replay.getWidth() ||
                        input.getMouseY() > replay.getHeight() && input.getMouseY() < replay.getY()+replay.getHeight()) {
                    container.reinit();    
                }
            }
        }
    }
    
    public void updatePlateforme(float y){
        for (Object p : plateformes) {
            Plateforme plat = ((Plateforme)p);
            plat.setLocation(plat.getX(), plat.getY()+y);
            plat.updatePower();
            if (plat.getY() > height) {
                plat.setLocation(rand.nextInt(width)-PLATEFORM_WIDTH/2, 0);
                if (rand.nextInt(10)%5 == 0) {
                    plat.setPower(new Power(plat.getX()+(float)rand.nextInt((int)plat.getWidth()-15), plat.getY()-15, 20, 15));
                }
                else{
                    plat.setPower(null);
                }
            }
        }  
    }
    
    public static void main(String[] args) throws SlickException{
        AppGameContainer app = new AppGameContainer(new Main());
        app.setDisplayMode(1366, 768, true);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);
        app.setShowFPS(false);
        app.setVSync(true);
        app.setFullscreen(true);
        app.start();
    }
}
