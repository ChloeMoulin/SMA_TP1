package model;


import java.awt.geom.Point2D;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by thomasd on 12/12/16.
 */
public class Agent extends Observable implements Runnable {
    int id;
    Grille g;
    Point2D positionCourante;
    Point2D positionPrecedente;
    Point2D positionFinale;
    List<Point2D> deplacementPossible;
    List<Message> messagesAttentes;


    //Grille
    //Structure de communication TBD

    public Agent(int nbElem, Grille g, Point2D positionCourante) {
        id = nbElem;
        this.g = g;
        this.positionCourante = positionCourante;
        this.positionPrecedente = positionCourante;
        messagesAttentes = new ArrayList<Message>();
    }
    
    public int getId() {
        return id;
    }

    public void run() {


        while (!g.puzzleOK()) {
            this.construireDeplacementPossible();
            this.orderDeplacementPossible();
            if (!(this.aucunMesage())) {
                this.deplacementGene(messagesAttentes.get(0));
            }
            else if (positionCourante.distance(positionFinale) != 0) {
                this.faireMeilleurMouvement();
            }
            
            try {
                //this.afficheInfo();
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void deplacementGene(Message m) {
        boolean deplacementOK = false;
        int cpt = 0;
        //On essaie de se déplacer dans une des directions possibles
        do {
            Point2D deplacement = deplacementPossible.get(cpt);
            int x = (int)deplacement.getX();
            int y = (int) deplacement.getY();
            if (g.getGrille()[x][y].getContenu() == null) {
                effectuerDeplacement(deplacementPossible.get(cpt));
                this.messagesAttentes.remove(m);
                deplacementOK = true;
            }
            cpt++;
        } while (!deplacementOK && cpt < deplacementPossible.size());
        //si ce n'est pas possible, on envoie un message au premier agent adjacent
        if(!deplacementOK) {
            Double x = deplacementPossible.get(0).getX();
            Double y = deplacementPossible.get(0).getY();
            if (g.getGrille()[x.intValue()][y.intValue()].getContenu()!= null)
                envoyerMessage(g.getGrille()[x.intValue()][y.intValue()].getContenu());

        }
    }
    private void afficheInfo() {
        System.out.println("L'agent " + id + " est à la position " + this.positionCourante.toString() + " son objectif est la position " + this.positionFinale.toString());
    }

    private boolean aucunMesage() {
        boolean test;
        test = (this.messagesAttentes.isEmpty())? true : false;
        return test;
    }

    private void effectuerDeplacement(Point2D nouvellePosition) {
        this.positionPrecedente.setLocation(this.positionCourante.getX(), positionCourante.getY());
        Double xCourant = this.positionCourante.getX();
        Double yCourant = this.positionCourante.getY();
        g.getGrille()[xCourant.intValue()][yCourant.intValue()].setContenu(null);
        this.positionCourante = nouvellePosition;
        g.getGrille()[(int)nouvellePosition.getX()][(int)nouvellePosition.getY()].setContenu(this);
    }

    private void faireMeilleurMouvement() {
        
        //pour chaque déplacement possible, trié par ordre de pertinence
        for (Point2D p : deplacementPossible) {
          
            Double x = p.getX();
            Double y = p.getY();
            if(g.getGrille()[x.intValue()][y.intValue()].getContenu() == null){
                //On interdit de revenir sur ses pas pour éviter les boucles infinis
                if(!(p.equals(this.positionPrecedente)) ) {
                    
                    effectuerDeplacement(p);
                    return;
               
                // Sauf s'il s'agit de la position finale, on peut en effet s'être déplacé car on gênait
                } else if (p.equals(positionFinale)) {
                    effectuerDeplacement(p);
                        return;
                }
                           
            } else {
                //Si on ne peut pas se déplacer à la meilleure position, on envoie un message et on return
                if(g.getGrille()[x.intValue()][y.intValue()].getContenu()!= null) {
                    envoyerMessage(g.getGrille()[x.intValue()][y.intValue()].getContenu());
                    return;
                }
            }
        }
       
    }

    private void orderDeplacementPossible() {
        Collections.sort(deplacementPossible, new Comparator<Point2D>() {
            @Override
            public int compare(Point2D point2D, Point2D t1) {
                if (point2D.distance(positionFinale) < t1.distance(positionFinale))
                    return -1;
                return 0;
            }
        });

    }

    private void construireDeplacementPossible() {
        deplacementPossible = new ArrayList<>();
        int xMin = 0;
        int xMax = g.getLargeur();
        int yMin = 0;
        int yMax = g.getHauteur();
        if (positionCourante.getX() + 1 < xMax) {
            deplacementPossible.add(new Point2D.Double(this.positionCourante.getX() + 1, this.positionCourante.getY()));
        }
        if (positionCourante.getY() + 1 < yMax) {
            deplacementPossible.add(new Point2D.Double(this.positionCourante.getX(), this.positionCourante.getY() + 1));
        }
        if (positionCourante.getX() - 1 >= xMin) {
            deplacementPossible.add(new Point2D.Double(this.positionCourante.getX() - 1, this.positionCourante.getY()));
        }
        if (positionCourante.getY() - 1 >= yMin) {
            deplacementPossible.add(new Point2D.Double(this.positionCourante.getX(), this.positionCourante.getY() - 1));
        }
    }

    public void envoyerMessage(Agent a) {
        Message m = new Message(this, a);
        a.addMessageAttente(m);
    }

    public void addMessageAttente(Message m) {
        this.messagesAttentes.add(m);
    }


    public Point2D getPositionCourante() {
        return positionCourante;
    }

    public void setPositionCourante(Point2D positionCourante) {
        this.positionCourante = positionCourante;
    }

    public Point2D getPositionFinale() {
        return positionFinale;
    }

    public void setPositionFinale(Point2D positionFinale) {
        this.positionFinale = positionFinale;
    }
}




