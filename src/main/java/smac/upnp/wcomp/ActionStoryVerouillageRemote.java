package smac.upnp.wcomp;

import org.fourthline.cling.model.types.InvalidValueException;

import java.util.HashMap;

/**
 * Created by IDA on 14/03/2017.
 */
public class ActionStoryVerouillageRemote extends Spy {
    public ActionStoryVerouillageRemote() {
        super("Android Remote Controller");
    }

    public void verouille() throws ErrorContainer, NoDevice, NoService, NotLaunched {
        try {

            HashMap<String,Object> arg = new HashMap<String, Object>();
            arg.put("NewTargetValue","CENTRE");

            this.launchAction("RemoteController","SetTarget", arg);
            arg.remove("NewTargetValue");
            arg.put("NewTargetValue","AUCUN");
            this.launchAction("RemoteController","SetTarget", arg);

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
