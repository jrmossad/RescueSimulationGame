package model.units;

import simulation.Address;

public abstract class MedicalUnit extends Unit {

	private int healingAmount;
	private int treatmentAmount;

	public MedicalUnit(String unitID, Address location, int stepsPerCycle) {

		super(unitID, location, stepsPerCycle);
		healingAmount = 10;
		treatmentAmount = 10;

	}

}
