/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Label;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Agent;

/**
 *
 * @author Kipicka
 */
public class AgentPanel extends JPanel implements Observer {
    Agent a;
    final Color c;
    boolean occupe = false;

    

    
    public AgentPanel() {
        a = null;
        c=Color.WHITE;
    }
    
    public void setOccupe(boolean b) {
        this.occupe = b;
    }
    
    public boolean getOccupe() {
        return this.occupe;
    }
    @Override
    public void update(Observable o, Object arg) {
        this.updateUI();
    }
    
    
    
}
