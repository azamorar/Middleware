package midd.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

	// IP local  donde ejecuta el servidor
	private static final String LOCALHOST = "127.0.0.1";
	
	public static void main(String[] args) throws RemoteException {
		
		// Indico la IP donde se deben exportar los objetos remotos
		System.setProperty("java.rmi.server.hostname", LOCALHOST);

		// Indico desde donde se pueden descargar las clases que necesiten los
		// clientes: ClienteJoven, ClienteBanco, etc...
		System.setProperty("java.rmi.server.codebase", "http://" + LOCALHOST + "/servidor/");

		// Añado la política de seguridad que permita leer las clases del codebase
		System.setProperty("java.security.policy", "miBanco.policy");

		// Indico que quiero usar codebases remotos
		System.setProperty("java.rmi.server.useCodebaseOnly", "false");

		try {
			// Se habilita el Security Manager para poder descargar clases
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			Registry registro = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			registro.rebind("midd.rmi.banco_online", new MiBanco());
			System.out.println("INFO: Servidor de objetos en marcha en IP "+ LOCALHOST + "...");

		} catch (RemoteException e) {
			System.err.println("INFO: Error al crear el registro o el banco remoto...");
		}
	}

}
