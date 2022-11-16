package practicaJMS;


import javax.jms.Queue;
import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public class ServidorObligatorio {


	TPVObligatorio tpv;
	//Maps mpara almacenar los mensajes y los casos de deteccion de fraudes
	static Map<String, Integer> fraudes;
	static Map <String,Integer> deteccion ;


	public ServidorObligatorio() {
	}

	//Metodo para imprimir el ranking de usuarios con mayor numero de casos de fraude en una region
	private static void ranking(Map<String, Integer> mapa) {


		Entry<String,Integer> valor;


		valor = null;
		for (Entry<String,Integer> entrada : mapa.entrySet()) {
			if (valor == null || entrada.getValue().compareTo(valor.getValue()) > 0 ){
				valor = entrada;
			}
		}
		mapa.remove(valor.getKey());
		System.out.println(""+valor.getKey()+"     "+valor.getValue());

	}

	// Metodo para tratar los casos de fraude dependiendo de la region en la que se este 
	private static void infoTPV(MessageConsumer myMsgConsumer) {
		Message msg;
		boolean fin = false;
		String id;
		String splitBy=" ";
		Integer value;
		while(!fin){
			try {
				//Mensaje recibido
				msg = myMsgConsumer.receive();

				TextMessage txtMsg = (TextMessage) msg;
				try {						
					id = txtMsg.getText();
					//Comprobacion de fin de lectura para salir del bucle
					if(id.equals("FIN") ) {
						fin=true;
					}

					else {

						if (fraudes.containsKey(id)) {
							value = fraudes.get(id);
							fraudes.put(id, value+1);
							String info []= id.split(splitBy);
							// Almacenamiento de usuarios con el numero de fraudes
							if (deteccion.containsKey(info[2]) &&  value < 1) {
								deteccion.put(info[2], deteccion.get(info[2])+1);
							}
							else if (!deteccion.containsKey(info[2])){								
								deteccion.put(info[2], 1);
							}
						}
						else {
							fraudes.put(id,0);
						}
					}

				} catch (JMSException e) {
					e.printStackTrace();
				}

			}catch(Exception e) {

			}
		}
	}

	public static void main (String [] args) {


		fraudes = new HashMap<String,Integer>();

		deteccion = new HashMap<String,Integer>();


		Connection myConn = null;
		Session mySess = null; 

		ConnectionFactory myConnFactory;
		Queue myQueue, myQueue1, myQueue2, myQueue3, myQueue4, myQueue5, myQueue6;

		myConnFactory = new com.sun.messaging.ConnectionFactory();

		try {

			myConn = myConnFactory.createConnection();

			mySess = myConn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Colas con los mensajes dependiendo de la region 
			myQueue = new com.sun.messaging.Queue("servidor");
			myQueue1 = new com.sun.messaging.Queue("N_A");
			myQueue2 = new com.sun.messaging.Queue("S_A");
			myQueue3 = new com.sun.messaging.Queue("EUR");
			myQueue4 = new com.sun.messaging.Queue("ASI");
			myQueue5 = new com.sun.messaging.Queue("OCE");
			myQueue6 = new com.sun.messaging.Queue("AFR");

			//Consumidores de mensajes dependiendo de la region 
			MessageConsumer myMsgConsumer = mySess.createConsumer(myQueue);
			MessageConsumer myMsgConsumer1 = mySess.createConsumer(myQueue1);
			MessageConsumer myMsgConsumer2 = mySess.createConsumer(myQueue2);
			MessageConsumer myMsgConsumer3 = mySess.createConsumer(myQueue3);
			MessageConsumer myMsgConsumer4 = mySess.createConsumer(myQueue4);
			MessageConsumer myMsgConsumer5 = mySess.createConsumer(myQueue5);
			MessageConsumer myMsgConsumer6 = mySess.createConsumer(myQueue6);

			myConn.start();



			Message msg = null;
			fraudes.clear();
			deteccion.clear();

			msg = myMsgConsumer.receive();
			TextMessage mensaje = (TextMessage) msg;
			Integer servidor = Integer.parseInt(mensaje.getText());

			//Division de los servidores por region 
			switch (servidor) {
			case 1: 
				infoTPV(myMsgConsumer1);
				System.out.println("Norte America:");
				break;
			case 2: 
				infoTPV(myMsgConsumer2);
				System.out.println("Sur America:");
				break;
			case 3: 
				infoTPV(myMsgConsumer3);
				System.out.println("Europa:");
				break;
			case 4:
				infoTPV(myMsgConsumer4);
				System.out.println("Asia:");
				break;
			case 5:
				infoTPV(myMsgConsumer5);
				System.out.println("Oceania:");
				break;

			case 6: 
				infoTPV(myMsgConsumer6);
				System.out.println("Africa:");
				break;
			}

			System.out.println(" ID "+" #Ataques");
			int numero = deteccion.size();
			for (int i = 0; i<10 && i<numero; i++) {
				ranking(deteccion);				
			}

			mySess.close();
			myConn.close();


		} catch (JMSException e) {
			e.printStackTrace();
		} 

	}


}
