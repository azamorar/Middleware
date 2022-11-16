package practicaRMI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map.Entry;
import java.util.*;

public class Bonificaciones extends UnicastRemoteObject implements Bonificacion , Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4489707234348408999L;

	public static ArrayList<String[]> transacciones; // lineas de fichero .txt
	public static Map <String,Integer> id_bonificado ; 	// id que reciben bonificacion
	public static Map <String, Pair> media_dia ;	// ids con sumatoria de transacciones y numero de transacciones
	public static Map<String, Integer> fechas;		// fechas
	public static Map<String, Double> medias;		// medias de cada id 

	public Bonificaciones() throws RemoteException{

		transacciones = new ArrayList<String[]>();
		id_bonificado = new HashMap<String, Integer>();
		media_dia = new HashMap<String, Pair>();
		fechas = new HashMap<String,Integer>();
		medias = new HashMap<String , Double>();

	}
	
	// Metodo para imprimir el ranking
	@Override
	public void ranking(Map<String, Integer> mapa) throws RemoteException {
		// TODO Auto-generated method stub

		Entry<String,Integer> valor;


		for (int i = 0; i < 10 ; i++) {
			valor = null;


			for (Entry<String,Integer> entrada : mapa.entrySet()) {
				if (valor == null || entrada.getValue().compareTo(valor.getValue()) > 0 ){
					valor = entrada;
				}
			}

			mapa.remove(valor.getKey());
			System.out.println( ""+valor.getKey()+"          "+valor.getValue()); 

		}

	}

	// Metodo para calcular las bonificaciones correspondientes de cada id
	@Override
	public void bonificaciones(ArrayList<String[]> transacciones ) throws RemoteException{


		Integer n, m;
		double media;
		double media_tpv = 0.0;
		
		for (int i = 0; i < transacciones.size(); i++) {
			if (!fechas.containsKey(transacciones.get(i)[0])) {
				fechas.put(transacciones.get(i)[0], 0);
			}
		}
		for (Entry<String,Integer> entrada : fechas.entrySet()) {


			for (String [] t : transacciones) {
				if (entrada.getKey().equals(t[0])) {
					if (!media_dia.containsKey(t[2])) {
						media_dia.put(t[2], new Pair(Integer.parseInt(t[3]), 1));

					}
					else {
						n = media_dia.get(t[2]).getRight();
						m = media_dia.get(t[2]).getLeft();
						media_dia.put(t[2], new Pair(Integer.parseInt(t[3]) + m , n+1));
					}

				}
			}

			for (Entry<String, Pair> entry : media_dia.entrySet()) {

				media = (double) ((double)entry.getValue().getLeft()/(double)entry.getValue().getRight());
				medias.put(entry.getKey(), media );
				


			}
			
			for (Entry<String, Double> entry : medias.entrySet()) {
				media_tpv += entry.getValue();
			}
						
			media_tpv = media_tpv/ medias.size();

			for (Entry<String, Double> entry : medias.entrySet()) {

				if (entry.getValue()>media_tpv) {
					if (!id_bonificado.containsKey(entry.getKey())) {
						id_bonificado.put(entry.getKey(),1);	

					}
					else {

						n = id_bonificado.get(entry.getKey());

						id_bonificado.put(entry.getKey(), n+1);
					}
				}

			}
			

			media_tpv = 0.0;
			media_dia.clear();
			medias.clear();

		}

		fechas.clear();




	}
	
	// Metodo para añadir las lineas de los ficheros .txt (transacciones) a la lista
	@Override
	public void addTransaccion(String [] t) throws RemoteException {

		//Transaccion x = new Transaccion (t[0], t[1], t[2], t[3]);
		transacciones.add(t);

	}
	
	// Metodo para devolver las transacciones 
	@Override
	public ArrayList<String[]> getTransacciones () throws RemoteException{

		return transacciones;
	}
	
	// Metodo para devolver el map de los id bonificados
	@Override
	public Map<String,Integer> getId_bonificado() throws RemoteException{

		return id_bonificado;
	}

	// Metodo para imprimir la cabecera 
	@Override
	public void cabereca() throws RemoteException {

		System.out.println("Ranking:");
		System.out.println(" id      Bonificacion");

	}

	// Metodo para borrar todas las transacciones de la lista
	@Override
	public void clearTransacciones() throws RemoteException {
		
		transacciones.clear();
		
	}




}

