package fr.istic.m2gl.spring.implems;

import fr.istic.m2gl.spring.interfaces.IClient;
import fr.istic.m2gl.spring.interfaces.IFastLane;
import fr.istic.m2gl.spring.interfaces.IJustHaveALook;
import fr.istic.m2gl.spring.interfaces.ILane;
import fr.istic.m2gl.spring.interfaces.IRun;


public class Client implements IClient, IRun {

	
	IFastLane lane;
	IJustHaveALook have;
	ILane ilane;
	public IFastLane getLane() {
		return lane;
	}
	public void setLane(IFastLane lane) {
		this.lane = lane;
	}
	public IJustHaveALook getHave() {
		return have;
	}
	public void setHave(IJustHaveALook have) {
		this.have = have;
	}
	public ILane getIlane() {
		return ilane;
	}
	public void setIlane(ILane ilane) {
		this.ilane = ilane;
	}
	public void startBuy() {
		if (have.hasProduct("toto"))
			ilane.buy("toto");
		
		
	}
}
