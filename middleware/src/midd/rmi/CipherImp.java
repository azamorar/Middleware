package midd.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;

public class CipherImp implements Cipher, Serializable {
	
    private String name;

    public CipherImp(String name) throws RemoteException{
	this.name = name;

    }

    public String getName() throws RemoteException {
	return name;
	
    }

}
