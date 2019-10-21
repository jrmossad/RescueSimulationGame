package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;

public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);

	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() > 0) {
			target.setFoundationDamage(target.getFoundationDamage() + 10);
			super.strike();
		}
		else
			throw new BuildingAlreadyCollapsedException(this, "The building is Collapsed");
	}

	public void cycleStep() {
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		target.setFoundationDamage(target.getFoundationDamage() + 10);
	}

}
