package main.java.smac.upnp.wcomp;

public class AlreadyRunning extends Exception{

	public AlreadyRunning(){
		super("The spy is already running");
	}
}
