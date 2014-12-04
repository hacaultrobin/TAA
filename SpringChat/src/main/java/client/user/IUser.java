package client.user;

import server.IChatRoom;
import client.command.ICommand;

public interface IUser {
		
	public String getPseudo(); 

	public void setPseudo(String pseudo);

	public String getDisplayMsg();
	
	public IChatRoom getRoom();
	
	public void setRoom(IChatRoom room);

	public void setGetPseudoCmd(ICommand getPseudoCmd);
	
	public void setDisplayMsgCmd(ICommand displayMsgCmd);
}
 