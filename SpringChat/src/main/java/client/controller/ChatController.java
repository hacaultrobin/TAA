package client.controller;

import java.rmi.RemoteException;

import server.IChatRoom;
import client.ui.IChatUI;
import client.user.IUser;

public class ChatController implements IChatController {

	private IChatUI ui;
	private IUser user;
	private IChatRoom room;
	
	/* ------ Init du controller (creation des commandes) ------ */
	
	@Override
	public void initController() {
		ui.setPostMessageCmd(() -> handleSendMessageCmd());
		ui.setUnregisterCmd(() -> handleUnRegisterCmd());
		user.setGetPseudoCmd(() -> handleRequestPseudoCmd());
		user.setDisplayMsgCmd(() -> handleDisplayMsgCmd());
	}

	/* ------ Traitement de l'execution des commandes venant de l'UI ------ */
	
	public void handleSendMessageCmd(){
		try {
			room.postMessage(user.getPseudo(), ui.getMessage());
		} catch (RemoteException e) {}
	}

	private void handleUnRegisterCmd(){
		try {
			room.unsubscribe(user.getPseudo());
		} catch (RemoteException e) {}
	}
	
	/* ------ Traitement de l'execution des commandes venant de l'User ------ */
	
	private void handleRequestPseudoCmd(){
		String pseudo = ui.requestPseudo();
		if (pseudo == null) {
			System.exit(0);
		} else {
			user.setPseudo(pseudo);
		}
	}
	
	private void handleDisplayMsgCmd() {
		ui.displayMessage(user.getDisplayMsg());
	}

	/* ------ Setters ------ */
	
	@Override
	public void setUi(IChatUI ui) {
		this.ui = ui;
	}

	@Override
	public void setUser(IUser user) {
		this.user = user;
	}

	@Override
	public void setRoom(IChatRoom room) {
		this.room = room;
	}

}
