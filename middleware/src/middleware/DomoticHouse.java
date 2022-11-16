package middleware;
//Step 1:
//Import the JMS API classes.
import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
//Import the classes to use JNDI.
import javax.naming.*;
import java.util.*;
import java.lang.Math.*;

import javax.jms.*;



///////////////////////////////////////////////////////////////////////////
// RoomController class representing each room controller
///////////////////////////////////////////////////////////////////////////
class RoomController{

	static int NUM_SENSORS_PER_ROOM = 2;
	static String[] ROOM_NAMES = {"BED_ROOM_1","BED_ROOM_2","LIVING_ROOM","DINING_ROOM"};
	
    public int room;
    // Message consumer for receiving messages from the Bus
    public MessageConsumer msgConsumer;
    // Message producer for sending messages to the Bus    
    public MessageProducer msgProducer;
    // Array containing sensor temperatures associated to this room
    public int sensors[] = new int[NUM_SENSORS_PER_ROOM];
    // Array containing temperature updates from sensors associated to this room    
    public int sensor_updates[] = new int[NUM_SENSORS_PER_ROOM];    

    ///////////////////////////////////////////////////////////////////////////
    // Methods for handling race conditions for reading and writing shared data
    ///////////////////////////////////////////////////////////////////////////    
    public void setSensorTemperature(int idx, int update){

        synchronized(this){
           this.sensors[idx] += update;   
        }
    }    

    public int getSensorTemperature(int idx){

        synchronized(this){
           return this.sensors[idx];   
        }
    }     

    public void setSensorUpdate(int idx, int value){

        synchronized(this){
           this.sensor_updates[idx] = value;   
        }
    }    

    public int getSensorUpdate(int idx){

        synchronized(this){
           return this.sensor_updates[idx];   
        }
    }        
    ///////////////////////////////////////////////////////////////////////////    
    ///////////////////////////////////////////////////////////////////////////
    
    
    ///////////////////////////////////////////////////////////////////////////
    // TextListener class for handling messages asynchronously
    ///////////////////////////////////////////////////////////////////////////
	class TextListener implements MessageListener {
	
		RoomController controller;
		
		public TextListener(RoomController controller) {
			this.controller = controller;
		}
	
		public void onMessage(Message message) {
		    if (message instanceof TextMessage) {
		
		        TextMessage  msg = (TextMessage) message;
		
		        try {
		        	
		        	String msgType = msg.getStringProperty("msgType");
		        	
		        	switch(msgType)
		            {
		                case "Measure":
				        	int sensorId = msg.getIntProperty("sensorId");
				        	controller.setSensorUpdate(sensorId, Integer.parseInt(msg.getText()));		        			        	
		                    break;
		                case "Command":
		                	int neighborRoomId = msg.getIntProperty("roomId");		                	
		                    System.out.println("\n\n[Command] Recived from Room " + ROOM_NAMES[neighborRoomId]);
		                    break;
		                default:
		                    System.out.println("ERROR: Unknown message type: [" + msgType + "]");
		            }
		        	

		        	
		        } catch (Exception e) {
		            System.out.println("Exception in onMessage(): " + e.toString());
		        }
		    }
		
		}
	
	}
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    
	
	
    ///////////////////////////////////////////////////////////////////////////
	// Method for printing room info
    ///////////////////////////////////////////////////////////////////////////	
	public void printRoomInfo(int roomId) {
		
		// Clear console output
    	System.out.print("\033[H\033[2J");
    	System.out.flush();
    	
    	System.out.println("+------------------------------+");    	
    	System.out.println("+           ROOM INFO          +");
    	System.out.println("+------------------------------+");
    	
	    System.out.println("Room: "+ROOM_NAMES[roomId] + " | ID = " + roomId + "\n");			
		for(int j=0;j<NUM_SENSORS_PER_ROOM;j++) {
			// '%1$2' -> Print format: int with 2 leading spaces 
			String strLine = "\tTemp sensor "+j+": "+ String.format("%1$2s", getSensorTemperature(j));
			int update = getSensorUpdate(j);
			// '%1$2' -> Print format: int with 2 leading spaces			
			strLine += update == -10 ? "" : " " + String.format("%1$2s", update) + " [UPDATE]";
		    System.out.println(strLine);				
		}		
		
	}
	
