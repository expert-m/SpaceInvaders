package expert_m.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.view.OrientationEventListener;
import android.view.View;

public class SpaceShip extends View {
    int width, height, angle;
    Bitmap image, image2;
    float speed, x, y;
    PackageVariables pv;

    long timeMove, timeShot, timeRegeneration;
    private static final long MOVE_INTERVAL = 10L;
    private static final long SHOT_INTERVAL = 300L;
    private static final long REGENERATION_INTERVAL = 10000L;
    private boolean regeneration = false;

    OrientationEventListener myOrientationEventListener;

    public SpaceShip(PackageVariables packageVariables) {
        super(packageVariables.getContext());
        pv = packageVariables;
        width = (int) (40 * pv.getScale());
        height = (int) (18 * pv.getScale());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
        image = Bitmap.createScaledBitmap(bitmap, width, height, true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship2);
        image2 = Bitmap.createScaledBitmap(bitmap, width, height, true);

        x = pv.getWidthPixels() / 2 - width / 2;
        y = (int) (pv.getHeightPixels() - height - 10 * pv.getScale());

        speed = 2 * pv.getScale();
        angle = 0;
    }

    public void draw() {
        if (pv.getTypeControl().equals("rotation")) {
            orientationChanged(); // это для управления нажатием
        } else if (pv.getTypeControl().equals("touch")) {
            touchChanged(); // это поворотом экрана
        }

        if (myOrientationEventListener == null && pv.getTypeControl().equals("rotation")) {
            initOrientationListener();
        }

        if (timeShot+SHOT_INTERVAL < pv.getTime()) {
            shot();
        }

        if (x + width > pv.getWidthPixels() - 10*pv.getScale()) {
            x = pv.getWidthPixels() - width - (int)(10*pv.getScale());
        } else if (x < 10*pv.getScale()) {
            x = (int)(10*pv.getScale());
        }

        if (regeneration && (pv.getNumLives() < pv.getMaxNumLives())) {
            if (timeRegeneration == 0) timeRegeneration = pv.getTime();

            if (timeRegeneration+REGENERATION_INTERVAL < pv.getTime()) {
                pv.incNumLives();
                timeRegeneration = 0;
            }
        }

        if (pv.getNumLives() == 1) {
            pv.getCanvas().drawBitmap(image, x, y, null);
        } else if (pv.getNumLives() == 2) {
            pv.getCanvas().drawBitmap(image2, x, y, null);
        }
    }

    private void touchChanged() {
        if (pv.getTouch().getAction() != "ACTION_DOWN" && pv.getTouch().getAction() != "ACTION_MOVE")
            return;

        if (pv.getTouch().getX() < pv.getWidthPixels()/2) {
            x -= (int) speed;
        } else {
            x += (int) speed;
        }
    }

    private void orientationChanged() {
        if (timeMove+MOVE_INTERVAL > SystemClock.uptimeMillis()) {
            return;
        }

        timeMove = SystemClock.uptimeMillis();

        int xc = 0;
        if (angle >= 300) {
            xc = angle-300;
        } else if (angle <= 60){
            xc = angle+60;
        }

        if (xc >= 60) {
            if (xc > 75) xc = 75;
            x += (int) (speed * (xc-60) / 15);
        } else {
            if (xc < 45) xc = 45;
            x -= (int) (speed * (60-xc) / 15);
        }
    }

    private void shot() {
        if (pv.getNumLives() == 1 && !checkBarrier(x+width/2)) {
            pv.getBeamProcessing().create(x+width/2, y+height, true, 230, 231, 230);
            timeShot = SystemClock.uptimeMillis();
        } if (pv.getNumLives() == 2) {
            if (!checkBarrier(x+width/2-width/9)) {
                pv.getBeamProcessing().create(x+width/2-width/9, y+height, true, 230, 231, 230);
                timeShot = SystemClock.uptimeMillis();
            }

            if (!checkBarrier(x+width/2+width/9)) {
                pv.getBeamProcessing().create(x+width/2+width/9, y+height, true, 230, 231, 230);
                timeShot = SystemClock.uptimeMillis();
            }
        }
    }

    private boolean checkBarrier(float mx) {
        if (pv.getBarrierProcessing().getSize() != 0) {
            float tx, tw;

            for (int i = 0; i < pv.getBarrierProcessing().getSize(); i++) {
                if (!pv.getBarrierProcessing().getBarrier(i).getExist()) continue;

                tx = pv.getBarrierProcessing().getBarrier(i).getX();
                tw = pv.getBarrierProcessing().getBarrier(i).getWidth();

                if (tx <= mx && mx <= tx+tw) return true;
            }
        }

        return false;
    }

    public int getWidthShip() {
        return width;
    }

    public int getHeightShip() {
        return height;
    }

    public float getXShip() {
        return x;
    }

    public float getYShip() {
        return y;
    }

    private void initOrientationListener() {
        myOrientationEventListener = new OrientationEventListener(pv.getContext(),
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int arg0) {
                angle = arg0;
            }
        };

        if (myOrientationEventListener.canDetectOrientation()) {
            myOrientationEventListener.enable();
        } else {
            myOrientationEventListener.disable();
        }
    }

    public void hit() {
        pv.decNumLives();

        if (pv.getNumLives() == 0) {
            pv.gameOver();
            return;
        }
    }

    public void setStatusRegeneration(boolean status) {
        regeneration = status;
    }
    public boolean getStatusRegeneration() {
        return regeneration;
    }
}





