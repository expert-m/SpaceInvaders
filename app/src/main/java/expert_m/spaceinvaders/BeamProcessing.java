package expert_m.spaceinvaders;

import java.util.ArrayList;

public class BeamProcessing {
    private ArrayList<Beam> listBeams;
    private PackageVariables pv;

    public BeamProcessing(PackageVariables packageVariables) {
        pv = packageVariables;
        listBeams = new ArrayList();
    }

    public void create(float x, float y, boolean up, int r, int g, int b) {
        listBeams.add(new Beam(pv, x, y, up, r, g, b));
    }

    public void draw() {
        for (int i = 0; i < listBeams.size(); i++) {
            listBeams.get(i).draw();
            if(listBeams.get(i).getDeletionStatus()) {
                listBeams.remove(i);
                i--;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < listBeams.size(); i++) {
            listBeams.remove(i);
        }
    }

    public Beam getBeam(int id) {
        return listBeams.get(id);
    }

    public int getSize() {
        return listBeams.size();
    }

}