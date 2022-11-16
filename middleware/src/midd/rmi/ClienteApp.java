package midd.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClassLoader;

public class ClienteApp {

	private static final String REMOTE = "127.0.0.1";

	public static void main(String[] args) {
		
		// Establezco la política de seguridad para usarla con el gestor
		System.setProperty("java.security.policy", "miBanco.policy");
		
		System.setProperty("java.rmi.server.codebase", "http://" + REMOTE + "/cliente/");

		// Indico que quiero usar codebases remotos
		System.setProperty("java.rmi.server.useCodebaseOnly", "false");
				
		try {
			
			// Se habilita el Security Manager para poder descargar clases
			if(System.getSecurityManager()==null){
				System.setSecurityManager(new SecurityManager());
			}
			
			Registry registro = LocateRegistry.getRegistry(REMOTE, Registry.REGISTRY_PORT);

			Banco miBanco = (Banco)registro.lookup("midd.rmi.banco_online");
			
			// Class cb = RMIClassLoader.loadClass("ClienteBanco");
			Cliente rafa = miBanco.addCliente(new CipherImp("Alice"), 35, null);
			System.out.println("Object client " + rafa);
			Cuenta ccRafa = rafa.getCc();
			ccRafa.ingresar(100000);
			System.out.println(rafa.getNombre() + " tiene " + ccRafa.getSaldo() + " € en la cuenta!");
			
			Cliente patri = miBanco.addCliente(new CipherImp("Bob"), 4, rafa);
			Cuenta ccPatri = patri.getCc();
			ccPatri.ingresar(1000);
			System.out.println(patri.getNombre() + " tiene " + ccPatri.getSaldo() + " € en la cuenta!");
			
			System.out.println("Lista de clientes del banco:");
			for(Cliente c: miBanco.getClientes()){
				System.out.println("\t"+ c.getNombre());
			}

		} catch (Exception e) {
			System.err.println("INFO: No puedo acceder al registro RMI o al banco remoto en la dirección " + REMOTE);
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
