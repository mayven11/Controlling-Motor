/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication12;

import eu.hansolo.medusa.Gauge;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static javafxapplication12.JavaFXApplication12.serial;

/**
 *
 * @author Dell
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Slider slider;
    @FXML
    private AnchorPane Anchor;
    @FXML
    private ToggleButton LightDarkMode;
    @FXML
    private Gauge gauge1;
    @FXML
    private Gauge gauge2;
    @FXML
    private ToggleButton StartStopButton;
    @FXML
    private ToggleButton direction;
    
    float setPoints;
    float rotationSpeed;
    float linearVelocity;
    /* 
    #define StartSerialOrder            1
    #define StopSerialOrder             2
    #define CW_direction_Serial_Order   3
    #define CCW_direction_Serial_Order  4
    */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void SliderDrag(MouseEvent event) throws IOException, InterruptedException {
        
        setPoints = (float)slider.getValue();
        rotationSpeed =(float) ((setPoints*0.38) - 7.66);
        gauge1.setValue(rotationSpeed);
        linearVelocity = (float)(rotationSpeed * 0.104 * 0.325 * 60); //m/min
        gauge2.setValue(linearVelocity);
        serial.writeData((int)slider.getValue());
    }

    @FXML
    private void LignhtDarkHandler(ActionEvent event) {
        if(LightDarkMode.isSelected())
        {
            Anchor.setStyle("-fx-background-image: url(\"/Images/lightBackground.jpg\")");
        }
        else
        {
            Anchor.setStyle("-fx-background-image: url(\"/Images/background.jpg\")");
        }
    }

    @FXML
    private void SrartStopHandler(ActionEvent event) throws IOException, InterruptedException {
        if(StartStopButton.isSelected())
        {
            serial.writeData(1);
        }
        else
        {
            serial.writeData(2);
            serial.writeData(10);
            gauge1.setValue(0);
            gauge2.setValue(0);
            slider.setValue(0);
        }
    }

    @FXML
    private void directionHandler(ActionEvent event) throws IOException, InterruptedException {
        if(direction.isSelected())
        {
            serial.writeData(4);
        }
        else
        {
            serial.writeData(3);
        }
    }


    
}
