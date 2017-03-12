package smac.upnp.wcomp;

public class App 
{
    public static void main( String[] args )
    {
    	// Vérifier nom Container
        ContainerWComp c = new ContainerWComp("args[0]");
        
        try {
        	pause(3000);
        	//AndroidRemote
        	String androidRemote = c.createBeanAtPos("Android Remote Controller", "urn:schemas-upnp-org:device:RemoteController:1", 400, 200);

        	pause(3000);
        	//Android Slider
        	String androidSlider = c.createBeanAtPos("Android Remote Slider Controller", "urn:schemas-upnp-org:device:RemoteSliderController:1", 300, 100);


        	pause(3000);
        	//Ampoule
        	String ampoule = c.createBeanAtPos("Ampoule (simulation)", "urn:schemas-upnp-org:device:Ampoule:1", 600, 300);

        	pause(1000);
			String linkRemote1 = c.createLink("Android Remote Controller", "Status_Event", "Ampoule (simulation)", "SetTarget", "");

			pause(1000);
			String linkRemote2 = c.createLink("Android Remote Slider Controller", "Status_Event", "Ampoule (simulation)", "SetValeur", "");

			pause(1000);

			ActionStoryVerouillage actionStoryVerouillage = new ActionStoryVerouillage();

			actionStoryVerouillage.SetValeur("50");

			pause(2000);

			actionStoryVerouillage.SetValeur("100");

			pause(2000);

			actionStoryVerouillage.verouille();

			actionStoryVerouillage.SetValeur("0");

			pause(2000);

			actionStoryVerouillage.SetValeur("50");

			pause(2000);

			actionStoryVerouillage.verouille();

			pause(2000);

			actionStoryVerouillage.SetValeur("0");



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
		} catch (SpyNotRunning e) {
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