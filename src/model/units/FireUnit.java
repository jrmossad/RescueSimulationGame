package model.units;

import simulation.Address;

public abstract class FireUnit extends Unit {
	
	public FireUnit(String id, Address location, int stepsPerCycle) {
		super(id, location, stepsPerCycle);
	}
	
}
