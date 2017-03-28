package smac.upnp.wcomp.creativeTechSP3;

import org.fourthline.cling.model.types.InvalidValueException;
import smac.upnp.wcomp.*;

import java.util.HashMap;

/**
 * Created by Abdourahamane Ly on 27/03/2017.
 */
public class ActionDetecterMouvement extends Spy{
    public ActionDetecterMouvement() {
        super("Motion Detector");
    }

    public void detecterMouvement(String val) throws ErrorContainer, NoDevice, NoService, NotLaunched {
        try {

            HashMap<String,Object> arg = new HashMap<String, Object>();
            arg.put("NewTargetValue",val);
            this.launchAction("MotionDetectorService","SetTarget", arg);
        } catch (InvalidValueException e) {
            throw new ErrorContainer("Wrong value");
        } catch (NoDevice e) {
            throw e;
        } catch (NoService e) {
            throw e;
        } catch (NotLaunched e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}