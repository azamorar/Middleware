package practicaRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Gasto extends Remote {
	
	
	public String getFecha() throws RemoteException;
	
	public String getHora() throws RemoteException;
	
	public String getId() throws RemoteException;
	
	public String getMonto() throws RemoteException;
	

}
