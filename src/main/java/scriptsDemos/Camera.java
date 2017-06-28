package scriptsDemos;

import smac.upnp.wcomp.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mkostiuk on 31/05/2017.
 */
public class Camera {

    public static void main(String[] args) {
        //Mise en place d'un Timer oour relancer le script d'assemblage toutes les minutes
        Timer timer = new Timer();

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        connect();
                    }
                },
                3000,
                20000
        );

    }

    public static void connect() {
        ContainerWComp c = new ContainerWComp("container_Structural_0");
        String nameAdapter = "";

        try {
            String[] beans = c.getBeans();

            for (String bean : beans) {

                String beanProperties[] = bean.split(":");
                if (bean.contains("Adapter")) {
                    nameAdapter = beanProperties[0];

                    String linkFloatX = c.createLink(beanProperties[0], "X_Event", "stringToFloat1", "convert", "");
                    String linkFloatY = c.createLink(beanProperties[0], "Y_Event", "stringToFloat2", "convert", "");

                    String linkMoveGenVector2D = c.createLink(beanProperties[0], "Move_Event", "oNVIF_ptz_Vector2DFactoryProxy2", "Create_Vector2D_0", "");
                    String linkMoveGenPtzVEctor = c.createLink(beanProperties[0], "Move_Event", "oNVIF_ptz_PTZVectorFactoryProxy1", "Create_PTZVector_0", "");
                    String linkMoveCallMove = c.createLink(beanProperties[0], "Move_Event", "pTZ_RelativeMove_CallProxy1", "PTZ_RelativeMove_Trigger", "");
                }
            }

            for (String bean : beans) {


                String beanProperties[] = bean.split(":");
                if (bean.contains("android_Remote_Controller")) {
                    //Ampoule
                    String ampoule = c.createBeanAtPos(beanProperties[0], beanProperties[1], 600, 300);
                    pause(1000);

                    //Creation du lien
                    String linkPoto = c.createLink(beanProperties[0], "Status_Event", nameAdapter, "SetCommande", "");
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
