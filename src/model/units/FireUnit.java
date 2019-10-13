package model.units;

import simulation.Address;

public abstract class FireUnit extends Unit {

	public FireUnit(String unitID, Address location, int stepsPerCycle) {

		super(unitID, location, stepsPerCycle);

	}

}
