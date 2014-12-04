package aspects;

import java.util.HashMap;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import server.IChatRoom;
import client.model.SampleLoginModule;
import client.user.IUser;

import com.sun.security.auth.callback.DialogCallbackHandler;

@Aspect
public class AuthAspect {

	/* PointCuts */
	
	@Pointcut("execution(* client.user.IUser.setDisplayMsgCmd(..))")
	public void setDisplayMsgCmd() {};
	
	
	/* Advices */
	
	@After("setDisplayMsgCmd()")
	public void tryLogin(JoinPoint pjp) {
		IUser user = (IUser) pjp.getThis();
		IChatRoom room = user.getRoom();
		SampleLoginModule lc = null;
		lc = new SampleLoginModule(room);
		lc.initialize(new Subject(), new DialogCallbackHandler(), null, new HashMap());
		// did they fail three times?
		if (!tryLogin(lc)) {			
			System.exit(-1);
		}
	}
	
	private boolean tryLogin(SampleLoginModule lc) {
		// the user has 3 attempts to authenticate successfully
		boolean loginOk = false;
		for (int i = 0; i < 3 && !loginOk; i++) {
			try {
				// attempt authentication
				lc.login();
				loginOk = true;
			} catch (LoginException le) {}
		}
		return loginOk;
	}
	
}
