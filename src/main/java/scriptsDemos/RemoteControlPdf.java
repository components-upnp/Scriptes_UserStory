package scriptsDemos;

import smac.upnp.wcomp.*;

/**
 * Created by mkostiuk on 22/05/2017.
 */
public class RemoteControlPdf {

    public static void main(String[] args) {

        String namePdf = "";
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
                    //Ampoule
                    String ampoule = c.createBeanAtPos(beanProperties[0], beanProperties[1], 600, 300);
                    pause(1000);

                    //Creation du lien
                    String linkPoto = c.createLink(beanProperties[0], "Status_Event", namePdf, "SetTarget", "");
                    pause(1000);
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
