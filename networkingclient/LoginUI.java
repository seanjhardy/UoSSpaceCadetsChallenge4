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
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import static networkingclient.GUIManager.getColour;
import static networkingclient.GUIManager.getDefaultFont;

/**
 *
 * @author seanjhardy
 */
public class LoginUI extends Menu{
    
    public LoginUI(LoginPanel parent){
        super(parent);
        createComponents();
    }
    
    public final void createComponents(){
      createLabel("loginLabel", "LOGIN", SwingConstants.CENTER, SwingConstants.CENTER, 40, getColour("background")).setBorder(null);
      
      createLabel("usernameLabel", "Username:", SwingConstants.CENTER, SwingConstants.CENTER, 20, getColour("background"));
      createLabel("infoMessage", "", SwingConstants.CENTER, SwingConstants.CENTER, 20, getColour("background")).setBorder(null);
      
      JTextField usernameInput = createTextField("usernameInput", "", getColour("background"));
      usernameInput.addKeyListener(new KeyAdapter(){
        public void keyPressed(KeyEvent evt){
          if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String username = usernameInput.getText();
            if(username.equals("")){
              ((JLabel)getComponent("infoMessage")).setText("Invalid Username");
              return;
            }
            ((JLabel)getComponent("infoMessage")).setText("Connecting to Server...");
            //set the username
            NetworkingClient.setUsername(username);
            //attempt to connect to the server
            boolean connectionSuccessful = NetworkingClient.connect();
            //display success/fail message
            String connectionMessage = connectionSuccessful ? "Connection Successful" : "Connection Failed";
            ((JLabel)getComponent("infoMessage")).setText(connectionMessage);
            
            if(connectionSuccessful){
              NetworkingClient.recieve();
            }
          }
        }
      });
    }
    
    public void render(Graphics2D g){
        g.setColor(getColour("background"));
        g.fillRect(0,0,width, 30);
        
        getComponent("loginLabel").setBounds((int)(startX + width/2-100), (int)(startY + height*0.2), 200, 50);
        
        getComponent("usernameLabel").setBounds((int)(startX + width/2-200), (int)(startY + height*0.4), 200, 50);
        getComponent("usernameInput").setBounds((int)(startX + width/2), (int)(startY + height*0.4), 200, 50);
        getComponent("infoMessage").setBounds((int)(startX + width/2-100), (int)(startY + height*0.5), 200, 50);
    }
    
}
