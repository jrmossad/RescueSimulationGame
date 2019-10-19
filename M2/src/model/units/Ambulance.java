package model.units;

import model.disasters.Injury;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		super.treat();
		if (getTarget() instanceof Citizen) {
			Citizen citizen = (Citizen) getTarget();
			if (citizen.getBloodLoss() > 0 && citizen.getState() != CitizenState.DECEASED) {
				if (citizen.getDisaster() instanceof Injury)
					citizen.getDisaster().setActive(false);
				int bloodLoss = citizen.getBloodLoss() - getTreatmentAmount();
				citizen.setBloodLoss(bloodLoss >= 0 ? bloodLoss : 0);
				if (citizen.getBloodLoss() == 0)
					citizen.setState(CitizenState.RESCUED);
			}
			if (citizen.getBloodLoss() == 0 && citizen.getState() == CitizenState.RESCUED)
				heal();
			if ((citizen.getHp() == 100 && citizen.getBloodLoss() == 0) || citizen.getState() == CitizenState.DECEASED)
				jobsDone();
		}
	}

}
