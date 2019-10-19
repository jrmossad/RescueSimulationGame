package model.units;

import model.disasters.GasLeak;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class GasControlUnit extends FireUnit {

	public GasControlUnit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		super.treat();
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding building = (ResidentialBuilding) getTarget();
			if (building.getGasLevel() > 0 && building.getStructuralIntegrity() != 0) {
				if (building.getDisaster() instanceof GasLeak)
					building.getDisaster().setActive(false);
				int gasLevel = building.getGasLevel() - 10;
				building.setGasLevel(gasLevel >= 0 ? gasLevel : 0);
			}
			if (building.getGasLevel() == 0 || building.getStructuralIntegrity() == 0)
				jobsDone();
		}
	}

}
