/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingclient;

import static networkingclient.GUIManager.getColour;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static networkingclient.NetworkingClient.recieve;

/**
 *
 * @author seanjhardy
 */
public final class MainPanel extends JPanel{
    
    private GUIManager parent;
    private MainUI mainUI;
    private ArrayList<String[]> messages;
    
    private Rectangle frameBounds;
    
    public MainPanel(GUIManager parent){
        this.parent = parent;
        messages = new ArrayList<>();
        setBackground(getColour("background"));
        mainUI = new MainUI(this);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        //draw background
        g.setColor(getColour("background"));
        g.fillRect(0,0,1920,1080);
        //render components
        renderComponents(g2);
    }
    
    public void addMessage(String[] message){
      messages.add(message);
    }
    
    public void renderComponents(Graphics2D g2){
        frameBounds = parent.getBounds();
        String messageHistory = "<html>";
        for(String[] message : messages){
          messageHistory += message[0] + ": " + message[1] + "<br>";
        }
        messageHistory += "<html>";
        ((JLabel)mainUI.getMessageArea()).setText(messageHistory);
        mainUI.resize(0, 0, (int)frameBounds.getWidth(), (int)frameBounds.getHeight());
        mainUI.render(g2);
        
    } 
}
