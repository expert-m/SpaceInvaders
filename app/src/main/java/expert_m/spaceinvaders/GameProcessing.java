package expert_m.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

public class GameProcessing extends View {

    private int widthPixels, heightPixels; // Размеры экрана
    private long lastFpsCalcUptime;
    private long frameCounter;
    private long fps;
    private static final long FPS_CALC_INTERVAL = 1000L;
    private SurfaceHolder surfaceHolder;
    private double scale;

    private Paint paint;
    private Rect rect;
    private Canvas canvas;

    private BeamProcessing beamProcessing;
    private InvaderProcessing invaderProcessing;
    private PackageVariables packageVariables;
    private SpaceShip spaceShip;
    private BarrierProcessing barrierProcessing;

    private String textDraw;

    private boolean running;

    public GameProcessing(Context context, SurfaceHolder surfaceHolder, TouchHelper touchHelper, String typeControl) {
        super(context);

        this.surfaceHolder = surfaceHolder;

        paint = new Paint();
        rect = new Rect();

        displaySize(); // Вычисляем размеры экрана
        packageVariables = new PackageVariables();
        packageVariables.setScale((float)scale);
        packageVariables.setContext(getContext());
        packageVariables.setWidthPixels(widthPixels);
        packageVariables.setHeightPixels(heightPixels);
        packageVariables.setPaint(paint);
        packageVariables.setRect(rect);
        packageVariables.setRunning(true);

        beamProcessing = new BeamProcessing(packageVariables);
        packageVariables.setBeamProcessing(beamProcessing);

        invaderProcessing = new InvaderProcessing(packageVariables);
        packageVariables.setInvaderProcessing(invaderProcessing);
        invaderProcessing.createWave();

        barrierProcessing = new BarrierProcessing(packageVariables);
        packageVariables.setBarrierProcessing(barrierProcessing);

        spaceShip = new SpaceShip(packageVariables);
        packageVariables.setSpaceShip(spaceShip);

        packageVariables.setTouch(touchHelper);
        packageVariables.setTypeControl(typeControl);
        packageVariables.setNumberWaves(1);
    }

    private long timeFps, timeWave = 0;
    private float tempSpeedUp = 0;
    public void run() {

        running = packageVariables.getRunning();

        if (!running) {
            return;
        }

        //if (timeFps < SystemClock.uptimeMillis()) {
        //   timeFps = SystemClock.uptimeMillis() + 2000L;
        //   return;
        //}

        canvas = null;

        try {
            canvas = surfaceHolder.lockCanvas(null);
            long time = SystemClock.uptimeMillis() % 10000L;
            if (canvas == null)
                return;
            packageVariables.setCanvas(canvas);

            if (timeWave != 0) {
                if (timeWave < SystemClock.uptimeMillis()) {
                    timeWave = 0;
                }

                packageVariables.newWave();

                if (packageVariables.getInvaderProcessing().getSize() == 0) {
                    packageVariables.getBeamProcessing().clear();
                    packageVariables.getBarrierProcessing().repair(2);

                    if (tempSpeedUp != 0) {
                        packageVariables.setSpeedUp(tempSpeedUp);
                        tempSpeedUp = 0;
                    }

                    switch (packageVariables.getNumberWaves()) {
                        case 5:
                            packageVariables.setTextWave("BOSS");
                            tempSpeedUp = packageVariables.getSpeedUp();
                            packageVariables.setSpeedUp(1f);
                            packageVariables.getInvaderProcessing().create("Boss1");
                            packageVariables.getBarrierProcessing().repair(1);
                            break;

                        case 10:
                            packageVariables.setTextWave("BOSS");
                            tempSpeedUp = packageVariables.getSpeedUp();
                            packageVariables.setSpeedUp(1f);
                            packageVariables.getInvaderProcessing().create("Boss2");
                            packageVariables.getBarrierProcessing().repair(1);
                            break;

                        case 15:
                            packageVariables.setTextWave("BOSS");
                            tempSpeedUp = packageVariables.getSpeedUp();
                            packageVariables.setSpeedUp(1f);
                            packageVariables.getInvaderProcessing().create("Boss3");
                            packageVariables.getBarrierProcessing().repair(1);
                            break;

                        case 6:
                        case 11:
                        case 16:
                            packageVariables.getBarrierProcessing().repair(1);

                        default:
                            packageVariables.setTextWave("");
                            packageVariables.getInvaderProcessing().createWave();
                            break;
                    }
                }

                return;
            }

            measureFps(); // Подсчитываем FPS
            canvas.drawRGB(35, 31, 32); // Заливка фона
            packageVariables.setTime(SystemClock.uptimeMillis());
            invaderProcessing.draw();
            spaceShip.draw();
            beamProcessing.draw();
            barrierProcessing.draw();

            textDraw = "SCORE ";
            for (int i = (packageVariables.getScore() == 0) ? 1 : packageVariables.getScore(); i < 100000; i*=10)
                textDraw += "0";
            textDraw += packageVariables.getScore();

            textDraw += "  WAVE ";
            textDraw += (packageVariables.getNumberWaves() < 10) ?  "0" : "";
            textDraw += packageVariables.getNumberWaves();

            textDraw += "  FPS" + fps;

            packageVariables.writeText(textDraw, 10*packageVariables.getScale(), 22*packageVariables.getScale());

            if (packageVariables.getInvaderProcessing().getSize() == 0) {
                timeWave = SystemClock.uptimeMillis() + 1500L;
                packageVariables.incNumberWaves();
                packageVariables.newWave();
            }
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void measureFps() {
        frameCounter++;
        long now = SystemClock.uptimeMillis();
        long delta = now - lastFpsCalcUptime;
        if (delta > FPS_CALC_INTERVAL) {
            fps = frameCounter * FPS_CALC_INTERVAL / delta;

            frameCounter = 0;
            lastFpsCalcUptime = now;
        }
    }

    private void displaySize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        scale = dm.densityDpi / 160.0;
    }

    public boolean getRunning() {
        return running;
    }
}
