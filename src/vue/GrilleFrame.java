/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import model.Agent;
import model.Grille;
import model.Case;


/**
 *
 * @author Kipicka
 */
public class GrilleFrame extends JFrame implements Observer {
    
    GridLayout l;
    AgentPanel[][] agentsPanel;
    ArrayList<Color> colorList = new ArrayList<Color>();
    Grille g;
    int m;
    int n;
    
    public GrilleFrame (int m, int n, int nbAgents, Grille g) {
        
        createColor(nbAgents);
        this.g = g;
        this.m=m;
        this.n=n;
        
        this.agentsPanel = new AgentPanel[m][n];
        l = new GridLayout(m,n);
        this.setLayout(l);
        for(int i = 0; i < m;i++) {
            for(int j = 0; j<n;j++) {
                AgentPanel a = new AgentPanel();
                agentsPanel[i][j] =a;
                this.add(a);
                a.setBackground(colorList.get(0));
                a.setVisible(true);
                
            }
        }
        this.setSize(500,500);
        
        setVisible(true);
    }
    
    public void createGrilleGoal() {
        new GrilleFrameFinal(m,n, g, this);
    }
    
    
    public void afficherCases(Case c) {
        if(c.getContenu()!= null)
            agentsPanel[c.getI()][c.getJ()].setBackground(colorList.get(c.getContenu().getId()+1));
        else    
            agentsPanel[c.getI()][c.getJ()].setBackground(colorList.get(0));
    }

    @Override
    public void update(Observable o, Object arg) {
        Case c = (Case) o;
        afficherCases(c);
    }
    
    public void createColor(int n) {
        colorList.add(Color.WHITE);
        for(int i = 0; i <n; i++) {
            randomColor();
        }
    }
    
    public ArrayList<Color> getColorList() {
        return colorList;
    }
    public Color randomColor() {
        Color color;
        do {
            int R = (int)(Math.random()*256);
            int G = (int)(Math.random()*256);
            int B= (int)(Math.random()*256);
            color = new Color(R,G,B);
        } while (colorList.contains(color));
        colorList.add(color);
        return color;
    }
}
