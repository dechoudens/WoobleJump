
package domain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Plateforme extends Item{
    
    private Power power;

    public Plateforme(float x, float y, int width, int height) {
        super(x, y, width, height);
        power = null;
    }
    
    public Power getPower() {
        return power;
    }
    
    public void setPower(Power power){
        this.power = power;
    }
    
    public void updatePower(){
        if (power != null) {
            power.setLocation(power.getX(), getY()-power.getHeight());
        }
    }
    
    @Override
    public void display(Graphics g){
        g.setColor(Color.white);
        g.fill(getRec());
        if (power != null) {
            g.setColor(Color.red);
            g.fill(power.getRec());
        }
    }
}
