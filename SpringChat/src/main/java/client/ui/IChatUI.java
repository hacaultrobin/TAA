package client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import client.command.ICommand;

public interface IChatUI {

	public abstract void window_windowClosing(WindowEvent e);

	public abstract void btnSend_actionPerformed(ActionEvent e);

	public abstract void displayMessage(String message);

	public abstract String requestPseudo();

	public abstract String getMessage();

	public abstract void setUnregisterCmd(ICommand unregister);

	public abstract void setPostMessageCmd(ICommand postMessage);

}