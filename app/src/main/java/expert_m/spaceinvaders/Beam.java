package expert_m.spaceinvaders;

public class Beam {
    private int width, height, r, g, b;
    protected float x, y, speed;
    private long timeMove;
    private long moveInterval;
    private boolean up, deletionStatus;
    private PackageVariables pv;

    public Beam(PackageVariables packageVariables, float xc, float yc, boolean up, int r, int g, int b) {
        pv = packageVariables;
        deletionStatus = false;

        width = (int)(4*pv.getScale()); height = (int)(15*pv.getScale());
        this.up = up;

        x = xc - width / 2;
        if (up) {
            speed = -15*pv.getScale();
            y = yc-height;
            moveInterval = 15L;
        } else {
            speed = 4*pv.getScale();
            y = yc+height;
            moveInterval = 15L;
        }

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void draw() {
        if (timeMove+moveInterval < pv.getTime()) {
            timeMove = pv.getTime();
            y += speed;
        }

        pv.getPaint().setARGB(255, r, g, b);
        pv.getCanvas().drawRect(x, y, width+x, y-height, pv.getPaint());

        processingFallingIntoTheShip();
        if (!deletionStatus) processingFallingIntoTheBarrier();
        if (!deletionStatus) processingFallingIntoTheBeam();

        if (y > pv.getHeightPixels() || y < 0) {
            deletionStatus = true;
        }
    }

    public void processingFallingIntoTheBeam() {
        if (!up) return;

        float tx, ty, tw, th;

        for (int i = 0; i < pv.getBeamProcessing().getSize(); i++) {
            if (pv.getBeamProcessing().getBeam(i).getDeletionStatus() || pv.getBeamProcessing().getBeam(i).getUp()) continue;

            tx = pv.getBeamProcessing().getBeam(i).getX();
            ty = pv.getBeamProcessing().getBeam(i).getY();
            tw = pv.getBeamProcessing().getBeam(i).getWidth();
            th = pv.getBeamProcessing().getBeam(i).getHeight();

            if (((tx <= x && x <= tx + tw) || (tx <= x+width && x+width <= tx + tw)) && ((ty <= y && y <= ty + th) || (ty <= y - speed && y - speed <= ty + th))) {
                pv.getBeamProcessing().getBeam(i).setDeletionStatus(true);
                deletionStatus = true;
                return;
            }
        }
    }

    public void processingFallingIntoTheShip() {
        if (up) {
            float tx, ty, tw, th;
            for (int i = 0; i < pv.getInvaderProcessing().getSize(); i++) {
                if (!pv.getInvaderProcessing().getInvader(i).getExist() || pv.getInvaderProcessing().getInvader(i).getStatusBoom()) continue;

                tx = pv.getInvaderProcessing().getInvader(i).getXInvader();
                ty = pv.getInvaderProcessing().getInvader(i).getYInvader();
                tw = pv.getInvaderProcessing().getInvader(i).getWidthInvader();
                th = pv.getInvaderProcessing().getInvader(i).getHeightInvader();

                // Проверяем первую точку луча
                if (((tx <= x && x <= tx + tw) || (tx <= x+width && x+width <= tx + tw)) && ((ty <= y && y <= ty + th) || (ty <= y - speed && y - speed <= ty + th))) {
                    pv.getInvaderProcessing().hitInvader(i);
                    deletionStatus = true;
                    return;
                }
            }
        } else {
            float tx = pv.getSpaceShip().getXShip();
            float ty = pv.getSpaceShip().getYShip();
            float tw = pv.getSpaceShip().getWidthShip();
            float th = pv.getSpaceShip().getHeightShip();

            if ((tx <= x && x <= tx+tw) && ((ty <= y && y <= ty+th) || (ty <= y-speed && y-speed <= ty+th))) {
                pv.getSpaceShip().hit();
                deletionStatus = true;
                return;
            }
        }
    }

    public void processingFallingIntoTheBarrier() {
        if (pv.getBarrierProcessing().getSize() == 0)
            return;
        if (y < pv.getBarrierProcessing().getBarrier(0).getY())
            return;
        if (up)
            return;

        float tx, ty, tw, th;
        for (int i = 0; i < pv.getBarrierProcessing().getSize(); i++) {
            if (!pv.getBarrierProcessing().getBarrier(i).getExist()) continue;

            tx = pv.getBarrierProcessing().getBarrier(i).getX();
            ty = pv.getBarrierProcessing().getBarrier(i).getY();
            tw = pv.getBarrierProcessing().getBarrier(i).getWidth();
            th = pv.getBarrierProcessing().getBarrier(i).getHeight();

            if ((tx <= x && x <= tx+tw) && ((ty <= y && y <= ty+th) || (ty <= y-speed && y-speed <= ty+th))) {
                pv.getBarrierProcessing().delBarrier(i);
                deletionStatus = true;
                return;
            }
        }
    }

    public void setDeletionStatus(boolean status) {
        deletionStatus = status;
    }
    public boolean getDeletionStatus() {
        return deletionStatus;
    }

    public boolean getUp() {
        return up;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
}
