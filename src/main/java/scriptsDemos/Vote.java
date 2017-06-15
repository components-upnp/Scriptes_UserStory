package scriptsDemos;

import smac.upnp.wcomp.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mkostiuk on 23/05/2017.
 */
public class Vote {

    private static ArrayList<String> beansConnected = new ArrayList<>();

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
                60000
        );
    }

    public static void connect() {

        // Vérifier nom Container
        ContainerWComp c = new ContainerWComp("container_Structural_0");
        String namePollingStation = "";

        try {

            String[] beans = c.getBeans();

            for (String bean : beans) {

               // if (!beansConnected.contains(bean) || beansConnected.isEmpty()) {
                    beansConnected.add(bean);
                    String beanProperties[] = bean.split(":");
                    if ((bean.contains("Polling_Station")) && !(bean.contains("IHM"))) {
                        namePollingStation = beanProperties[0];
                    }
                //}
            }

            for (String bean : beans) {

              //  if (!beansConnected.contains(bean) || beansConnected.isEmpty()) {
                    String beanProperties[] = bean.split(":");
                    if (bean.contains("Android_Remote_Controller")) {
                        String ampoule = c.createBeanAtPos(beanProperties[0], beanProperties[1], 600, 300);
                        pause(1000);

                        //Creation du lien
                        String linkCommande = c.createLink(beanProperties[0], "Status_Event", namePollingStation, "_upnporgIdVoteService_SetCommande", "");
                        pause(1000);

                    }
                    if (bean.contains("Vote_Remote_Control")) {
                        //Ampoule
                        String ampoule = c.createBeanAtPos(beanProperties[0], beanProperties[1], 600, 300);
                        pause(1000);

                        //Creation du lien
                        String linkCommande = c.createLink(beanProperties[0], "Commande_Event", namePollingStation, "_upnporgIdVoteService_SetCommande", "");
                        pause(1000);

                        String linkQuestion = c.createLink(namePollingStation, "Question_Event", beanProperties[0], "questionNotif", "");

                    }

                    if (bean.contains("Accelerometer")) {
                        //Ampoule
                        String ampoule = c.createBeanAtPos(beanProperties[0], beanProperties[1], 600, 300);
                        pause(1000);

                        //Creation du lien
                        String linkCommande = c.createLink(beanProperties[0], "Direction_Event", namePollingStation, "_upnporgIdVoteService_SetCommande", "");
                        pause(1000);
                        String linkUdn = c.createLink(beanProperties[0], "Udn_Event", namePollingStation, "_upnporgIdVoteService_SetCommande", "");
                        pause(1000);
                        String linkPoto = c.createLink(namePollingStation, "Question_Event", beanProperties[0], "questionNotif", "");
                        pause(1000);
                    }

                    if (bean.contains("IHM_Polling_Station")) {
                        String ampoule = c.createBeanAtPos(beanProperties[0], beanProperties[1], 600, 300);
                        pause(1000);

                        String linkCommande = c.createLink(beanProperties[0], "CommandeToSend_Event", namePollingStation, "SetCommande", "");
                        pause(1000);

                        String linkQuestion = c.createLink(beanProperties[0], "QuestionToSend_Event", namePollingStation, "SetQuestion", "");
                        pause(1000);
                    }

                    if (bean.contains("Generateur_de_rapport")) {
                        String ampoule = c.createBeanAtPos(beanProperties[0], beanProperties[1], 600, 300);
                        pause(1000);

                        String link = c.createLink(namePollingStation, "Reponses_Event", beanProperties[0], "SetVotes", "");
                    }
                    beansConnected.add(bean);
               // }
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
