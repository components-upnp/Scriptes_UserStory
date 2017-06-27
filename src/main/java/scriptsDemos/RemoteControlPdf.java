package scriptsDemos;

import smac.upnp.wcomp.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mkostiuk on 22/05/2017.
 */
public class RemoteControlPdf {

    public static void main(String[] args) {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                connect();
            }
        }, 3000, 20000);

    }

    public static void connect() {
        String namePdf = "";
        String nameAdapter = "";
        int nb = 1;
        // Vérifier nom Container
        ContainerWComp c = new ContainerWComp("container_Structural_0");

        try {

            String[] beans = c.getBeans();

            for (String bean : beans) {

                String beanProperties[] = bean.split(":");
                if (bean.contains("lecteur_PDF")) {
                    namePdf = beanProperties[0];
                }
            }

            for (String bean : beans) {


                String beanProperties[] = bean.split(":");
                if (bean.contains("android_Remote_Controller")) {


                    //Creation du lien
                    String linkPoto = c.createLink(beanProperties[0], "Status_Event", namePdf, "SetCommande", "");
                    pause(1000);
                }

                if (bean.contains(("Accelerometer"))) {
                    String linkCommande = c.createLink(beanProperties[0], "Direction_Event", namePdf, "SetCommande", "");
                }

                if (bean.contains("Arduino_Adapter")) {
                    nameAdapter = beanProperties[0];
                    String linkCommand = c.createLink(nameAdapter, "AdaptedCommand_Event", namePdf, "SetCommande", "");
                }

                if (bean.contains("BUTTONS")) {
                    String linkCommand = c.createLink(beanProperties[0], "Status_Event", nameAdapter, "SetArduinoCommand", "");
                }

            }



            pause(3000);
            c.stopSpy();
        } catch (NoService noService) {
            noService.printStackTrace();
        } catch (NotLaunched notLaunched) {
            notLaunched.printStackTrace();
        } catch (SpyNotRunning spyNotRunning) {
            spyNotRunning.printStackTrace();
        } catch (NoDevice noDevice) {
            noDevice.printStackTrace();
        } catch (ErrorContainer errorContainer) {
            errorContainer.printStackTrace();
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
