package model;

import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import vue.GrilleFrame;

/**
 * Created by thomasd on 12/12/16.
 */
public class Grille {

    ArrayList<Agent> listAgent = new ArrayList<>();
    Case[][] grille;
    final int largeur;
    final int hauteur;
    GrilleFrame gf;
    

    public Grille(int largeur, int hauteur, int nbElement) {
  
        this.largeur = largeur;
        this.hauteur = hauteur;
        grille = new Case[largeur][hauteur];
        this.initGrille(nbElement);
        this.initGoal();
        gf.createGrilleGoal();
    }
    
    public ArrayList<Agent> getAgents() {
        return listAgent;
    }

    private void initGrille(int nbElement) {
        gf = new GrilleFrame(largeur,hauteur,nbElement,this);
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {              
                grille[i][j] = new Case(i,j);
                grille[i][j].addObserver(gf);
            }
        }
        // On place l'agent dans la grille
        for (int nbElem = 0; nbElem < nbElement; nbElem++) {
            boolean posOK = false;
            do {
                int posX = new Random().nextInt(largeur);
                int posY = new Random().nextInt(hauteur);
                if (grille[posX][posY].getContenu() == null) {
                    Agent a = new Agent(nbElem,this, new Point2D.Double(posX, posY));                    
                    listAgent.add(a);
                    grille[posX][posY].setContenu(a);
                    posOK = true;
                }

            } while (posOK != true);
        }
    }

    private void initGoal() {
        for (Agent a : listAgent) {
            boolean posOK = false;
            do {
                int posX = new Random().nextInt(largeur);
                int posY = new Random().nextInt(hauteur);
                if (grille[posX][posY].isEstObjectif() == false && !(grille[posX][posY].getContenu() == a)) {
                    a.setPositionFinale(new Point2D.Double(posX, posY));
                    grille[posX][posY].setEstObjectif(true);
                    posOK = true;
                }

            } while (posOK != true);
        }
    }

    public boolean puzzleOK(){
        for (Agent a:listAgent)
        {
            if(a.getPositionCourante().distance(a.getPositionFinale())!=0){
                return false;
            }
        }
        return true;
    }

    public void startGrille() {
        for (Agent a:listAgent) {
            Thread t = new Thread(a);
            t.start();

        }
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public Case[][] getGrille() {
        return grille;
    }

    public void setGrille(Case[][] grille) {
        this.grille = grille;
    }


}

