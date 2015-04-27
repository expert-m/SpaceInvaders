package expert_m.spaceinvaders;

import android.graphics.Bitmap;
import java.util.Random;

public class Invader {
    protected int width, height;
    protected long timeMove, timeUp, timeBoom, timeShot; // Таймеры
    protected float x, y, numLives = 1;
    protected int course, score, r, g, b, randMove;
    protected static final int C_LEFT = 0;
    protected static final int C_RIGHT = 1;
    protected long moveInterval, upInterval, boomInterval, shotInterval;
    protected float speedx, speedy; // Скорость
    protected Bitmap image, imageInvader, imageInvaderUp, imageBoom; // Изображения
    protected PackageVariables pv;
    protected boolean exist, boom, iDetachment;

    public Invader(PackageVariables packageVariables, float x, float y) {
        pv = packageVariables;

        this.x = x; this.y = y;
        course = C_RIGHT;
        timeMove = pv.getTime();
        timeUp = pv.getTime();

        exist = true; boom = false;
    }

    public void draw() {
        if (boom) {
            drawBoom();
            return;
        }

        if (imageInvaderUp != null) {
            if (pv.getTime() >= timeUp + upInterval / pv.getSpeedUp()) {
                if (image == imageInvader) {
                    image = imageInvaderUp;
                } else {
                    image = imageInvader;
                }

                timeUp = pv.getTime();
            }
        }

        pv.getCanvas().drawBitmap(image, x, y, null);
    }

    public void move() {
        if (pv.getTime() < timeMove+moveInterval || boom)
            return;

        if(checkShot()) shot();

        if (course == C_RIGHT) {
            x += speedx * pv.getSpeedUp();
        } else if (course == C_LEFT) {
            x -= speedx * pv.getSpeedUp();
        }

        timeMove = pv.getTime();

        if (x + width > pv.getWidthPixels() - 10*pv.getScale()) {
            x = pv.getWidthPixels() - width - 10*pv.getScale();
            course = C_LEFT;
            controlDetachment();
        } else if (x < 10*pv.getScale()) {
            x = (int)(10*pv.getScale());
            course = C_RIGHT;
            controlDetachment();
        }

        if (y+height > pv.getHeightPixels()) {
            pv.gameOver();
        }
    }

    public void shot() {
        pv.getBeamProcessing().create(x+width/2, y+height, false, r, g, b);
    }

    public boolean checkShot() {
        if ((timeShot == 0 && image == imageInvaderUp) || pv.getTime() >= timeShot+shotInterval/pv.getSpeedUp()) {
            timeShot = pv.getTime();
        } else {
            return false;
        }

        if ((new Random()).nextInt(randMove) != 0) return false;

        float xi, yi, xb = x+width/2, wi;
        for (int i = 0; i < pv.getInvaderProcessing().getSize(); i++) {
            xi = pv.getInvaderProcessing().getInvader(i).getXInvader();
            yi = pv.getInvaderProcessing().getInvader(i).getYInvader();
            wi = pv.getInvaderProcessing().getInvader(i).getWidthInvader();

            if (yi > y && xi <= xb && xb <= xi+wi) return false;
        }

        return true;
    }

    public int getWidthInvader() {
        return width;
    }
    public int getHeightInvader() {
        return height;
    }

    public float getXInvader() {
        return x;
    }
    public float getYInvader() {
        return y;
    }
    public void addYInvader(float n) {
        this.y += n;
    }

    public void setCourse(int course) {
        this.course = course;
    }
    public boolean getExist() {
        return exist;
    }
    public boolean getStatusBoom() {
        return boom;
    }
    public int getScore() {
        return score;
    }

    public boolean getStatusDetachment() {
        return iDetachment;
    }

    public void controlDetachment() {
        if (!iDetachment) return;

        for (int i = 0; i < pv.getInvaderProcessing().getSize(); i++) {
            if (!pv.getInvaderProcessing().getInvader(i).getStatusDetachment()) continue;
            pv.getInvaderProcessing().getInvader(i).setCourse(course);
            pv.getInvaderProcessing().getInvader(i).addYInvader(speedy);
        }
    }

    public void hit() {
        if (--numLives <= 0) {
            boom();
        }
    }

    public void boom() {
        boom = true;
        timeBoom = pv.getTime();
    }

    protected void drawBoom() {
        if (imageBoom != null) {
            if (pv.getTime() >= timeBoom + boomInterval) {
                exist = false;
            } else {
                pv.getCanvas().drawBitmap(imageBoom, x, y - (width - height) / 2, null);
            }
        }
    }
}
