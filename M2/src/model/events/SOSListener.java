package model.events;

import simulation.Rescuable;

public interface SOSListener {
	
	void receiveSOSCall(Rescuable r);

}
