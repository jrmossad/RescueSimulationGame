package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class Fire extends Disaster {

	public Fire(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
	}

	@Override
	public void cycleStep() {
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding target = (ResidentialBuilding) getTarget();
			int fireDamage = target.getFireDamage() + 10;
			target.setFireDamage(fireDamage);
		}
	}

	@Override
	public void strike() {
		super.strike();
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding target = (ResidentialBuilding) getTarget();
			int fireDamage = target.getFireDamage() + 10;
			target.setFireDamage(fireDamage);
		}
	}

}
