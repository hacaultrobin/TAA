package client.user;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.command.ICommand;

public interface IChatUser extends Remote {
	
    public void displayMessage(String message) throws RemoteException;
    
}