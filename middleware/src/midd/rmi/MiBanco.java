package midd.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class MiBanco extends UnicastRemoteObject implements Banco {

	private static final long serialVersionUID = -1740227210084638053L;
	private List<Cliente> clientes;
	
	
	protected MiBanco() throws RemoteException {
		super();
		this.clientes = new ArrayList<Cliente>();
	}

	@Override
	public Cliente addCliente(Cipher nom, int edad, Cliente tutor) throws RemoteException {
		
		Cliente c;

		String nombre = nom.getName();
		if (edad>=18) {
			c = new ClienteBanco(nombre, edad);
		} else {
			c = new ClienteJoven(nombre, edad, tutor);
		}
		
		if(!clientes.contains(c)){
			clientes.add(c);			
			System.out.println("INFO: Añadido cliente nuevo: " + c.getNombre());
		} else {
			System.err.println("INFO: El usuario " + c.getNombre() + " ya existe.");
			c = getCliente(nombre);
		}
		
		return c;
	}


	private Cliente getCliente(String nombre) throws RemoteException{
		
		Cliente buscado = null;
		
		for(Cliente c: this.clientes){
			if(nombre.equals(c.getNombre())){
				buscado = c;
				break;
			}
		}
		return buscado;
	}
	
	
	@Override
	public List<Cliente> getClientes() throws RemoteException {
		return clientes;
	}

}
