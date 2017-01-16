package model;

import vue.GrilleFrame;

public class Main {

    public static void main(String[] args) {
        //Dimensions en paramètre : largeur, hauteur, nombre d'agents
        Grille g = new Grille(5,5,10); 
        
               
        g.startGrille();
    }
}
