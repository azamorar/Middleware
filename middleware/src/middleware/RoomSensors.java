package middleware;
//Step 1:
//Import the JMS API classes.
import javax.jms.Queue;
//Import the classes to use JNDI.
import javax.naming.*;


import java.util.*;
import java.lang.Math.*;
import javax.jms.*;



///////////////////////////////////////////////////////////////////////////
// TemperatureSensor class representing each room controller
///////////////////////////////////////////////////////////////////////////
class TemperatureSensor {

    public String name;
    public float temp;
    // Message producer for sending messages to the Bus    
    public MessageProducer msgProducer;
    
    public TemperatureSensor(String name, Session sess,Topic destination) {

    	this.name = name;
        try {	

        	msgProducer = sess.createProducer(destination);
        	
        } catch (Exception jmse) {
            System.out.println("Exception occurred : " + jmse.toString());
            jmse.printStackTrace();
        }    	
    	
    	
    }

}


public class RoomSensors {

	static int NUM_ROOMS = 4;	
	static int NUM_SENSORS_PER_ROOM = 2;
	static int UPDATE_FREQUENCY = 4000;
	static TemperatureSensor[] sensors = null;
	
	
	public static void updateTemperature(Session session) {
		
		
        try {		
    	
			for(int i=0;i<NUM_ROOMS;i++) {			
				for(int j=0;j<NUM_SENSORS_PER_ROOM;j++) {
					
					// Simulate room temperature change by random variation
					// First generate a random int [0,1] for the sign
					int sign = 0 + (int)(Math.random() * ((1 - 0) + 1));
					// From the random number [0,1] obtain a positive or negative sign
					// Transform sign: [0,1] -> [-1,1]				
					sign = sign == 0 ? 1 : -1;
					// Temperature magnitude change is a random int [0,3]
					int tempDelta = 0 + (int)(Math.random() * ((3 - 0) + 1));
					// Then change temperature magnitude with the sign
					// Random int [-3,3]
					tempDelta = (sign * tempDelta);
					
			    	sensors[(i*NUM_SENSORS_PER_ROOM)+j].temp += (sign * tempDelta);
			    	
			        // Compose sensor msg with temperature measurement
			        TextMessage textMsg = session.createTextMessage();
			    	textMsg.setText("" + tempDelta);
			        // Specify the message type
			    	textMsg.setStringProperty("msgType","Measure");
			        // Specify the target roomId -> message filter in the bus
			    	textMsg.setIntProperty("roomId",i);
			        // Inform about the sensor ID within the room
			    	textMsg.setIntProperty("sensorId",j);
			        // Sensor 'j' in room 'i' sends the msg
			    	sensors[(i*NUM_SENSORS_PER_ROOM)+j].msgProducer.send(textMsg);
			    	System.out.println("ROOM "+i+" | SENSOR "+ j + " -> " + String.format("%1$2s", tempDelta));
				}
				if(i!=(NUM_ROOMS-1))
					System.out.println("------------");
			}
        } catch (Exception jmse) {
            System.out.println("Exception occurred : " + jmse.toString());
            jmse.printStackTrace();
        }		
		
	}	
	
    /**
     * Main method.
     *
     * @param args	not used
     *
     */
    public static void main(String[] args) {

        try {
        	
        	

            ConnectionFactory myConnFactory;
            Topic myTopic;

            myConnFactory = new com.sun.messaging.ConnectionFactory();

            Connection myConn = myConnFactory.createConnection();

            Session mySess = myConn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            myTopic = new com.sun.messaging.Topic("house");
            
            sensors = new TemperatureSensor[NUM_ROOMS*NUM_SENSORS_PER_ROOM];
            
            for(int i=0;i<(NUM_ROOMS*NUM_SENSORS_PER_ROOM);i++) {

            	sensors[i] = new TemperatureSensor(""+i, mySess, myTopic);
            }

            // Wait 1.5 s for sensor creation
        	Thread.sleep(1500);
            
            while(true){  
            	Thread.sleep(UPDATE_FREQUENCY);            	
            	updateTemperature(mySess);
            	System.out.println("+-----------------------------+");
            	                     
            	                    
            }

//            mySess.close();
//            myConn.close();

        } catch (Exception jmse) {
            System.out.println("Exception occurred : " + jmse.toString());
            jmse.printStackTrace();
        }
    }
}
