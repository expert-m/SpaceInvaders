package expert_m.spaceinvaders.Invaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import expert_m.spaceinvaders.Invader;
import expert_m.spaceinvaders.PackageVariables;
import expert_m.spaceinvaders.R;

public class Boss3 extends Invader {

    public Boss3(PackageVariables packageVariables, float x, float y) {
        super(packageVariables, x, y);

        // Скорость
        speedx = 2*pv.getScale();

        shotInterval = 250L;
        moveInterval = 20L;

        numLives = 200;
        randMove = 3;

        // Размер захватчика
        width = (int)(80*pv.getScale()); height = (int)(62*pv.getScale());

        // Изображения
        Bitmap bitmap = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.boss3);
        image = Bitmap.createScaledBitmap(bitmap, width, height, true);

        // Кол-во очков
        score = 5000;

        r = 237; g = 29; b = 36;
    }

    @Override
    public void boom() {
        pv.setStatusRepairBarrier(true);
        pv.setTextWave("+ repair barrier");
        exist = false;
    }

    @Override
    public void shot() {
        pv.getBeamProcessing().create(x+width/2-width/9, y+height, false, r, g, b);
        pv.getBeamProcessing().create(x+width/2+width/9, y+height, false, r, g, b);

        pv.getBeamProcessing().create(x+width/2-width/3, y+height, false, r, g, b);
        pv.getBeamProcessing().create(x+width/2+width/3, y+height, false, r, g, b);
    }
}