    ///////////////////////////////////////////////////////////////////////////
	// Method for update room info
    ///////////////////////////////////////////////////////////////////////////	
	public void updateRoomInfo() {
		
		for(int j=0;j<NUM_SENSORS_PER_ROOM;j++) {
			int update = getSensorUpdate(j);
			update = update == -10 ? 0 : update;
			setSensorTemperature(j, update); 
			// Clean update value
			setSensorUpdate(j,-10);				
		}
	}
	
	
    ///////////////////////////////////////////////////////////////////////////
	// Method for sending command messages to neighbor rooms
    ///////////////////////////////////////////////////////////////////////////	
	public void sendCommandMsg(int roomId, Session session) {
		
		// Compute average temperature in the room
		int averageTemp = 0;
		for(int j=0;j<NUM_SENSORS_PER_ROOM;j++) {
			averageTemp += getSensorTemperature(j);
		}
		averageTemp = averageTemp / NUM_SENSORS_PER_ROOM;
		
	    try {			
	        TextMessage textMsg = session.createTextMessage();
	        textMsg.setText("" + averageTemp);
	        textMsg.setStringProperty("msgType","Command");
	        textMsg.setIntProperty("roomId",roomId);		            
	    	this.msgProducer.send(textMsg);		
	    } catch (Exception jmse) {
	        System.out.println("Exception occurred : " + jmse.toString());
	        jmse.printStackTrace();
	    }	
	    
	}
	
	
    ///////////////////////////////////////////////////////////////////////////
	// Class constructor
    ///////////////////////////////////////////////////////////////////////////	
    public RoomController(int roomId, Session session,Topic destination){

		room = roomId;
		
		for(int j=0;j<NUM_SENSORS_PER_ROOM;j++) {
			// Initial temperature is 15 degrees
			sensors[j] = 15;
			// Set to -10 means 'No new update received'
		    sensor_updates[j] = -10;				
		}		

		// Each Controller should only receive from sensors in the same room		
		String msgFilter = "(msgType = 'Measure') AND (roomId = " + roomId + ")";
		
		// And controllers from LIVING_ROOM (ID=2) and DINING_ROOM (ID=3) should 
		// also receive 'Command' type messages
		msgFilter += roomId == 2 || roomId == 3 ? " OR (msgType = 'Command')" : "";
		
	    try {	
	    	// Argument 'boolean noLocal = true' for skipping own messages
	    	msgConsumer = session.createConsumer(destination, msgFilter, true);
	    	TextListener textListener = new TextListener(this);
	    	msgConsumer.setMessageListener(textListener);
	
	    	msgProducer = session.createProducer(destination);
	    
	    } catch (Exception jmse) {
	        System.out.println("Exception occurred : " + jmse.toString());
	        jmse.printStackTrace();
	    }	    
	
    }
    
}  

	      

///////////////////////////////////////////////////////////////////////////
// Class containing the main method
///////////////////////////////////////////////////////////////////////////
public class DomoticHouse{
	


    /**
     * Main method.
     *
     * @param args
     *
     */
    public static void main(String[] args) {

    	int roomId = -1;
    	try {
    		roomId = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("ERROR: Wrong arguments provided. ");
            System.out.println("\tArguments: ROOM_ID (int)");	    
            return;
        }	    

    	Connection myConn = null;
        Session mySess = null;   	
    	
        try {

            ConnectionFactory myConnFactory;
            Topic  myTopic;

            myConnFactory = new com.sun.messaging.ConnectionFactory();

            myConn = myConnFactory.createConnection();

            mySess = myConn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            myTopic = new com.sun.messaging.Topic("house");


            RoomController actuator = new RoomController(roomId,mySess,myTopic);
	    

            myConn.start();

            TextMessage myTextMsg = mySess.createTextMessage();
	    
            System.out.println("Controller ready for room " + roomId + " ...");            
            while(true){
            	Thread.sleep(1000);	            	
            	actuator.printRoomInfo(roomId);
            	actuator.updateRoomInfo();
            	if( (roomId == 2) || (roomId == 3) )
            		actuator.sendCommandMsg(roomId,mySess);
            }
            
            //mySess.close();
            //myConn.close();


        } catch (Exception jmse) {
            System.out.println("Exception occurred : " + jmse.toString());
            jmse.printStackTrace();
        }
    }


}
