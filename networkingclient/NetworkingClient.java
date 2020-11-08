/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingclient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanjhardy
 */
public class NetworkingClient { 
  
  private static GUIManager frame;
  private static String username = null;
  private static ObjectOutputStream out;
  private static ObjectInputStream in;
  
  public static void main(String[] args){
    frame = new GUIManager();
  }
  
  
  public static boolean connect(){
    if(username == null) return false;
    try {
      Socket serverSocket = new Socket("localhost", 1205);
      out = new ObjectOutputStream(serverSocket.getOutputStream());
      in = new ObjectInputStream(new BufferedInputStream(serverSocket.getInputStream()));
      GUIManager.setCurrentPanel("main");
    } catch (IOException ex) {
      Logger.getLogger(NetworkingClient.class.getName()).log(Level.SEVERE, null,
              ex);
      return false;
    }
    return true;
  }
  
  //send a message to the server
  public static void send(String stringToSend){
    if(stringToSend != null){
      try {
        String[] data = new String[]{username, stringToSend};
        out.writeObject(data);
      } catch (IOException ex) {
        Logger.getLogger(NetworkingClient.class.getName()).
                log(Level.SEVERE, null, ex);
      }
    }
  }
  
  public static void recieve(){
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String[] message = (String[]) in.readObject();
          if(message != null){
            GUIManager.getMainPanel().addMessage(message);
          }
          frame.repaint();
        } catch (IOException | ClassNotFoundException ex) {
          Logger.getLogger(NetworkingClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        recieve();
      }
    }).start();
  }
  
  public static void setUsername(String username){
    username = username.trim();
    if(username.equals("")){
      NetworkingClient.username = null;
    }else{
      NetworkingClient.username = username;
    }
  }
}
