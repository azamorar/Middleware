package midd.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Cuenta extends Remote {
	
	public int ingresar(int cantidad) throws RemoteException;
	public int getSaldo() throws RemoteException;

}
