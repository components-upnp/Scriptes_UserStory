package smac.upnp.wcomp;

import org.fourthline.cling.model.types.InvalidValueException;
import smac.upnp.wcomp.Spy;

import java.util.HashMap;

public class ActionStoryVerouillage extends Spy {
	public ActionStoryVerouillage() {
		super("Android Remote Slider Controller");
    }

	public void setValeur(String val) throws ErrorContainer, NoDevice, NotLaunched, NoService {
		try {
			System.out.println("launch SetTarget");
			HashMap<String,Object> arg = new HashMap<String, Object>();
			arg.put("NewTargetValue",val);
		this.launchAction("SliderController","SetTarget", arg);
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


	public void verouille() throws ErrorContainer, NoDevice, NoService, NotLaunched {
		try {

			this.launchAction("urn:upnp-org:serviceId:RemoteController","SetTarget", (HashMap<String, Object>) new HashMap<String, Object>().put("CENTRE","CENTRE"));
			this.launchAction("urn:upnp-org:serviceId:RemoteController","SetTarget", (HashMap<String, Object>) new HashMap<String, Object>().put("AUCUN","AUCUN"));

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