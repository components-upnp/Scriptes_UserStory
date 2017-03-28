package smac.upnp.wcomp.creativeTechSP3;

import smac.upnp.wcomp.*;
import smac.upnp.wcomp.creativeTech.ActionStoryVerouillage;
import smac.upnp.wcomp.creativeTech.ActionStoryVerouillageRemote;

/**
 * Created by Abdourahamane Ly on 27/03/2017.
 */
public class UserStoryAdaptationLumineuse {
    public static void main( String[] args )
    {
        // Vérifier nom Container
        ContainerWComp containerWComp = new ContainerWComp("MyContainer_Structural_0");

        try {
            pause(2000);
            //Detecteur de luminosité
            String lumiere = containerWComp.createBeanAtPos("LUMI","WComp.UPnPDevice.LUMI",400, 200);

            pause(3000);
            //la lumière
            String ampoule = containerWComp.createBeanAtPos("Ampoule (simulation)", "WComp.UPnPDevice.Ampoule__simulation_", 600, 300);

            pause(1000);
            String linkRemote1 = containerWComp.createLink("LUMI", "Status_Event", "Ampoule (simulation)", "SetTarget", "");

            ActionAdaptationLumineuse actionAdaptationLumineuse = new ActionAdaptationLumineuse();

            actionAdaptationLumineuse.adapterLumiere("50");

            pause(2000);
            actionAdaptationLumineuse.adapterLumiere("0");

            pause(2000);
            actionAdaptationLumineuse.adapterLumiere("40");

            pause(2000);
            actionAdaptationLumineuse.adapterLumiere("80");

            pause(2000);
            actionAdaptationLumineuse.adapterLumiere("100");

            pause(2000);
            actionAdaptationLumineuse.adapterLumiere("10");

            pause(2000);
            actionAdaptationLumineuse.adapterLumiere("60");
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
