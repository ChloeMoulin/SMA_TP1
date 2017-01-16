/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import model.Agent;
import model.Grille;

/**
 *
 * @author Kipicka
 */
public class GrilleFrameFinal extends JFrame {
    
    AgentPanel[][] agentsPanel;
    GridLayout l;
    Grille g;
    ArrayList<Color> colorList;
    GrilleFrame gf;
    int m;
    int n;
        
    
    public GrilleFrameFinal(int m, int n, Grille g, GrilleFrame gf) {
        
        this.n = n;
        this.m = m;
        this.gf = gf;
        this.g = g;
        colorList = gf.getColorList();
        this.agentsPanel = new AgentPanel[m][n];
        l = new GridLayout(m,n);
        this.setLayout(l);
        for(int i = 0; i < m;i++) {
            for(int j = 0; j<n;j++) {
                AgentPanel a = new AgentPanel();
                agentsPanel[i][j] =a;
                this.add(a);
                a.setVisible(true);
                
            }
        }
        this.setSize(300,300);
        this.setLocationRelativeTo(null);
        setVisible(true);
        afficherAgents();
    }
    
    public void afficherAgents() {
        
        ArrayList<Agent> agents = g.getAgents();
        for(Agent a : agents) {
            agentsPanel[(int)a.getPositionFinale().getX()][(int)a.getPositionFinale().getY()].setBackground(colorList.get(a.getId()+1));
            agentsPanel[(int)a.getPositionFinale().getX()][(int)a.getPositionFinale().getY()].setOccupe(true);
            
        }
        for(int i =0; i < m;i++) {
            for(int j=0;j<n;j++) {
                if(!agentsPanel[i][j].getOccupe())
                    agentsPanel[i][j].setBackground(colorList.get(0));
                else
                    agentsPanel[i][j].setOccupe(false);
            }
        }
    }
}


