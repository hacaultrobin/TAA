package aspects;

import java.rmi.RemoteException;

import javax.security.auth.login.LoginException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import server.IChatRoom;

@Aspect
public class LogAspect {
	
	private int nbLoginAttempts = 0;

	/* Pointcuts */
	
	@Pointcut("execution(* server.IChatRoom.authentification(..))")
	public void authentification() {};
	
	@Pointcut("execution(* server.IChatRoom.subscribe(..))")
	public void subscribe() {};
	
	@Pointcut("execution(* server.IChatRoom.unsubscribe(..))")
	public void unSubscribe() {};
	
	@Pointcut("execution(* server.IChatRoom.postMessage(..))")
	public void postMessage() {};


	/* Advices */
	
	@AfterThrowing(pointcut = "authentification()", throwing = "e")
	public void AfterThrowingLoginException(LoginException e) {
		nbLoginAttempts++;
		if (nbLoginAttempts < 3) {
			System.err.println("[LOG]\tIdentifiants incorrects");
		} else {
			System.err.println("[LOG]\tLogin impossible apres 3 essais - Au revoir !");
		}		
	}
	
	@Before("subscribe()")
	public void BeforeSubscribe(JoinPoint pjp){
		String pseudo = (String) pjp.getArgs()[1];
		IChatRoom room = (IChatRoom) pjp.getThis();
		try {
			room.logOnServer("[LOG]\tConnexion de " + pseudo);
		} catch (RemoteException e) {}
	}
	
	@Before("unSubscribe()")
	public void BeforeUnsubscribe(JoinPoint pjp){
		String pseudo = (String) pjp.getArgs()[0];
		IChatRoom room = (IChatRoom) pjp.getThis();
		try {
			room.logOnServer("[LOG]\tDeconnexion de " + pseudo);
		} catch (RemoteException e) {}
	}
	
	@Before("postMessage()")
	public void BeforePostMessage(JoinPoint pjp){
		String pseudo = (String) pjp.getArgs()[0];
		String message = (String) pjp.getArgs()[1];
		IChatRoom room = (IChatRoom) pjp.getThis();
		try {
			room.logOnServer("[LOG]\t" + pseudo + " >> " + message);
		} catch (RemoteException e) {}
	}

	@AfterThrowing(pointcut = "unSubscribe()", throwing = "e")
	public void AfterThrowingUnSubscribe(RemoteException e){
		System.err.println("[LOG]\tDesinscription impossible");
	}
	
	@AfterThrowing(pointcut = "postMessage()", throwing = "e")
	public void AfterThrowingPostMessage(RemoteException e){
		System.err.println("[LOG]\tEnvoi du message impossible"); 
	}

}