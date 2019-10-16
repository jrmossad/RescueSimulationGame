package model.units;

import simulation.Address;

public class Ambulance extends MedicalUnit {

	public Ambulance(String id, Address location, int stepsPerCycle) {
		super(id, location, stepsPerCycle);
	}

}
