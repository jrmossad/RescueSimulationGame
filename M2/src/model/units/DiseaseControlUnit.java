package model.units;

import model.disasters.Infection;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class DiseaseControlUnit extends MedicalUnit {

	public DiseaseControlUnit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		super.treat();
		if (getTarget() instanceof Citizen) {
			Citizen citizen = (Citizen) getTarget();
			if (citizen.getToxicity() > 0 && citizen.getState() != CitizenState.DECEASED) {
				if (citizen.getDisaster() instanceof Infection)
					citizen.getDisaster().setActive(false);
				int toxicity = citizen.getToxicity() - getTreatmentAmount();
				citizen.setToxicity(toxicity >= 0 ? toxicity : 0);
				if (citizen.getToxicity() == 0)
					citizen.setState(CitizenState.RESCUED);
			}
			if (citizen.getToxicity() == 0 && citizen.getHp() > 0 && citizen.getHp() < 100)
				heal();
			if ((citizen.getHp() == 100 && citizen.getToxicity() == 0) || citizen.getState() == CitizenState.DECEASED)
				jobsDone();
		}
	}

}
