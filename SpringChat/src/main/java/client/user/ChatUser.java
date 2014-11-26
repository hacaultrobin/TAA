package client.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import server.IChatRoom;
import client.command.ICommand;
import client.model.SampleLoginModule;

import com.sun.security.auth.callback.DialogCallbackHandler;

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
			SampleLoginModule lc = null;
			try {
				lc = new SampleLoginModule(room);
				//Set principals = new HashSet();
				//principals
				//		.add(new sample.principal.SamplePrincipal("testUser"));
				//Subject mySubject = new Subject(false, principals,
				//		new HashSet(), new HashSet());

				lc.initialize(new Subject(), new DialogCallbackHandler(), null, new HashMap());

			} catch (SecurityException se) {
				System.err.println("Cannot create LoginContext. "
						+ se.getMessage());
				System.exit(-1);
			}

			// the user has 3 attempts to authenticate successfully
			int i;
			for (i = 0; i < 3; i++) {
				try {

					// attempt authentication
					lc.login();

					// if we return with no exception, authentication succeeded
					break;

				} catch (LoginException le) {

					System.err.println("Authentication failed:");
					System.err.println("  " + le.getMessage());
					try {
						Thread.currentThread().sleep(3000);
					} catch (Exception e) {
						// ignore
					}

				}
			}

			// did they fail three times?
			if (i == 3) {
				System.out.println("Sorry");

				System.exit(-1);
			}

			System.out.println("Authentication succeeded!");
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

	public void setRoom(IChatRoom room) {
		this.room = room;
	}

}