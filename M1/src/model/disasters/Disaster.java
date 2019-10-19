package model.disasters;

import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable {
	
	private int startCycle;
	private Rescuable target;
	private boolean active;

	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
		this.active = false;
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
	
}
