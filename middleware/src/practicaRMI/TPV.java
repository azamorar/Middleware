package practicaRMI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TPV extends UnicastRemoteObject implements TPVinterface , Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8916287070674839034L;
	
	private File input;
	private String id;
	private String region;
	
	// Constructor de TPV
	public TPV(File input, String id, String region) throws RemoteException {
		this.input = input;
		this.id = id;
		this.region = region;
	}
	
	// Metodo que retorna el id
	public String getId() throws RemoteException {

		return id;
	}


	// Metodo para leer los ficheros .txt
	public void leer(Bonificacion bonificaciones) throws RemoteException {

		String line = "";
		String splitBy = " ";
		String info [];
		String num, continente;

		try {
			bonificaciones.getTransacciones().clear();
			BufferedReader br = new BufferedReader(new FileReader(input));


			while((line = br.readLine()) != null){
				info = line.split(splitBy);
				num = info[2].charAt(1) + "";
				continente = info[2].charAt(0)+"";

				if (id.equals(num) && region.equals(continente)) {
					bonificaciones.addTransaccion(info);

				}
			}
			
			br.close();
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} 

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Establezco la poltica de seguridad para usarla con el gestor
		System.setProperty("java.security.policy", "Bonificacion.policy");



		try {



			// Se habilita el Security Manager para poder descargar clases
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			Registry registro = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);

			Bonificacion bonificacion = (Bonificacion) registro.lookup("practicaRMI.servidor");

			// Creacion de los 26TPVs


			// Norte America
			TPV TPV1 = new TPV(new File("./ficheros/NORTH_AMERICA_TPV_1.txt"), "1", "1");
			TPV1.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV2 = new TPV(new File("./ficheros/NORTH_AMERICA_TPV_2.txt"), "2", "1");
			TPV2.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV3 = new TPV(new File("./ficheros/NORTH_AMERICA_TPV_3.txt"), "3", "1");
			TPV3.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV4 = new TPV(new File("./ficheros/NORTH_AMERICA_TPV_4.txt"), "4", "1");
			TPV4.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV5 = new TPV(new File("./ficheros/NORTH_AMERICA_TPV_5.txt"), "5", "1");
			TPV5.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();



			// Sur America 
			TPV TPV6 = new TPV(new File("./ficheros/SOUTH_AMERICA_TPV_1.txt"), "1", "2");
			TPV6.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV7 = new TPV(new File("./ficheros/SOUTH_AMERICA_TPV_2.txt"), "2", "2");
			TPV7.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV8 = new TPV(new File("./ficheros/SOUTH_AMERICA_TPV_3.txt"), "3", "2");
			TPV8.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV9 = new TPV(new File("./ficheros/SOUTH_AMERICA_TPV_4.txt"), "4", "2");
			TPV9.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();


			// Europa
			TPV TPV10 = new TPV(new File("./ficheros/EUROPE_TPV_1.txt"), "1", "3");
			TPV10.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV11 = new TPV(new File("./ficheros/EUROPE_TPV_2.txt"), "2", "3");
			TPV11.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV12 = new TPV(new File("./ficheros/EUROPE_TPV_3.txt"), "3", "3");
			TPV12.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV13 = new TPV(new File("./ficheros/EUROPE_TPV_4.txt"), "4", "3");
			TPV13.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();



			// Asia
			TPV TPV14 = new TPV(new File("./ficheros/ASIA_TPV_1.txt"), "1", "4");
			TPV14.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV15 = new TPV(new File("./ficheros/ASIA_TPV_2.txt"), "2", "4");
			TPV15.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV16 = new TPV(new File("./ficheros/ASIA_TPV_3.txt"), "3", "4");
			TPV16.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV17 = new TPV(new File("./ficheros/ASIA_TPV_4.txt"), "4", "4");
			TPV17.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV18 = new TPV(new File("./ficheros/ASIA_TPV_5.txt"), "5", "4");
			TPV18.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV19 = new TPV(new File("./ficheros/ASIA_TPV_6.txt"), "6", "4");
			TPV19.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();


			// Oceania
			TPV TPV20 = new TPV(new File("./ficheros/OCEANIA_TPV_1.txt"), "1", "5");
			TPV20.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV21 = new TPV(new File("./ficheros/OCEANIA_TPV_2.txt"), "2", "5");
			TPV21.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV22 = new TPV(new File("./ficheros/OCEANIA_TPV_3.txt"), "3", "5");
			TPV22.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV23 = new TPV(new File("./ficheros/OCEANIA_TPV_4.txt"), "4", "5");
			TPV23.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();



			// Africa
			TPV TPV24 = new TPV(new File("./ficheros/AFRICA_TPV_1.txt"), "1", "6");
			TPV24.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV25 = new TPV(new File("./ficheros/AFRICA_TPV_2.txt"), "2", "6");
			TPV25.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			TPV TPV26 = new TPV(new File("./ficheros/AFRICA_TPV_3.txt"), "3", "6");
			TPV26.leer(bonificacion);
			bonificacion.bonificaciones(bonificacion.getTransacciones());
			bonificacion.clearTransacciones();
			
			
			// Creacion del ranking
			bonificacion.cabereca();
			bonificacion.ranking(bonificacion.getId_bonificado());
				
			



		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}