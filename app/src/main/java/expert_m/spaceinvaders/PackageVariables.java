package expert_m.spaceinvaders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class PackageVariables {
    private InvaderProcessing invaderProcessing;
    private BeamProcessing beamProcessing;
    private SpaceShip spaceShip;
    private BarrierProcessing barrierProcessing;
    private float scale, speedUp;
    private int widthPixels, heightPixels, numberWaves, score;
    private Context context;
    private Paint paint;
    private Rect rect;
    private Canvas canvas;
    private boolean running;
    private long time;
    private TouchHelper touchHelper;
    private String typeControl;

    public PackageVariables() {
        score = 0;
        speedUp = 1;

        numLives = 1;
        maxNumLives = 1;
    }

    public void setInvaderProcessing(InvaderProcessing invaderProcessing) {
        this.invaderProcessing = invaderProcessing;
    }
    public InvaderProcessing getInvaderProcessing () {
        return invaderProcessing;
    }

    public void setBeamProcessing(BeamProcessing beamProcessing) {
        this.beamProcessing = beamProcessing;
    }
    public BeamProcessing getBeamProcessing () {
        return beamProcessing;
    }

    public void setBarrierProcessing(BarrierProcessing barrierProcessing) {
        this.barrierProcessing = barrierProcessing;
    }
    public BarrierProcessing getBarrierProcessing () {
        return barrierProcessing;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }
    public SpaceShip getSpaceShip () {
        return spaceShip;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
    public float getScale() {
        return scale;
    }

    public void setWidthPixels(int widthPixels) {
        this.widthPixels = widthPixels;
    }
    public int getWidthPixels() {
        return widthPixels;
    }

    public void setHeightPixels(int heightPixels) {
        this.heightPixels = heightPixels;
    }
    public int getHeightPixels() {
        return heightPixels;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public Context getContext() {
        return context;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    public Paint getPaint() {
        return paint;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
    public Rect getRect() {
        return rect;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    public Canvas getCanvas() {
        return canvas;
    }

    public void gameOver() {
        running = false;

        Intent intent = new Intent(getContext(), GameOverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("score", getScore());

        intent.putExtra("typeControl", getTypeControl());
        getContext().startActivity(intent);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    public boolean getRunning() {
        return running;
    }

    public void addScore(int n) {
        score += n;
    }
    public int getScore() {
        return score;
    }

    public void setSpeedUp(float speedUp) {
        this.speedUp = speedUp;
    }
    public float getSpeedUp() {
        return speedUp;
    }
    public void addSpeedUp(float n) {
        speedUp += n;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public long getTime() {
        return time;
    }

    public void setTouch(TouchHelper touchHelper) {
        this.touchHelper = touchHelper;
    }
    public TouchHelper getTouch() {
        return touchHelper;
    }

    public void setTypeControl(String typeControl) {
        this.typeControl = typeControl;
    }
    public String getTypeControl() {
        return typeControl;
    }

    public void setNumberWaves(int numberWaves) {
        this.numberWaves = numberWaves;
    }
    public void incNumberWaves() {
        numberWaves++;
    }
    public int getNumberWaves() {
        return numberWaves;
    }

    private Typeface tf;
    public void writeText(String text, float x, float y) {
        writeText(text, x, y, 22);
    }
    public void writeText(String text, float x, float y, float size) {
        if (tf == null) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Moder DOS 437.ttf");
        }

        getPaint().setTextSize(size*getScale());
        getPaint().setColor(Color.WHITE);
        getPaint().setTypeface(tf);
        getCanvas().drawText(text, x, y, getPaint());
    }

    private String textWave = "";
    public void setTextWave(String textWave) {
        this.textWave = textWave;
    }
    public void newWave() {
        getCanvas().drawRGB(35, 31, 32);
        String textDraw = "WAVE ";
        textDraw += (getNumberWaves() < 10) ?  "0" : "";
        textDraw += getNumberWaves();

        float x1 = getWidthPixels()/2 - textWidth(textDraw, 50)/2;
        float y1 = getHeightPixels()/2+(50*getScale())/2;

        if (!textWave.isEmpty())
            y1 -= 30*getScale() - 10*getScale();

        writeText(textDraw, x1, y1, 50);

        if (!textWave.isEmpty()) {
            float x2 = getWidthPixels()/2 - textWidth(textWave, 30)/2;
            float y2 = getHeightPixels()/2+(50*getScale())/2+(30*getScale())/2+10*getScale();
            writeText(textWave, x2, y2, 30);
        }
    }

    public float textWidth(String str, float size) {
        float[] widths;
        widths = new float[str.length()];
        getPaint().setTextSize(size*getScale());
        getPaint().setTypeface(tf);
        getPaint().getTextWidths(str, widths);

        float widthText = 0;

        for (float w : widths) {
            widthText += w;
        }

        return widthText;
    }

    private int numLives, maxNumLives;
    public void incNumLives() {
        numLives++;
    }
    public void decNumLives() {
        numLives--;
    }
    public void incMaxNumLives() {
        maxNumLives++;
    }
    public void decMaxNumLives() {
        maxNumLives--;
    }

    public int getNumLives() {
        return numLives;
    }
    public int getMaxNumLives() {
        return maxNumLives;
    }

    private boolean repairBarrier;
    public void setStatusRepairBarrier(boolean status) {
        repairBarrier = status;
    }
    public boolean getStatusRepairBarrier() {
        return repairBarrier;
    }
}
