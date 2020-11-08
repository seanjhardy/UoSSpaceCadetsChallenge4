package networkingclient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static networkingclient.GUIManager.getColour;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author seanjhardy
 */
public final class LoginPanel extends JPanel{
    
    private GUIManager parent;
    private LoginUI loginUI;
    
    private Rectangle frameBounds;
    
    public LoginPanel(GUIManager parent){
        this.parent = parent;
        setBackground(getColour("background"));
        loginUI = new LoginUI(this);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        //draw background
        g.setColor(getColour("background"));
        g.fillRect(0,0,1920,1080);
        renderComponents(g2);
    }
    
    public void renderComponents(Graphics2D g2){
        frameBounds = parent.getBounds();
        loginUI.resize(0, 0, (int)frameBounds.getWidth(), (int)frameBounds.getHeight());
        loginUI.render(g2);
        
    } 
}
