/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication12;

import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafxapplication12.FXMLDocumentController.*;
import serialpkg.*;

/**
 *
 * @author Dell
 */
public class JavaFXApplication12 extends Application {
    public static SerialCommunication serial;
  public static int data;

  
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
       // stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                try{
                serial = new SerialCommunication("COM3" ,9600);
                Scanner myObj = new Scanner(System.in);
                while(true)
                {
                    
                }
                }catch(Exception e)
                {
                    System.out.println(e);
                }
            }
            
        }
        ).start();
        launch(args);
    }
    @Override
    public void stop() throws Exception {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
        serial.serialEnd();
    }
    
}
