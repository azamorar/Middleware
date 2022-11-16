package practicaRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TPVinterface extends Remote {
	
	public void leer(Bonificacion bonificaciones) throws RemoteException;
	
	public String getId() throws RemoteException;

}
