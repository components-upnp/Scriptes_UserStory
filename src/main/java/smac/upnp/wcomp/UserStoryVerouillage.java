package smac.upnp.wcomp;

public class UserStoryVerouillage
{
    public static void main( String[] args )
    {
    	// Vérifier nom Container
        ContainerWComp c = new ContainerWComp("container1_Structural_0");
        
        try {
        	pause(3000);
        	//AndroidRemote
        	String androidRemote = c.createBeanAtPos("Android Remote Controller", "WComp.UPnPDevice.Android_Remote_Controller", 400, 200);

        	pause(3000);
        	//Android Slider
        	String androidSlider = c.createBeanAtPos("Android Remote Slider Controller", "WComp.UPnPDevice.Android_Remote_Slider_Controller", 300, 100);


        	pause(3000);
        	//Ampoule
        	String ampoule = c.createBeanAtPos("Ampoule (simulation)", "WComp.UPnPDevice.Ampoule__simulation_", 600, 300);

        	pause(1000);
			String linkRemote1 = c.createLink("Android Remote Controller", "Status_Event", "Ampoule (simulation)", "SetTarget", "");

			pause(1000);
			String linkRemote2 = c.createLink("Android Remote Slider Controller", "Status_Event", "Ampoule (simulation)", "SetValeur", "");

			pause(1000);

			ActionStoryVerouillage actionStoryVerouillage = new ActionStoryVerouillage();
			ActionStoryVerouillageRemote actionStoryVerouillageRemote = new ActionStoryVerouillageRemote();
			pause(5000);

			actionStoryVerouillage.setValeur("50");

			pause(2000);

			actionStoryVerouillage.setValeur("100");

			pause(2000);

			actionStoryVerouillageRemote.verouille();

			actionStoryVerouillage.setValeur("0");

			pause(2000);

			actionStoryVerouillage.setValeur("50");

			pause(2000);

			actionStoryVerouillageRemote.verouille();

			pause(2000);

			actionStoryVerouillage.setValeur("0");



        } catch (NoDevice e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (NoService e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLaunched e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorContainer e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
    public static void pause(long ms){
        try {
			Thread.sleep(ms);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    }
}