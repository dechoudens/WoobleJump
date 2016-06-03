
package domain;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Player extends Item{
    private float y_velocity;
    private float x_velocity;
    private boolean lose;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        y_velocity = 0;
        x_velocity = 0;
        lose = false;
    }
    
    @Override
    public void display(Graphics g){
        g.setColor(Color.yellow);
        g.fill(getRec());
    }
    
    public void setLose(boolean lose){ this.lose = lose; }
    public boolean isLose() { return lose; }

    public void setY_velocity(float y_velocity) { this.y_velocity = y_velocity; }
    public void setX_velocity(float x_velocity) { this.x_velocity = x_velocity; }
    public void updateX_velocity(float inc){
        x_velocity += inc;
    }
    
    public void updateY_velocity(float inc){
        y_velocity += inc;
    }
    
    public float getY_velocity() { return y_velocity; }
    public float getX_velocity() { return x_velocity; }

    public boolean onGround(int height){
        return getRec().getMaxY() >= height;
    }
    
    public boolean falling(){
        return y_velocity > 0;
    }
    
    public void update(){
        setLocation(getX()+x_velocity, getY()+y_velocity);
    }
    
    public int onPlateform(ArrayList plateformes, boolean falling, int size){
        if(falling){
            for (int i = 0; i < plateformes.size(); i++) {
                Plateforme plat = (Plateforme)plateformes.get(i);
                if ((getRec().getMaxX() < plat.getRec().getMaxX()+size-1 && 
                        getRec().getX() >= plat.getRec().getX()-size+1) && 
                        (getRec().getMaxY() < plat.getRec().getMaxY() && getRec().getMaxY() >= plat.getRec().getY())) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void collision(int width, int height, int size){
        if(getX() < 0){
            setLocation(0, getY());
        }
        else if(getRec().getMaxX() > width){
            setLocation(width-size, getY());
        }
        
        if(getY() < height/2){
            setLocation(getX(), height/2);
        }
    }
}
