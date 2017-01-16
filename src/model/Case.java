package model;

import java.awt.geom.Point2D;
import java.util.Observable;

/**
 * Created by thomasd on 12/12/16.
 */
public class Case extends Observable {
    Agent contenu;
    boolean estObjectif =false;
    boolean reserve = false;
    int i,j;

    public Case(int i, int j) {
        this.contenu = null;
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }
    
    public int getJ() {
        return j;
    }
    
    //non implémenté, cf rapport
    public boolean getReserve() {
        if (reserve)
            return true;
        else
            reserve = true;
            return false;
    }
    

    public Agent getContenu() {
        return contenu;
    }

    public void setContenu(Agent contenu) {
        this.contenu = contenu;
        this.reserve = false;
        this.setChanged();
        this.notifyObservers(this);
    }

    public boolean isEstObjectif() {
        return estObjectif;
    }

    public void setEstObjectif(boolean estObjectif) {
        this.estObjectif = estObjectif;
    }
}
