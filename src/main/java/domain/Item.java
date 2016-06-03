package domain;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public abstract class Item {

  private final Rectangle rec;
  private final int width, height;

  public Item(float x, float y, int width, int height) {
    this.width = width;
    this.height = height;
    rec = new Rectangle(x, y, width, height);
  }

  public Rectangle getRec() {
    return rec;
  }

  public void setLocation(float x, float y) {
    rec.setLocation(x, y);
  }

  public float getX() {
    return rec.getX();
  }

  public float getY() {
    return rec.getY();
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void display(Graphics g) {
    g.setColor(Color.white);
    g.fill(rec);
  }

}
