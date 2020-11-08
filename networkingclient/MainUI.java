/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingclient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import static networkingclient.GUIManager.getColour;
import static networkingclient.GUIManager.getDefaultFont;

/**
 *
 * @author seanjhardy
 */
public class MainUI extends Menu{
    
    public MainUI(MainPanel parent){
        super(parent);
        createComponents();
    }
    
    public final void createComponents(){
      createMessageArea("messageArea", getColour("background"));
      
      JTextField chatInput = createTextField("chatInput", "", getColour("background"));
      chatInput.addKeyListener(new KeyAdapter(){
        public void keyPressed(KeyEvent evt){
          if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            NetworkingClient.send(chatInput.getText());
            chatInput.setText("");
            parent.repaint();
          }
        }
      });
    }
    
    public void render(Graphics2D g){
        g.setColor(getColour("background"));
        g.fillRect(0,0,width, 30);
        
        getComponent("messageArea").setBounds((int)(startX + 20), (int)(startY + 20), (int)(width - 40), (int)(height - 140));
        getComponent("chatInput").setBounds((int)(startX + 20), (int)(startY + height - 100), (int)(width - 40), 60);
    }
    
    public JLabel getMessageArea(){
      return (JLabel) ((JScrollPane)getComponent("messageArea")).getViewport().getView();
    }
    
    public final void createMessageArea(String name, Color colour){
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(getColour("background"));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        JLabel messageArea = new JLabel();
        messageArea.setVerticalAlignment(SwingConstants.BOTTOM);
        messageArea.setForeground(getColour("foreground"));
        messageArea.setBackground(colour);
        messageArea.setOpaque(true);
        messageArea.setFont(new Font(getDefaultFont(), 0, 14));
        
        scrollPane.getViewport().add(messageArea);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        parent.add(scrollPane);
        components.put(name, scrollPane);
    }
}
