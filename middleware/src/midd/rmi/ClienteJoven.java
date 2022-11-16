package midd.rmi;

import java.rmi.RemoteException;

public class ClienteJoven extends ClienteBanco{

	private static final long serialVersionUID = 3131731404439393026L;
	private Cliente tutor;
	
	public ClienteJoven(String nombre, int edad, Cliente tutor) throws RemoteException {
		super(nombre, edad);
		this.tutor = tutor;
	}
	
	public Cliente getTutor() {
		return tutor;
	}

}
