package client.controller;

import java.rmi.RemoteException;

import client.ui.IChatUI;
import client.user.IUser;
import server.IChatRoom;

public class ChatController implements IChatController {

	private IChatUI ui;
	private IUser user;
	private IChatRoom room;
	
	/* ------ Init du controller (creation des commandes) ------ */
	
	/* (non-Javadoc)
	 * @see client.controller.IChatController#initController()
	 */
	@Override
	public void initController() {
		ui.setPostMessageCmd(() -> handleSendMessageCmd());
		ui.setUnregisterCmd(() -> handleUnRegisterCmd());
		user.setGetPseudoCmd(() -> handleRequestPseudoCmd());
		user.setDisplayMsgCmd(() -> handleDisplayMsgCmd());
	}

	/* ------ Traitement de l'execution des commandes venant de l'UI ------ */
	
	private void handleSendMessageCmd() {
		try {
			room.postMessage(user.getPseudo(), ui.getMessage());
		} catch (RemoteException e) {
			System.err.println("Envoi du message impossible");
		}
	}

	private void handleUnRegisterCmd() {
		try {
			room.unsubscribe(user.getPseudo());
		} catch (RemoteException e) {
			System.err.println("Desinscription impossible");
		}
	}
	
	/* ------ Traitement de l'execution des commandes venant de l'User ------ */
	
	private void handleRequestPseudoCmd() {
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
	
	/* (non-Javadoc)
	 * @see client.controller.IChatController#setUi(client.ui.ChatUI)
	 */
	@Override
	public void setUi(IChatUI ui) {
		this.ui = ui;
	}

	/* (non-Javadoc)
	 * @see client.controller.IChatController#setUser(client.user.User)
	 */
	@Override
	public void setUser(IUser user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see client.controller.IChatController#setRoom(server.ChatRoom)
	 */
	@Override
	public void setRoom(IChatRoom room) {
		this.room = room;
	}

}
