package main.java.smac.upnp.wcomp;

public class App 
{
    public static void main( String[] args )
    {
    	// Vérifier nom Container
        ContainerWComp c = new ContainerWComp("Container1_Structural_0");
        
        try {
        	//Winamp
        	pause(3000);
			String winamp = c.createBeanAtPos("Winamp", "WComp.UPnPDevice.WinampRemote", 400, 200);

			pause(2000);
			String bouton1 = c.createBeanAtPos("Bouton 1", "System.Windows.Forms.Button", 600, 400);
			pause(1000);
			String linkBut1 = c.createLink("Bouton 1", "Click", "Winamp", "NextSong", "");
			
			pause(1000);
			String bouton2 = c.createBeanAtPos("Bouton 2", "System.Windows.Forms.Button", 200, 400);
			pause(1000);
			String linkBut2 = c.createLink("Bouton 2", "Click", "Winamp", "PreviousSong", "");
			
			//Impress
			pause(2000);
			String impressJS = c.createBeanAtPos("ImpressJS", "WComp.UPnPDevice.ImpressJS", 400, 100);
			
			//ArduinoButton
        	//pause(1500);
        	//String buttonArd = c.createBeanAtPos("Arduino Bouton", "WComp.UPnPDevice.Arduino_Button", 200, 100);
			
        	pause(1000);
        	c.removeLink(linkBut1);
        	pause(1000);
        	c.removeLink(linkBut2);
        	
        	pause(1000);
			/*String linkNext = c.createLink("Arduino Bouton", "Button1_Event", "Winamp", "NextSong", "");
			pause(1000);
			String linkPrevious = c.createLink("Arduino Bouton", "Button2_Event", "Winamp", "PreviousSong", "");*/

			//
			
			pause(1000);
			linkBut1 = c.createLink("Bouton 1", "Click", "ImpressJS", "Next", "");
			pause(1000);
			linkBut2 = c.createLink("Bouton 2", "Click", "ImpressJS", "Previous", "");
			
			//Arduino Joystick
			/*pause(40000);
			String joystickArd = c.createBeanAtPos("Arduino Joystick", "WComp.UPnPDevice.Arduino_Joystick", 200, 200);*/
			
			/*pause(2000);
			c.removeLink(linkNext);
			pause(1000);
			c.removeLink(linkPrevious);*/
			
			/*pause(1000);
			String linkUp = c.createLink("Arduino Joystick", "X_Up_Event", "Winamp", "VolumeUp", "");
			pause(1000);
			String linkDown = c.createLink("Arduino Joystick", "X_Down_Event", "Winamp", "VolumeDown", "");
			pause(1000);
			String linkLeft = c.createLink("Arduino Joystick", "Y_Left_Event", "Winamp", "PreviousSong", "");
			pause(1000);
			String linkRight = c.createLink("Arduino Joystick", "Y_Right_Event", "Winamp", "NextSong", "");*/
			
			
			//Apparaitre  nexu
			pause(25000);
			String nexus_tablet = c.createBeanAtPos("nexus_tablet", "WComp.UPnPDevice.Nexus_Tablet", 600, 600);
	//		pause(2000);
			
			
			//Remove liens
			/*pause(2000);
			c.removeLink(linkUp);
        	c.removeLink(linkDown);
			c.removeLink(linkLeft);
			c.removeLink(linkRight);*/
			//create link nexus winamp
			pause(2000);
			String linkNexus = c.createLink("nexus_tablet", "Value_Event", "Winamp", "jumpTo", "");
			
//			//pause(20000);
//			String scrollBar = c.createBeanAtPos("Scroll 1", "System.Windows.Forms.HScrollBar", 400, 400);
//			c.setPropertyValue("Scroll 1", "Maximum", "﻿<?xml version=\"1.0\" encoding=\"utf-8\"?><int>3</int>");
//			c.setPropertyValue("Scroll 1", "LargeChange", "﻿<?xml version=\"1.0\" encoding=\"utf-8\"?><int>1</int>");
//			
//			//pause(10000);
//        	c.removeLink(linkNext);
//        	c.removeLink(linkPrevious);

//        	pause(2000);
//			String linkScroll = c.createLink("Scroll 1", "ValueChanged", "Winamp", "jumpTo", "get_Value");
//
//        	//pause(10000);
//        	c.removeLink(linkBut1);
//        	c.removeLink(linkBut2);
//        	
			
////			pause(10000);
//			String linkImpressJSNext = c.createLink("Arduino Bouton", "Button1_Event", "ImpressJS", "Next", "");
//			String linkImpressJSPrevious = c.createLink("Arduino Bouton", "Button2_Event", "ImpressJS", "Previous", "");
//			
////			pause(10000);
//			c.removeBean("Bouton 1");
//			c.removeBean("Bouton 2");
//			

		
			pause(3000);
			c.stopSpy();
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
		} catch (SpyNotRunning e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
