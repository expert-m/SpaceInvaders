package expert_m.spaceinvaders;

public class Barrier {
    private int width, height;
    protected float x, y;
    private PackageVariables pv;
    private boolean exist;

    public Barrier(PackageVariables packageVariables, float x, float y) {
        pv = packageVariables;
        this.x = x;
        this.y = y;
        width = (int)(7*pv.getScale());
        height = (int)(7*pv.getScale());
        exist = true;
    }

    public void draw() {
        if (!exist) return;
        
        pv.getPaint().setARGB(255, 1, 160, 219);
        pv.getCanvas().drawRect(x, y, width + x, y - height, pv.getPaint());
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
    public boolean getExist() {
        return exist;
    }
}
