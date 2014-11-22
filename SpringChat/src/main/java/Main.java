
import java.rmi.RemoteException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import client.ChatUserImpl;

public class Main {
	
	public static void main(String[] args) throws RemoteException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
		ChatUserImpl chat = (ChatUserImpl) context.getBean("chatUserImpl");
		new Thread(chat).start();
		context.close();
	}

}
