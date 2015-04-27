package expert_m.spaceinvaders;

import android.os.SystemClock;
import java.util.ArrayList;
import java.util.Random;

import expert_m.spaceinvaders.Invaders.Boss1;
import expert_m.spaceinvaders.Invaders.Boss2;
import expert_m.spaceinvaders.Invaders.Boss3;
import expert_m.spaceinvaders.Invaders.InvaderA;
import expert_m.spaceinvaders.Invaders.InvaderB;
import expert_m.spaceinvaders.Invaders.InvaderC;
import expert_m.spaceinvaders.Invaders.InvaderSaucer;

public class InvaderProcessing {
    private ArrayList<Invader> listInvader;
    private PackageVariables pv;
    private long timeSaucer, saucerInterval;

    public InvaderProcessing(PackageVariables packageVariables) {
        pv = packageVariables;
        listInvader = new ArrayList();
        timeSaucer = SystemClock.uptimeMillis();
        saucerInterval = (new Random()).nextInt(5) * 5000 + 60000;
    }

    public void create(String type) {
        float x = 10*pv.getScale(), y = 50*pv.getScale();
        create(x, y, type);
    }

    public void create(float x, float y, String type) {
        switch (type) {
            case "InvaderA":
                listInvader.add(new InvaderA(pv, x, y));
                break;
            case "InvaderB":
                listInvader.add(new InvaderB(pv, x, y));
                break;
            case "InvaderC":
                listInvader.add(new InvaderC(pv, x, y));
                break;
            case "InvaderSaucer":
                listInvader.add(new InvaderSaucer(pv, x, y));
                break;
            case "Boss1":
                listInvader.add(new Boss1(pv, x, y));
                break;
            case "Boss2":
                listInvader.add(new Boss2(pv, x, y));
                break;
            case "Boss3":
                listInvader.add(new Boss3(pv, x, y));
                break;
        }
    }

    public void draw() {
        processingAdditional();

        for (int i = 0; i < listInvader.size(); i++) {
            listInvader.get(i).move();
            listInvader.get(i).draw();

            if(!listInvader.get(i).getExist()) {
                pv.addScore(getInvader(i).getScore());
                pv.addSpeedUp((float) 0.05);
                listInvader.remove(i);
                i--;

                if (pv.getStatusRepairBarrier()) {
                    pv.getBarrierProcessing().repair(20);
                }
            }
        }
    }

    public void createWave() {
        float x = 10*pv.getScale(), y = 50*pv.getScale();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                switch (i) {
                    case 0:
                        create(x, y, "InvaderC");
                        break;
                    case 1:
                    case 2:
                        create(x, y, "InvaderA");
                        break;
                    case 3:
                    case 4:
                        create(x, y, "InvaderB");
                        break;
                }

                x += getLastInvader().getWidthInvader() + 10*pv.getScale();
            }

            x = (int)(10*pv.getScale());
            y += getLastInvader().getHeightInvader() + 10*pv.getScale();
        }
    }

    public Invader getLastInvader() {
        return listInvader.get(listInvader.size() - 1);
    }

    public Invader getInvader(int id) {
        return listInvader.get(id);
    }

    public int getSize() {
        return listInvader.size();
    }

    public void hitInvader(int id) {
        listInvader.get(id).hit();
    }

    public void processingAdditional() {
        if (pv.getTime() < timeSaucer+saucerInterval) return;

        float ty, th;
        for (int i = 0; i < pv.getInvaderProcessing().getSize(); i++) {
            if (!pv.getInvaderProcessing().getInvader(i).getExist() || pv.getInvaderProcessing().getInvader(i).getStatusBoom()) continue;

            ty = pv.getInvaderProcessing().getInvader(i).getYInvader();
            th = pv.getInvaderProcessing().getInvader(i).getHeightInvader();

            if ((ty <= 50*pv.getScale() && 50*pv.getScale() <= ty + th) || (ty <= 80*pv.getScale() && 88*pv.getScale() <= ty + th)) {
                saucerInterval = 2000;
                return;
            }
        }

        timeSaucer = pv.getTime();
        saucerInterval = (new Random()).nextInt(5) * 5000 + 60000;
        create(0, 50*pv.getScale(), "InvaderSaucer");
    }
}
