
package domain;

public class Power extends Item{
    private final float boost;

    public Power(float x, float y, int width, int height) {
        super(x, y, width, height);
        boost = 15;
    }

    public float getBoost() {
        return boost;
    }
}
