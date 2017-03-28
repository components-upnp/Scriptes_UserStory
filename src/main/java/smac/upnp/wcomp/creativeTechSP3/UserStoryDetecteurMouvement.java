package smac.upnp.wcomp.creativeTechSP3;

import smac.upnp.wcomp.*;

/**
 * Created by Abdourahamane Ly on 27/03/2017.
 */
public class UserStoryDetecteurMouvement {
    public static void main( String[] args )
    {
        // Vérifier nom Container
        ContainerWComp containerWComp = new ContainerWComp("MyContainer_Structural_0");

        try {
            pause(2000);
            //Detecteur de mouvement
            String lumiere = containerWComp.createBeanAtPos("Motion Detector","WComp.UPnPDevice.Motion_Detector",400, 200);

            pause(3000);
            //la lumière
            String ampoule = containerWComp.createBeanAtPos("Ampoule (simulation)", "WComp.UPnPDevice.Ampoule__simulation_", 600, 300);

            pause(1000);
            String link = containerWComp.createLink("Motion Detector", "Status_Event", "Ampoule (simulation)", "SetValeur", "");

            ActionDetecterMouvement actionDetecterMouvement = new ActionDetecterMouvement();

            pause(2000);
            actionDetecterMouvement.detecterMouvement("0");

            pause(2000);
            actionDetecterMouvement.detecterMouvement("50");

            pause(2000);
            actionDetecterMouvement.detecterMouvement("69");

            pause(2000);
            actionDetecterMouvement.detecterMouvement("12");

            pause(2000);
            actionDetecterMouvement.detecterMouvement("80");

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
