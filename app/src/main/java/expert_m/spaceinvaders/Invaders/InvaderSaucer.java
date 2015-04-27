package expert_m.spaceinvaders.Invaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;

import java.util.Random;

import expert_m.spaceinvaders.Invader;
import expert_m.spaceinvaders.PackageVariables;
import expert_m.spaceinvaders.R;

public class InvaderSaucer extends Invader {

    public InvaderSaucer(PackageVariables packageVariables, float x, float y) {
        super(packageVariables, x - (int)(60*packageVariables.getScale()), y);

        // Скорость
        speedx = 4*pv.getScale();
        //speedy = pv.getScale();

        moveInterval = 18L;
        //upInterval = 1500L;
        boomInterval = 1000L;

        // Размер захватчика
        width = (int)(60*pv.getScale()); height = (int)(28*pv.getScale());

        // Изображения
        Bitmap bitmap = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.invader_saucer);
        image = Bitmap.createScaledBitmap(bitmap, width, height, true);

        //Bitmap bitmap2 = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.invader_saucer);
        //imageInvaderUp = Bitmap.createScaledBitmap(bitmap2, width, height, true);

        // Кол-во очков
        score = ((new Random()).nextInt(4) * 50) + 100;

        r = 18; g = 65; b = 206;
    }

    @Override
    public void move() {
        if (pv.getTime() < timeMove+moveInterval || boom ) return;
        x += speedx;
        timeMove = pv.getTime();
        if (pv.getWidthPixels() <= x-width) exist = false;
    }

    @Override
    protected void drawBoom() {
        if (pv.getTime() >= timeBoom+boomInterval) {
            exist = false;
        } else {
            float size = 26;
            pv.writeText(String.valueOf(score), (x+width/2-pv.textWidth(String.valueOf(score), size)/2), (y+height/2+(size*pv.getScale())/2), size);
        }
    }
}
