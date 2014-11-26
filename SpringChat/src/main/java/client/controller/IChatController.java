package client.controller;

import server.IChatRoom;
import client.ui.IChatUI;
import client.user.IUser;

public interface IChatController {

	public abstract void initController();

	public abstract void setUi(IChatUI ui);

	public abstract void setUser(IUser user);

	public abstract void setRoom(IChatRoom room);

}