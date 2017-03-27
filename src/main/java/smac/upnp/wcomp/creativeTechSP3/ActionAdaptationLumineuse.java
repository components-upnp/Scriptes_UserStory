package smac.upnp.wcomp.creativeTechSP3;

import org.fourthline.cling.model.types.InvalidValueException;
import smac.upnp.wcomp.*;

import java.util.HashMap;

/**
 * Created by Abdourahamane Ly on 27/03/2017.
 */
public class ActionAdaptationLumineuse extends Spy{
    public ActionAdaptationLumineuse() {
        super("LUMI");
    }

    public void adapterLumiere(String val) throws ErrorContainer, NoDevice, NotLaunched, NoService {
        try {
            HashMap<String,Object> arg = new HashMap<String, Object>();
            arg.put("NewTargetValue",val);
            this.launchAction("LUMI1","SetTarget", arg);
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