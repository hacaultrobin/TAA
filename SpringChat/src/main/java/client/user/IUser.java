package client.user;

import client.command.ICommand;

public interface IUser {
		
	public String getPseudo(); 

	public void setPseudo(String pseudo);

	public String getDisplayMsg();

	public void setGetPseudoCmd(ICommand getPseudoCmd);

	public void setDisplayMsgCmd(ICommand displayMsgCmd);
}
 