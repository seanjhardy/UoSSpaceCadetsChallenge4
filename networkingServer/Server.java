/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanjhardy
 */
public class Server {
  
  private static ArrayList<ConnectionThread> threads;
  private static ServerSocket serverSocket;
  
  public static void main(String[] args) {
    Server server = new Server();
    threads = new ArrayList<>();
  }
  
  public Server() {
    try {
      serverSocket = new ServerSocket(1205);
      Socket clientSocket;
      while((clientSocket = serverSocket.accept()) != null){
        if(clientSocket != null){
          ConnectionThread conn = new ConnectionThread(clientSocket);
          threads.add(conn);
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
