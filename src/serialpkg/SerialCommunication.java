/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serialpkg;

/**
 *
 * @author BishoyZakaria
 */
import com.fazecast.jSerialComm.*;
import java.io.IOException;
public class SerialCommunication {
    public static String Port;
    public static int PaudRate;
    private final SerialPort serialPort;
    
    
    public SerialCommunication(String port ,int paudRate) throws IOException
    {
        Port = port;
        PaudRate = paudRate;
        serialPort = SerialPort.getCommPort(Port);
        serialPort.setComPortParameters(paudRate, 8, 1, 0);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        if(serialPort.openPort())
            System.out.println("port is opened :)");
        else
            System.out.println("port is not opened :(");
        serialPort.getOutputStream().flush(); //flush the output stream 
       
    }
         
    
    public void writeData(Integer data) throws IOException, InterruptedException
    {//Integer data 
        //data.byteValue();
        serialPort.getOutputStream().flush();//flush before sending a new data
        serialPort.getOutputStream().write(data.byteValue());
        serialPort.getOutputStream().flush();//flush after sending a new data
        Thread.sleep(5); //take care this is not the start thread of gui in javafx
    }
    
    @SuppressWarnings("empty-statement")
    public int readData() throws IOException, InterruptedException
    {
        Integer data; 
        //data.byteValue();
        
        while(serialPort.getInputStream().available() == 0); //super loop until there is an input 
        data = serialPort.getInputStream().read();
        Thread.sleep(5); //take care this is not the start thread of gui in javafx
        if(data == -1) //default value for the frame from the sender
            data=0;
        
        return data.byteValue();
    }
        
    public void serialEnd() throws IOException
    {
        serialPort.getOutputStream().close(); //close the output stream
        serialPort.getInputStream().close(); //close the input stream
        if(serialPort.closePort())
            System.out.println("Serial closed");
        else
            System.out.println("Serial not closed");
    }
    
}
