import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.istic.m2gl.spring.interfaces.IClient;


public class Main {
	public static void main(String[] args) throws Exception {
		
		/* Init Spring Default Config */
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
		IClient client = (IClient) context.getBean("client");
		client.startBuy();
//		engine.run();

	}

}
