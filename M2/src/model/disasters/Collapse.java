package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
	}

	@Override
	public void cycleStep() {
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding target = (ResidentialBuilding) getTarget();
			int foundationDamage = target.getFoundationDamage() + 10;
			target.setFoundationDamage(foundationDamage);
		}
	}

	@Override
	public void strike() {
		super.strike();
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding target = (ResidentialBuilding) getTarget();
			int foundationDamage = target.getFoundationDamage() + 10;
			target.setFoundationDamage(foundationDamage);
		}
	}

}
