package model.disasters;

import model.people.Citizen;

public class Infection extends Disaster {

	public Infection(int startCycle, Citizen target) {
		super(startCycle, target);
	}

	@Override
	public void cycleStep() {
		if (getTarget() instanceof Citizen) {
			Citizen target = (Citizen) getTarget();
			int toxicity = target.getToxicity() + 15;
			target.setToxicity(toxicity);
		}
	}

	@Override
	public void strike() {
		super.strike();
		if (getTarget() instanceof Citizen) {
			Citizen target = (Citizen) getTarget();
			int toxicity = target.getToxicity() + 25;
			target.setToxicity(toxicity);
		}
	}

}
