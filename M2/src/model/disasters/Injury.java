package model.disasters;

import model.people.Citizen;

public class Injury extends Disaster {

	public Injury(int startCycle, Citizen target) {
		super(startCycle, target);
	}

	@Override
	public void cycleStep() {
		if (getTarget() instanceof Citizen) {
			Citizen target = (Citizen) getTarget();
			int bloodloss = target.getBloodLoss() + 10;
			target.setBloodLoss(bloodloss);
		}
	}

	@Override
	public void strike() {
		super.strike();
		if (getTarget() instanceof Citizen) {
			Citizen target = (Citizen) getTarget();
			int bloodloss = target.getBloodLoss() + 30;
			target.setBloodLoss(bloodloss);
		}
	}

}
