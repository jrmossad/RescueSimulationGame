package model.units;

import simulation.Address;

public class Evacuator extends PoliceUnit {

	public Evacuator(String id, Address location, int stepsPerCycle, int maxCapacity) {
		super(id, location, stepsPerCycle, maxCapacity);
	}
	
}
