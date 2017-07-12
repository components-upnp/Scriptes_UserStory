package main.java.main;


import main.java.scriptsDemos.Camera;
import main.java.scriptsDemos.MultiplePdf;
import main.java.scriptsDemos.RemoteControlPdf;
import main.java.scriptsDemos.Vote;

/**
 * Created by mkostiuk on 28/06/2017.
 */
public class App {
    public static void main(String[] args) {
        if (args[0].equals("Vote"))
            new Vote().run();
        if (args[0].equals("Camera"))
            new Camera().run();
        if (args[0].equals("Pdf"))
            new RemoteControlPdf().run();
        if (args[0].equals("Pdf2"))
            new MultiplePdf().run();
    }
}
