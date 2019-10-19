package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class GasLeak extends Disaster {

	public GasLeak(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
	}

	@Override
	public void cycleStep() {
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding target = (ResidentialBuilding) getTarget();
			int gasLevel = target.getGasLevel() + 15;
			target.setGasLevel(gasLevel);
		}
	}

	@Override
	public void strike() {
		super.strike();
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding target = (ResidentialBuilding) getTarget();
			int gasLevel = target.getGasLevel() + 10;
			target.setGasLevel(gasLevel);
		}
	}

}
