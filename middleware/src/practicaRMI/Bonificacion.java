package practicaRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface Bonificacion extends Remote {
	
	public void bonificaciones(ArrayList<String[]> transacciones) throws RemoteException;
	public void cabereca() throws RemoteException;
	public void ranking(Map<String, Integer> mapa) throws RemoteException;
	public void addTransaccion(String [] t) throws RemoteException;
	public ArrayList<String[]> getTransacciones () throws RemoteException;
	public Map<String,Integer> getId_bonificado() throws RemoteException;
	public void clearTransacciones() throws RemoteException;
	
	

}
