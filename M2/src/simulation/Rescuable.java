package simulation;

import model.disasters.Disaster;

public interface Rescuable {

	void struckBy(Disaster d);

	Address getLocation();

	Disaster getDisaster();

}
