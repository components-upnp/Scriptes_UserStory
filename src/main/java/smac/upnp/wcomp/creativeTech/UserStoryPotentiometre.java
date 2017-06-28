package main.java.smac.upnp.wcomp.creativeTech;


import main.java.smac.upnp.wcomp.*;

/**
 * Created by IDA on 28/03/2017.
 */
public class UserStoryPotentiometre {

    public static void main( String[] args ) {
        // Vérifier nom Container
        ContainerWComp c = new ContainerWComp(args[0]);


        try {
            pause(3000);
            //Ampoule
            String ampoule = c.createBeanAtPos("Ampoule (simulation)", "WComp.UPnPDevice.Ampoule__simulation_", 600, 300);
            pause(1000);

            //Potentiometre
            String poto = c.createBeanAtPos("POTO", "WComp.UPnPDevice.POTO", 700, 400);
            pause(1000);

            //Creation du lien
            String linkPoto = c.createLink("POTO", "Status_Event", "Ampoule (simulation)", "SetValeur", "");
            pause(1000);

            ActionStoryPoto actionStoryPoto = new ActionStoryPoto();
            pause(5000);


            actionStoryPoto.setValeur("50");
            pause(2000);

            actionStoryPoto.setValeur("0");
            pause(2000);

            actionStoryPoto.setValeur("100");
            pause(2000);

        } catch (NoDevice noDevice) {
            noDevice.printStackTrace();
        } catch (ErrorContainer errorContainer) {
            errorContainer.printStackTrace();
        } catch (NoService noService) {
            noService.printStackTrace();
        } catch (NotLaunched notLaunched) {
            notLaunched.printStackTrace();
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
