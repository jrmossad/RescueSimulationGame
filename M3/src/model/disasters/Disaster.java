package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable {
	private int startCycle;
	private Rescuable target;
	private boolean active;

	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getStartCycle() {
		return startCycle;
	}

	public Rescuable getTarget() {
		return target;
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {

		target.struckBy(this);
		active = true;
	}
	
	@Override
	public String toString() {
		return String.format("");
	}
}
