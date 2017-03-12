public class ActionStoryVerouillage extends Spy {
	public ActionStoryVerouillage() {
		super();
	}

	public void setValeur(String val) {
		try {
		super.launchAction("urn:upnp-org:serviceId:SliderController","SetTarget", val);
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
		}
	}


	public void verouille() {
		try {
		super.launchAction("urn:upnp-org:serviceId:RemoteController","SetTarget", "CENTRE");
		super.launchAction("urn:upnp-org:serviceId:RemoteController","SetTarget", "AUCUN");

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
		}
	}
}