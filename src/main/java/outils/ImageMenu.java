/*
 * Antoine de Choudens
 */
package outils;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ImageMenu {
    private final Image img;
    private float x, y;
    
    public ImageMenu(String name) throws SlickException{
        img = new Image(name);
        this.x = 0;
        this.y = 0;
    }
    
    public ImageMenu(String name, float x, float y) throws SlickException{
        img = new Image(name);
        this.x = x-img.getWidth()/2;
        this.y = y-img.getHeight()/2;
    }
    
    public float getX(){ return x; }
    public float getY(){ return y; }
    public void setLocation(float x, float y){ this.x = x; this.y = y; }
    public Image getImg(){ return img; }
    public int getWidth(){
        return img.getWidth();
    }
    public int getHeight(){
        return img.getHeight();
    }
    
    public void display(Graphics g){
        g.drawImage(img, x, y);
    }
}
