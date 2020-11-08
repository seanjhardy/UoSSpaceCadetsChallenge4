/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingServer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanjhardy
 */
public final class ConnectionThread implements Runnable{
  
  private Socket client;
  private String connectedUser;
  
  public ConnectionThread (Socket connection) {
      client = connection;
      run();
   }
  
  @Override
  public void run() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
      
      ObjectInputStream in = new ObjectInputStream(
              new BufferedInputStream(client.getInputStream()));
      Object input = in.readObject();
      connectedUser = (String) input;
      
      // Initiate conversation with client
      while ((input = in.readObject()) != null) {
        out.writeObject(input);
      }
    } catch (IOException ex) {
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(ConnectionThread.class.getName()).log(Level.SEVERE, null,
              ex);
    }
  }
  
  public void setConnection(Socket connection){
    this.client = connection;
  }
  
  public Socket getSocket() {
    return client;
  }
}
