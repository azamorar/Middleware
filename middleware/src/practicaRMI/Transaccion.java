package practicaRMI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Transaccion extends UnicastRemoteObject implements Serializable, Gasto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6107470373411153362L;


	String fecha;
	String hora;
	String id;
	String monto;

	public Transaccion (String fecha, String hora, String id, String monto ) throws RemoteException {

		this.fecha = fecha;
		this.hora = hora;
		this.id = id;
		this.monto = monto;

	}

	public String getFecha() throws RemoteException{

		return fecha;
	}

	public String getHora() throws RemoteException{

		return hora;
	}

	public String getId()throws RemoteException {

		return id;
	}

	public String getMonto() throws RemoteException{

		return monto;
	}




}
