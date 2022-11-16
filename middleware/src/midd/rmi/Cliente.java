package midd.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Cliente extends Remote {

	public String getNombre() throws RemoteException;
	public int getEdad() throws RemoteException;
	public Cuenta getCc() throws RemoteException;

}