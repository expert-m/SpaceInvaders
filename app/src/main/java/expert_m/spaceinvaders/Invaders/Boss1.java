package expert_m.spaceinvaders.Invaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import expert_m.spaceinvaders.Invader;
import expert_m.spaceinvaders.PackageVariables;
import expert_m.spaceinvaders.R;

public class Boss1 extends Invader {

    public Boss1(PackageVariables packageVariables, float x, float y) {
        super(packageVariables, x, y);

        // Скорость
        speedx = 2*pv.getScale();

        shotInterval = 400L;
        moveInterval = 20L;

        numLives = 40;
        randMove = 2;

        // Размер захватчика
        width = (int)(80*pv.getScale()); height = (int)(64*pv.getScale());

        // Изображения
        Bitmap bitmap = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.boss1);
        image = Bitmap.createScaledBitmap(bitmap, width, height, true);

        // Кол-во очков
        score = 1000;

        r = 130; g = 201; b = 156;
    }

    @Override
    public void boom() {
        pv.incNumLives();
        pv.incMaxNumLives();
        pv.setTextWave("+ 1 laser gun");
        exist = false;
    }

    @Override
    public void shot() {
        pv.getBeamProcessing().create(x+width/2-width/5, y+height, false, r, g, b);
        pv.getBeamProcessing().create(x+width/2+width/5, y+height, false, r, g, b);
    }
}
