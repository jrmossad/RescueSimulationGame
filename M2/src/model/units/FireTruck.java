package model.units;

import model.disasters.Fire;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class FireTruck extends FireUnit {

	public FireTruck(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		super.treat();
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding building = (ResidentialBuilding) getTarget();
			if (building.getFireDamage() > 0 && building.getStructuralIntegrity() != 0) {
				if (building.getDisaster() instanceof Fire)
					building.getDisaster().setActive(false);
				int firedamage = building.getFireDamage() - 10;
				building.setFireDamage(firedamage >= 0 ? firedamage : 0);
			}
			if (building.getFireDamage() == 0 || building.getStructuralIntegrity() == 0)
				jobsDone();
		}
	}

}
