package practicaRMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Servidor {




	public static void main(String[] args) {


		// Añado la política de seguridad que permita leer las clases del codebase
		System.setProperty("java.security.policy", "Bonificacion.policy");


		Registry registro;


		try {


			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			registro = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			registro.rebind("practicaRMI.servidor", new Bonificaciones() );

		} catch (RemoteException e) {
			e.printStackTrace();
		}


	}

}