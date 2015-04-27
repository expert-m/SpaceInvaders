package expert_m.spaceinvaders;

public class TouchHelper {
    float x, y;
    String action = "";

    public void setX(float x) {
        this.x = x;
    }
    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }
    public float getY() {
        return y;
    }

    public void setAction(String str) {
        action = str;
    }
    public String getAction() {
        return action;
    }
}
