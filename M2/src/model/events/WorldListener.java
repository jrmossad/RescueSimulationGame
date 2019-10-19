package model.events;

import simulation.Simulatable;

public interface WorldListener {
	
	void assignAddress(Simulatable sim, int x , int y);

}
