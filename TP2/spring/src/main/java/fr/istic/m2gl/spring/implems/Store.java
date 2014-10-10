package fr.istic.m2gl.spring.implems;

import fr.istic.m2gl.spring.interfaces.IBank;
import fr.istic.m2gl.spring.interfaces.IFastLane;
import fr.istic.m2gl.spring.interfaces.IJustHaveALook;
import fr.istic.m2gl.spring.interfaces.ILane;
import fr.istic.m2gl.spring.interfaces.IProvider;
import fr.istic.m2gl.spring.interfaces.IStore;

public class Store implements IFastLane, IJustHaveALook, ILane, IStore {

	IProvider provider;
	IBank bank;
	public IProvider getProvider() {
		return provider;
	}
	public void setProvider(IProvider provider) {
		this.provider = provider;
	}
	public IBank getBank() {
		return bank;
	}
	public void setBank(IBank bank) {
		this.bank = bank;
	}
	public boolean hasProduct(String string) {

			return true;
	}
	public void buy(String string) {
		bank.createTransaction(25, "c1","c2");
		provider.send(string, "adresse");
	}
}
