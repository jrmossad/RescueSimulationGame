package model.units;

import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public abstract class MedicalUnit extends Unit {

	private int healingAmount;
	private int treatmentAmount;

	public MedicalUnit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
		healingAmount = 10;
		treatmentAmount = 10;
	}

	public int getTreatmentAmount() {
		return treatmentAmount;
	}

	public void heal() {
		if (getTarget() instanceof Citizen) {
			setState(UnitState.TREATING);
			Citizen citizen = (Citizen) getTarget();
				citizen.setState(CitizenState.RESCUED);
				int hp = citizen.getHp() + healingAmount;
				citizen.setHp(hp <= 100 ? hp : 100);
			if ((citizen.getHp() == 100 && citizen.getBloodLoss() == 0 && citizen.getToxicity() == 0)
					|| citizen.getState() == CitizenState.DECEASED)
				jobsDone();
		}
	}

}
