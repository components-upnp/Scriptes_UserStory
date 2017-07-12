package main.java.scriptsDemos;

import main.java.smac.upnp.wcomp.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mkostiuk on 11/07/2017.
 */
public class MultiplePdf implements Runnable {

    private HashMap<String, String> connexions = new HashMap<>();
    private String master = "";
    private HashMap<String, String> remotes = new HashMap<>();
    private ArrayList<String> connectedToMaster = new ArrayList<>();


    @Override
    public void run() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    connect();
                } catch (NoDevice noDevice) {
                    noDevice.printStackTrace();
                } catch (NoService noService) {
                    noService.printStackTrace();
                } catch (ErrorContainer errorContainer) {
                    errorContainer.printStackTrace();
                } catch (NotLaunched notLaunched) {
                    notLaunched.printStackTrace();
                }
            }
        }, 3000, 20000);

    }

    public void connect() throws NoDevice, NoService, ErrorContainer, NotLaunched {
        // Vérifier nom Container
        ContainerWComp c = new ContainerWComp("container_Structural_0");

        cleanDeadLinks(c);

        String[] beans = c.getBeans();

        //On recupere d'abord la liste des lecteurs PDF
        for (String bean : beans) {
            String beanProperties[] = bean.split(":");
            if (beanProperties[0].contains("PDF")) {
                System.out.println("Lecteur PDF : " + beanProperties[0]);
                if (!connexions.keySet().contains(beanProperties[0]))
                    connexions.put(beanProperties[0],"");
            }

        }

        for (String bean : beans) {
            String beanProperties[] = bean.split(":");

            if (bean.contains("android_Remote_Controller") ) {

                if (master.equals(""))
                    master = beanProperties[0];

                if (beanProperties[0].equals(master)) {
                    System.err.println("Master : " + master);
                    for (String lec : connexions.keySet()) {
                        System.err.println("Key Set : " + lec);
                        String link = connexions.get(lec);
                        //Si lecteur pas connecté, on le connecte à la télécommande maîtresse
                        if (link.equals("")) {
                            System.err.println("Création lien master lecteur PDF :");
                            link = c.createLink(master, "Status_Event", lec, "SetCommande", "");
                            connexions.put(lec, link);
                            connectedToMaster.add(lec);
                        }
                    }
                }
                pause(1000);
            }
            if ((bean.contains("android_Remote_Controller") || bean.contains("Defilement")) && !beanProperties[0].equals(master)) {
                if (!remotes.keySet().contains(beanProperties[0])) {
                    remotes.put(beanProperties[0], "");
                }
                for (String r : remotes.keySet()) {
                    int i = 0;
                    while (remotes.get(r).equals("") && i < connexions.keySet().toArray().length) {
                        String l = (String) connexions.keySet().toArray()[i];

                        if (connectedToMaster.contains(l)) {
                            System.err.println("Création lien tel lecteur PDF :");
                            connectedToMaster.remove(l);
                            c.removeLink(connexions.get(l));
                            String link = c.createLink(r, "Status_Event", l, "SetCommande", "");
                            connexions.put(l,link);
                            remotes.put(r,l);
                        }
                        i++;
                    }
                }
            }
        }

    }


    /**
     * Supprime toute trace d'un bean qui n'est plus présent dans le conteneur WComp
     * Un bean qui n'est plus présent ne peut plus de facto être connecté à un autre
     */

    public void cleanDeadLinks(ContainerWComp c) throws NoDevice, NoService, ErrorContainer, NotLaunched {
        String[] beans = c.getBeans();

        for (String r : remotes.keySet()) {
            
            int i = 0;
            String b = "";
            boolean contained = false;
            String beanProperties[];

            while ((i < beans.length) && (!b.equals(r)) ) {
               b = beans[i];
               beanProperties = b.split(":");
               i++;
               if (beanProperties[0].equals(r)) {
                   contained = true;
               }
           }
           if (!contained) {
               String  l = remotes.remove(r);
               connexions.put(l,"");
           }

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
