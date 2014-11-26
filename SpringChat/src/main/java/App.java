
import java.rmi.RemoteException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import client.user.ChatUser;

public class App {
	
	public static void main(String[] args) throws RemoteException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
		ChatUser chat = (ChatUser) context.getBean("chatUserImpl");
		new Thread(chat).start();
		context.close();
	}

}
