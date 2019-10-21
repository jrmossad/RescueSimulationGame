package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;
import simulation.Rescuable;

public class GasControlUnit extends FireUnit {

	public GasControlUnit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getGasLevel() > 0) 
			target.setGasLevel(target.getGasLevel() - 10);

		if (target.getGasLevel() == 0)
			jobsDone();

	}

	@Override
	public boolean canTreat(Rescuable r) {
		if(r instanceof ResidentialBuilding) {
			ResidentialBuilding target = (ResidentialBuilding) r;
			if(target.getGasLevel() == 0)
				return false;
			else
				return true;
		}
		else
			return false;
	}

}
