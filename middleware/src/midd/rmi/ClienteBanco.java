package midd.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClienteBanco extends UnicastRemoteObject implements Cliente, Serializable {

	private static final long serialVersionUID = -1464609396422651539L;
	private String nombre;
	private int edad;
	private Cuenta cc;
	
	public ClienteBanco(String nombre, int edad) throws RemoteException {
		super();
		try {
			this.nombre = nombre;
			this.edad = edad;
			this.cc = new CuentaCorriente();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Override
	public Cuenta getCc() {
		return cc;
	}

	public void setCc(Cuenta cc) {
		this.cc = cc;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if(obj instanceof ClienteBanco) {
			try {
				equals = this.getNombre().equals(((Cliente)obj).getNombre());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return equals;
	}

}
