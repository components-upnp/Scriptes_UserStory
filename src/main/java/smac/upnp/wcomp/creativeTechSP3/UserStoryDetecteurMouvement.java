package smac.upnp.wcomp.creativeTechSP3;

import smac.upnp.wcomp.*;

/**
 * Created by Abdourahamane Ly on 27/03/2017.
 */
public class UserStoryDetecteurMouvement {
    public static void main( String[] args )
    {
        // Vérifier nom Container
        ContainerWComp containerWComp = new ContainerWComp(args[0]);

        try {
            pause(2000);
            //Detecteur de mouvement
            String lumiere = containerWComp.createBeanAtPos("Motion Dector","WComp.UPnPDevice.motion_Detector1",400, 200);

            pause(3000);
            //la lumière
            String ampoule = containerWComp.createBeanAtPos("Ampoule (simulation)", "WComp.UPnPDevice.Ampoule__simulation_", 600, 300);

            pause(1000);
            String linkRemote1 = containerWComp.createLink("Motion Detector", "Status_Event", "Ampoule (simulation)", "SetTarget", "");

            ActionDetecterMouvement actionAdaptationLumineuse = new ActionDetecterMouvement();

            actionAdaptationLumineuse.adapterLumiere();

            containerWComp.stopSpy();
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
        } catch (SpyNotRunning spyNotRunning) {
            spyNotRunning.printStackTrace();
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
