package midd.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CuentaCorriente extends UnicastRemoteObject implements Cuenta, Serializable {

	private static final long serialVersionUID = -3018765733270736090L;
	private int saldo;
	
	protected CuentaCorriente() throws RemoteException {
		super();
		this.saldo = 0;
	}

	@Override
	public int ingresar(int cantidad) throws RemoteException {
		this.saldo += cantidad;
		return this.saldo;
	}

	@Override
	public int getSaldo() throws RemoteException {
		return this.saldo;
	}

}
