package practicaJMS;

import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
//Import the classes to use JNDI.
import java.util.*;
import javax.jms.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TPVObligatorio {

	private File input;

	
	public TPVObligatorio(File input) {
		this.input = input;
	}

	
	// Metodo para leer las lineas de los ficheros .txt y enviarlas al servidor
	private void leer(TextMessage myTxt, MessageProducer myMsgP) {

		String line = "";

		try {

			BufferedReader br = new BufferedReader(new FileReader(input));


			while((line = br.readLine()) != null){
				myTxt.setText(line);
				myMsgP.send(myTxt);

			}
			
			br.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (JMSException r) {
			System.err.println(r.getMessage());
		}

	}


	public static void main(String[] args) {

		try {
			ConnectionFactory myConnFactory;
			Queue myQueue, myQueue1, myQueue2, myQueue3, myQueue4, myQueue5, myQueue6;

			// No-standard way of getting Context Factory
			myConnFactory = new com.sun.messaging.ConnectionFactory();
			Connection myConn = myConnFactory.createConnection();
			Session mySess = myConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// No-standard way of getting destination
			myQueue = mySess.createQueue("servidor");
			myQueue1 = mySess.createQueue("N_A");
			myQueue2 = mySess.createQueue("S_A");
			myQueue3 = mySess.createQueue("EUR");
			myQueue4 = mySess.createQueue("ASI");
			myQueue5 = mySess.createQueue("OCE");
			myQueue6 = mySess.createQueue("AFR");

			// Creacion de los productores de mensajes 
			MessageProducer myMsgProducer = mySess.createProducer(myQueue);
			MessageProducer myMsgProducer1 = mySess.createProducer(myQueue1);
			MessageProducer myMsgProducer2 = mySess.createProducer(myQueue2);
			MessageProducer myMsgProducer3 = mySess.createProducer(myQueue3);
			MessageProducer myMsgProducer4 = mySess.createProducer(myQueue4);
			MessageProducer myMsgProducer5 = mySess.createProducer(myQueue5);
			MessageProducer myMsgProducer6 = mySess.createProducer(myQueue6);

			TextMessage myTextMsg = mySess.createTextMessage();



			//--------------------


			System.out.println("Introduzca servidor: ");
			System.out.println("NORTE AMERICA: 1 ");
			System.out.println("SUR AMERICA: 2 ");
			System.out.println("EUROPA: 3 ");			
			System.out.println("ASIA: 4 ");
			System.out.println("OCEANIA: 5");
			System.out.println("AFRICA: 6");

			


			Scanner sc = new Scanner(System.in);
			Integer entry = sc.nextInt();
			sc.close();

			
			myTextMsg.setText(entry.toString());
			myMsgProducer.send(myTextMsg);

			//Lectura de los TPV por region 
			switch(entry) {
			case 1:
				
				
				TPVObligatorio tpv1 = new TPVObligatorio(new File("./include/NORTH_AMERICA_TPV_1.txt"));
				tpv1.leer(myTextMsg, myMsgProducer1);
				TPVObligatorio tpv2 = new TPVObligatorio(new File("./include/NORTH_AMERICA_TPV_2.txt"));
				tpv2.leer(myTextMsg, myMsgProducer1);
				TPVObligatorio tpv3 = new TPVObligatorio(new File("./include/NORTH_AMERICA_TPV_3.txt"));
				tpv3.leer(myTextMsg, myMsgProducer1);
				TPVObligatorio tpv4 = new TPVObligatorio(new File("./include/NORTH_AMERICA_TPV_4.txt"));
				tpv4.leer(myTextMsg, myMsgProducer1);
				TPVObligatorio tpv5 = new TPVObligatorio(new File("./include/NORTH_AMERICA_TPV_5.txt"));
				tpv5.leer(myTextMsg, myMsgProducer1);
				
				
				
				myTextMsg.setText("FIN");
				myMsgProducer1.send(myTextMsg);
				
				break;

			case 2:
				
				TPVObligatorio tpv6 = new TPVObligatorio(new File("./include/SOUTH_AMERICA_TPV_1.txt"));
				tpv6.leer(myTextMsg, myMsgProducer2);
				TPVObligatorio tpv7 = new TPVObligatorio(new File("./include/SOUTH_AMERICA_TPV_2.txt"));
				tpv7.leer(myTextMsg, myMsgProducer2);
				TPVObligatorio tpv8 = new TPVObligatorio(new File("./include/SOUTH_AMERICA_TPV_3.txt"));
				tpv8.leer(myTextMsg, myMsgProducer2);
				TPVObligatorio tpv9 = new TPVObligatorio(new File("./include/SOUTH_AMERICA_TPV_4.txt"));
				tpv9.leer(myTextMsg, myMsgProducer2);
				
				
				
				myTextMsg.setText("FIN");
				myMsgProducer2.send(myTextMsg);
				

				break;

			case 3:
				
				TPVObligatorio tpv10 = new TPVObligatorio(new File("./include/EUROPE_TPV_1.txt"));
				tpv10.leer(myTextMsg, myMsgProducer3);
				TPVObligatorio tpv11 = new TPVObligatorio(new File("./include/EUROPE_TPV_2.txt"));
				tpv11.leer(myTextMsg, myMsgProducer3);
				TPVObligatorio tpv12 = new TPVObligatorio(new File("./include/EUROPE_TPV_3.txt"));
				tpv12.leer(myTextMsg, myMsgProducer3);
				TPVObligatorio tpv13 = new TPVObligatorio(new File("./include/EUROPE_TPV_4.txt"));
				tpv13.leer(myTextMsg, myMsgProducer3);
				
				
				myTextMsg.setText("FIN");
				myMsgProducer3.send(myTextMsg);
				
				break;

			case 4:
				
				TPVObligatorio tpv14 = new TPVObligatorio(new File("./include/ASIA_TPV_1.txt"));
				tpv14.leer(myTextMsg, myMsgProducer4);
				TPVObligatorio tpv15 = new TPVObligatorio(new File("./include/ASIA_TPV_2.txt"));
				tpv15.leer(myTextMsg, myMsgProducer4);
				TPVObligatorio tpv16 = new TPVObligatorio(new File("./include/ASIA_TPV_3.txt"));
				tpv16.leer(myTextMsg, myMsgProducer4);
				TPVObligatorio tpv17 = new TPVObligatorio(new File("./include/ASIA_TPV_4.txt"));
				tpv17.leer(myTextMsg, myMsgProducer4);
				TPVObligatorio tpv18 = new TPVObligatorio(new File("./include/ASIA_TPV_5.txt"));
				tpv18.leer(myTextMsg, myMsgProducer4);
				TPVObligatorio tpv19 = new TPVObligatorio(new File("./include/ASIA_TPV_6.txt"));
				tpv19.leer(myTextMsg, myMsgProducer4);
				
				
				myTextMsg.setText("FIN");
				myMsgProducer4.send(myTextMsg);
				
				break;

			case 5:
				
				TPVObligatorio tpv20 = new TPVObligatorio(new File("./include/OCEANIA_TPV_1.txt"));
				tpv20.leer(myTextMsg, myMsgProducer5);
				TPVObligatorio tpv21 = new TPVObligatorio(new File("./include/OCEANIA_TPV_2.txt"));
				tpv21.leer(myTextMsg, myMsgProducer5);
				TPVObligatorio tpv22 = new TPVObligatorio(new File("./include/OCEANIA_TPV_3.txt"));
				tpv22.leer(myTextMsg, myMsgProducer5);
				TPVObligatorio tpv23 = new TPVObligatorio(new File("./include/OCEANIA_TPV_4.txt"));
				tpv23.leer(myTextMsg, myMsgProducer5);
				
				
				
				myTextMsg.setText("FIN");
				myMsgProducer5.send(myTextMsg);
				
				break;

			case 6:
				
				
				TPVObligatorio tpv24 = new TPVObligatorio(new File("./include/AFRICA_TPV_1.txt"));
				tpv24.leer(myTextMsg, myMsgProducer6);
				TPVObligatorio tpv25 = new TPVObligatorio(new File("./include/AFRICA_TPV_2.txt"));
				tpv25.leer(myTextMsg, myMsgProducer6);
				TPVObligatorio tpv26 = new TPVObligatorio(new File("./include/AFRICA_TPV_3.txt"));
				tpv26.leer(myTextMsg, myMsgProducer6);
				
				myTextMsg.setText("FIN");
				myMsgProducer6.send(myTextMsg);
				
				break;
				

			default: System.out.println("El codigo introducido es invalido");break;
			}

			mySess.close();
			myConn.close();

		} catch (Exception jmse) {
			jmse.printStackTrace();
		}
		
	}
}
