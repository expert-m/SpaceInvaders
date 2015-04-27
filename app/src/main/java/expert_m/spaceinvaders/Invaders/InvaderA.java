package expert_m.spaceinvaders.Invaders;

import expert_m.spaceinvaders.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import expert_m.spaceinvaders.Invader;
import expert_m.spaceinvaders.PackageVariables;

public class InvaderA extends Invader {
    public InvaderA(PackageVariables packageVariables, float x, float y) {
        super(packageVariables, x, y);

        // Скорость
        speedx = pv.getScale()/2;
        speedy = 10*pv.getScale();

        moveInterval = 20L;
        upInterval = 1500L;
        boomInterval = 100L;
        shotInterval = upInterval * 2;
        iDetachment = true;

        randMove = 2;

        // Размер захватчика
        width = (int)(40*pv.getScale()); height = (int)(31*pv.getScale());

        // Изображения
        Bitmap bitmap = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.invader);
        imageInvader = Bitmap.createScaledBitmap(bitmap, width, height, true);
        image = imageInvader;

        Bitmap bitmap2 = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.invader_up);
        imageInvaderUp = Bitmap.createScaledBitmap(bitmap2, width, height, true);

        Bitmap bitmap3 = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.boom);
        imageBoom = Bitmap.createScaledBitmap(bitmap3, width, width, true);

        // Кол-во очков
        score = 20;

        r = 66; g = 150; b = 140;
    }
}
