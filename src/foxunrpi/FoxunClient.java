/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxunrpi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author pitte
 */
public class FoxunClient {
    private final String _ip;
    private final int _port;
    
    public FoxunClient(String ip, int port){
        _ip = ip;
        _port = port;
    }
    
    public void Route(int input, int output) {
        byte[] command = CreateCommand(input, output);
        
        try{
        SendRouteCommand(command);
        }
        catch(Exception ex){
            
        }
    }
    
    public void RouteToAllOutputs(int input){
        byte[] command = CreateCommand(input, 5);
        
        try{
        SendRouteCommand(command);
        }
        catch(Exception ex){
            
        }
    }
    
    private byte[] CreateCommand(int input, int output){
        byte[] command = new byte[11];
        command[0] = 0x40;
        command[1] = 0x20;
        command[2] = 0x57;
        command[3] = 0x20;
        command[4] = 0x30;     
        command[5] = (byte)Integer.toString(output - 1).charAt(0);
        command[6] = 0x20;
        command[7] = 0x30;
        command[8] = (byte)Integer.toString(input - 1).charAt(0);
        command[9] = 0x20;
        command[10] = 0x23;
        
        return command;
    }
    
    private void SendRouteCommand(byte[] command) throws SocketException, UnknownHostException, IOException{
        DatagramSocket socket = new DatagramSocket();               
        
        InetAddress address = InetAddress.getByName(_ip);
        DatagramPacket packet = new DatagramPacket(command, command.length, address, _port);
        socket.send(packet);
        
        socket.close();
    }
}
