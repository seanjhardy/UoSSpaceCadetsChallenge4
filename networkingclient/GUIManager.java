/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingclient;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author s-hardy
 */
public final class GUIManager extends JFrame{
    //panels
    private static CardLayout layoutController;
    private static JPanel panelController;
    private static LoginPanel loginPanel;
    private static MainPanel mainPanel;
    
    private static String currentPanel;
    
    //visuals and sprites
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final File IMAGE_DIRECTORY = new File("assets");
    private static HashMap<String, BufferedImage> images;
    private static HashMap<String, Color> colourScheme;
    
    //custom variables
    private static String font;
        

    //initialisation
    public GUIManager(){
        super("Messager");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //load sprites
        loadColourScheme();
        loadImages();
        createPanels();
        setFrameProperties();
    }
    
    public void createPanels(){ 
        mainPanel = new MainPanel(this);
        loginPanel = new LoginPanel(this);
        
        layoutController = new CardLayout();
        panelController = new JPanel(layoutController);
        
        //This componentListener allows the panel to
        //dynamically resize every widget when the frame changes shape
        panelController.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                switch (currentPanel) {
                    case "main":
                        mainPanel.repaint();
                        break;
                      case "login":
                        mainPanel.repaint();
                        break;
                    default:
                        break;
                }
            }
        });
        //add the panels to the cardlayout
        layoutController.addLayoutComponent(loginPanel, "login");
        panelController.add(loginPanel);
        layoutController.addLayoutComponent(mainPanel, "main");
        panelController.add(mainPanel);
        
        //add the cardlayout panel to the main frame
        add(panelController);
    }
    
    public void setFrameProperties(){
        setCurrentPanel("login");
        
        setSize(500, 500);
        setMinimumSize(new Dimension(500,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        setBackground(getColour("background"));
        setVisible(true); 
    }
    
    //image/colour manipulation
    public static Color brightness(Color c, double i){
        int R = (int) Math.max(Math.min(c.getRed()*i,255),0);
        int G = (int) Math.max(Math.min(c.getGreen()*i,255),0);
        int B = (int) Math.max(Math.min(c.getBlue()*i,255),0);
        return new Color(R,G,B,c.getAlpha());
    }
    
    public static Color addAlpha(Color c, int a){
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
    }
    
    //getter methods
    public static Dimension getScreenSize(){
        return screenSize;
    }
    
    public static void loadImages(){
        images = new HashMap<>();
        loadImagesFromDirectory(IMAGE_DIRECTORY);
    }
    
    public static void loadImagesFromDirectory(File directory){
        try {
            for (File file : directory.listFiles()){
                //create a variable to store the sprite name
                if (file.isDirectory() ) {
                    loadImagesFromDirectory(file);
                }else{
                    String name = file.getName();
                    name = name.substring(0, name.lastIndexOf('.'));
                    BufferedImage image = ImageIO.read(file);
                    //add name image pair to the images hashmap
                    images.put(name, image);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GUIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadColourScheme(){
        colourScheme = new HashMap<>();
        colourScheme.put("default", new Color(255,0,230));
        colourScheme.put("background", Color.decode("#1f292e"));
        colourScheme.put("foreground", Color.decode("#d6f1ff"));
        
        colourScheme.put("noColour", new Color(0, 0, 0, 0));

    }
    
    public static BufferedImage getImage(String imageName){
        if (images.containsKey(imageName)) {
            return images.get(imageName);
        }
        return images.get("DefaultTexture");
    }
    
    public static Color getColour(String colourName){
        if (colourScheme.containsKey(colourName)) {
            return colourScheme.get(colourName);
        }
        return colourScheme.get("default");
    }
    
    public static String getDefaultFont(){
        return font;
    }
    
    //setter methods
    public static void setCurrentPanel(String panel){
        currentPanel = panel;
        layoutController.show(panelController, panel);
    }  
    
    public static MainPanel getMainPanel(){
      return mainPanel;
    }
}

