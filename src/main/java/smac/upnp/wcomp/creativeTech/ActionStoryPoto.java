package smac.upnp.wcomp.creativeTech;

import org.fourthline.cling.model.types.InvalidValueException;
import smac.upnp.wcomp.*;

import java.util.HashMap;

/**
 * Created by IDA on 28/03/2017.
 */
public class ActionStoryPoto extends Spy {
    public ActionStoryPoto() {
        super("POTO");
    }

    public void setValeur(String val) throws ErrorContainer, NoDevice, NotLaunched, NoService {
        try {
            System.out.println("launch SetTarget");
            HashMap<String,Object> arg = new HashMap<String, Object>();
            arg.put("NewTargetValue",val);
            this.launchAction("PotoService","SetTarget", arg);
            // SI NON PARAM
            //super.launchAction(serviceId,actionName, null);
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
