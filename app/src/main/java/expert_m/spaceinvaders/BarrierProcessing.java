package expert_m.spaceinvaders;

import java.util.ArrayList;
import java.util.Random;

public class BarrierProcessing {
    private ArrayList<Barrier> listBarrier;
    private PackageVariables pv;

    public BarrierProcessing(PackageVariables packageVariables) {
        pv = packageVariables;
        listBarrier = new ArrayList();
    }

    public void create(float x, float y) {
        listBarrier.add(new Barrier(pv, x, y));
    }

    public void draw() {
        if (listBarrier.size() == 0) {
            int bw = (int) (7 * pv.getScale());
            int bww = 8*bw;
            int x = pv.getWidthPixels()/4 - bww/2;
            int y = (int) (pv.getSpaceShip().getYShip() - 55 * pv.getScale());

            for (int i = 0; i < 8; i++) {
                create(x, y);
                create(x, y+bw);
                create(x, y+2*bw);
                create(x, y+3*bw);
                x += bw;
            }

            x = pv.getWidthPixels()/2+pv.getWidthPixels()/4 - bww/2;
            for (int i = 0; i < 8; i++) {
                create(x, y);
                create(x, y+bw);
                create(x, y+2*bw);
                create(x, y+3*bw);
                x += bw;
            }
        }

        for (int i = 0; i < listBarrier.size(); i++) {
            listBarrier.get(i).draw();
        }
    }

    public int getSize() {
        return listBarrier.size();
    }

    public void delBarrier(int id) {
        listBarrier.get(id).setExist(false);
    }

    public Barrier getBarrier(int id) {
        return listBarrier.get(id);
    }

    public void repair(int rand) {
        for (int i = 0; i < listBarrier.size(); i++) {
            if (listBarrier.get(i).getExist()) continue;

            if ((new Random()).nextInt(rand) == 0)
                listBarrier.get(i).setExist(true);
        }
    }
}
