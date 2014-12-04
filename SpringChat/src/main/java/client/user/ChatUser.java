package client.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.IChatRoom;
import client.command.ICommand;

public class ChatUser extends UnicastRemoteObject implements IChatUser, Runnable, IUser {

	private static final long serialVersionUID = 1L;

	private IChatRoom room = null;	

	private ICommand getPseudoCmd;
	private ICommand displayMsgCmd;
	private String displayMsg;

	private String pseudo = null;

	public ChatUser() throws RemoteException {
		super(); // Appel au constructeur de UnicastRemoteObject
	}

	public void displayMessage(String message) throws RemoteException {
		if (displayMsgCmd != null) {
			displayMsg = message;
			displayMsgCmd.execute();
		}
	}
	
	public void run() {
		try {
			if (getPseudoCmd != null) {
				getPseudoCmd.execute();
			}
			this.room.subscribe(this, this.pseudo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/* ------ Setters commandes ------ */

	public void setGetPseudoCmd(ICommand getPseudoCmd) {
		this.getPseudoCmd = getPseudoCmd;
	}

	public void setDisplayMsgCmd(ICommand displayMsgCmd) {
		this.displayMsgCmd = displayMsgCmd;
	}

	/* ------ Getters et Setters ------ */

	public String getDisplayMsg() {
		return displayMsg;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public IChatRoom getRoom () {
		return this.room;
	}

	public void setRoom(IChatRoom room) {
		this.room = room;
	}

}