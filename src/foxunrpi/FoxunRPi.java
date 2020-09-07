/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxunrpi;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author pitte
 */
public class FoxunRPi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException, InterruptedException {        
        
        byte[] host = new byte[4];
        host[0] = (byte) 192;
        host[1] = (byte) 168;
        host[2] = (byte) 1;
        host[3] = (byte) 168; 
        
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput input1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput input2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_23, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput input3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_22, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput input4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);
        
        input1.setShutdownOptions(true);
        input2.setShutdownOptions(true);
        input3.setShutdownOptions(true);
        input4.setShutdownOptions(true);
        
        FoxunClient client = new FoxunClient(host, 5000);
        FourWaySwitch fourWaySwitch = new FourWaySwitch(input1, input2, input3, input4);
        System.out.println("... Listening for input ...");
        int previousInput = -1;
        
        while(true) {
            int currentInput = fourWaySwitch.getCurrentInput();
            
            if(currentInput > 0 && currentInput != previousInput){
                client.RouteToAllOutputs(currentInput);
                previousInput = currentInput;
            }
        }
    }
    
}
