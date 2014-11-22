package client.controller;

import java.rmi.RemoteException;

import client.ChatUI;
import client.User;
import server.ChatRoom;


public class CommandePost implements Commande {

	public CommandePost(ChatRoom room) {
		this.room = room;
	}

	ChatRoom room = null;
	User user = null;
	ChatUI ui = null;

	public void execute() {
		try {
			room.postMessage(user.getPseudo(), ui.getMessage());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void setUser(User user) {
		this.user = user;
	}


	public void setUI(ChatUI ui2) {
		this.ui=ui2;
	}

}
