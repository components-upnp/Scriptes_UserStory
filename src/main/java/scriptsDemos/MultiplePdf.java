package main.java.scriptsDemos;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
    private HashMap<String, Boolean> likers = new HashMap<>();
    private HashMap<String, Boolean> afficheurs = new HashMap<>();
    private HashMap<String, Boolean> recuperateurs = new HashMap<>();

    private HashMap<String,String> visionneuseRecuperateur = new HashMap<>();
    private HashMap<String, Boolean> recupAssocieVisio = new HashMap<>();
    private HashMap<String,String> linkRecup = new HashMap<>();


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

    /**
     * Méthode qui permet de gérer la connexion et la découverte des différents composants du conteneur WComp
     * */
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
                if (!connexions.keySet().contains(beanProperties[0])) {
                    connexions.put(beanProperties[0], "");
                    visionneuseRecuperateur.put(beanProperties[0],"");
                }
            }

        }



        for (String bean : beans) {
            String beanProperties[] = bean.split(":");

            if (bean.contains("recuperateur")) {
                if (!recuperateurs.keySet().contains(beanProperties[0])) {
                    recuperateurs.put(beanProperties[0], false);
                    recupAssocieVisio.put(beanProperties[0], false);
                }

                if (!recupAssocieVisio.get(beanProperties[0])) {
                    //On associe le recuperateur avec une visionneuse
                    boolean found = false;
                    int i = 0;
                    while (!found && i < connexions.size()) {
                        if (visionneuseRecuperateur.keySet().contains(connexions.keySet().toArray()[i]) &&  visionneuseRecuperateur.get(connexions.keySet().toArray()[i]).equals(""))
                            found = true;
                        else
                            i++;
                    }
                    if (found) {
                        String v = (String) connexions.keySet().toArray()[i];
                        recupAssocieVisio.put(beanProperties[0], true);
                        System.out.println("Association entre " + v + " " + beanProperties[0]);
                        visionneuseRecuperateur.put(v,beanProperties[0]);
                    }
                }
            }

            if ((bean.contains("android_Remote_Controller") || bean.contains("Defilement")) ) {

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
                            linkRecup.put(visionneuseRecuperateur.get(lec),c.createLink(master, "Status_Event", visionneuseRecuperateur.get(lec), "SetCommande", ""));
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
                            c.removeLink(linkRecup.get(visionneuseRecuperateur.get(l)));
                            String link = c.createLink(r, "Status_Event", l, "SetCommande", "");
                            linkRecup.put(visionneuseRecuperateur.get(l),c.createLink(r, "Status_Event", visionneuseRecuperateur.get(l), "SetCommande", ""));
                            connexions.put(l,link);
                            remotes.put(r,l);
                        }
                        i++;
                    }
                }
            }

            if (bean.contains("liker"))
                if (!likers.keySet().contains(beanProperties[0]))
                    likers.put(beanProperties[0], false);

            if (bean.contains("afficheur"))
                if (!afficheurs.keySet().contains(beanProperties[0]))
                    afficheurs.put(beanProperties[0], false);




        }

        connectLikers(c);
        connectRecuperateurAfficheurLiker(c);

    }


    /**
     * Supprime toute trace d'un bean qui n'est plus présent dans le conteneur WComp
     * Un bean qui n'est plus présent ne peut plus de facto être connecté à un autre
     * On peut grâce à cette méthode retirer et ajouter des télécommandes
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

    /**
     * Cette méthode connecte tous les likers etre eux.
     * On créé un maillage complet de likers.
     * */
    public void connectLikers(ContainerWComp c) throws NoDevice, NoService, ErrorContainer, NotLaunched {

        System.err.println("CONNECT LIKERS");
        for (String liker : likers.keySet())
            for (String otherLiker : likers.keySet())
                if (!otherLiker.equals(liker))
                    c.createLink(liker, "Like_Event", otherLiker, "SetLikes", "");



    }

    /**
     * Méthode qui permet d'associer un récupérateur avec un afficheur ainsi qu'un liker.
     * */
    public void connectRecuperateurAfficheurLiker(ContainerWComp c) throws NoDevice, NoService, ErrorContainer, NotLaunched {

        System.err.println("CONNECT LES AUTRES ENTRE EUXpfjceojf");
        for (String recup : recuperateurs.keySet())
            if (!recuperateurs.get(recup)) {
                String liker = "";
                String afficheur = "";
                boolean found = false;

                int i = 0;

                while (!found && (i < likers.size())) {
                    if (!likers.get(likers.keySet().toArray()[i])) {
                        liker = (String) likers.keySet().toArray()[i];
                        found = true;
                    }
                }

                found = false;
                i = 0;

                while (!found && (i < afficheurs.size())) {
                    if (!afficheurs.get(afficheurs.keySet().toArray()[i])) {
                        afficheur = (String) afficheurs.keySet().toArray()[i];
                        found = true;
                     }
                 }

                 if (!(liker.equals("") && afficheur.equals(""))) {
                    c.createLink(recup, "Page_Event", liker, "SetNumPage", "");
                    c.createLink(recup, "Page_Event", afficheur, "SetPage", "");
                    c.createLink(liker, "Likes_Event", afficheur, "SetLikes", "");

                    recuperateurs.put(recup, true);
                    likers.put(liker, true);
                    afficheurs.put(afficheur, true);
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
