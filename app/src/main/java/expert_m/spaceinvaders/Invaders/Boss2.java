package expert_m.spaceinvaders.Invaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import expert_m.spaceinvaders.Invader;
import expert_m.spaceinvaders.PackageVariables;
import expert_m.spaceinvaders.R;

public class Boss2 extends Invader {

    public Boss2(PackageVariables packageVariables, float x, float y) {
        super(packageVariables, x, y);

        // Скорость
        speedx = 4*pv.getScale();

        shotInterval = 250L;
        moveInterval = 20L;

        numLives = 100;
        randMove = 2;

        // Размер захватчика
        width = (int)(80*pv.getScale()); height = (int)(80*pv.getScale());

        // Изображения
        Bitmap bitmap = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.boss2);
        image = Bitmap.createScaledBitmap(bitmap, width, height, true);

        // Кол-во очков
        score = 2000;

        r = 189; g = 150; b = 99;
    }

    @Override
    public void boom() {
        pv.getSpaceShip().setStatusRegeneration(true);
        pv.setTextWave("+ auto repair");
        exist = false;
    }

    @Override
    public void shot() {
        pv.getBeamProcessing().create(x+width/2-width/9, y+height, false, r, g, b);
        pv.getBeamProcessing().create(x+width/2+width/9, y+height, false, r, g, b);
    }
}
