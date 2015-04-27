package expert_m.spaceinvaders.Invaders;

import expert_m.spaceinvaders.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import expert_m.spaceinvaders.Invader;
import expert_m.spaceinvaders.PackageVariables;

public class InvaderC extends Invader {
    public InvaderC(PackageVariables packageVariables, float x, float y) {
        super(packageVariables, x, y);

        // Скорость
        speedx = pv.getScale()/2;
        speedy = 10*pv.getScale();

        moveInterval = 20L;
        upInterval = 1500L;
        boomInterval = 100L;
        shotInterval = upInterval * 2;
        iDetachment = true;

        randMove = 1;

        // Размер захватчика
        width = (int)(40*pv.getScale()); height = (int)(40*pv.getScale());

        // Изображения
        Bitmap bitmap = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.invader_c);
        imageInvader = Bitmap.createScaledBitmap(bitmap, width, height, true);
        image = imageInvader;

        Bitmap bitmap2 = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.invader_c_up);
        imageInvaderUp = Bitmap.createScaledBitmap(bitmap2, width, height, true);

        Bitmap bitmap3 = BitmapFactory.decodeResource(pv.getContext().getResources(), R.drawable.boom);
        imageBoom = Bitmap.createScaledBitmap(bitmap3, width, width, true);

        // Кол-во очков
        score = 30;

        r = 57; g = 180; b = 74;
    }
}
