package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {

	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		if (distanceToTarget < 0)
			this.distanceToTarget = 0;
		else
			this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void cycleStep() {
		if (getTarget() != null) {
			if (distanceToTarget > 0) {
				int distance = this.distanceToTarget - getStepsPerCycle();
				setDistanceToTarget(Math.max(distance, 0));
				if (distanceToTarget == 0)
					getWorldListener().assignAddress(this, getTarget().getLocation().getX(),
							getTarget().getLocation().getY());
			} else if (distanceToTarget == 0) {
				getWorldListener().assignAddress(this, getTarget().getLocation().getX(),
						getTarget().getLocation().getY());
				treat();
			}
		}
	}

	public void treat() {
		setState(UnitState.TREATING);
	}

	public void jobsDone() {
		target = null;
		setState(UnitState.IDLE);
	}

	@Override
	public void respond(Rescuable r) {
		if (r != null) {
			Address location = r.getLocation();
			int x = location.getX();
			int y = location.getY();
			int distance = Math.abs((x - getLocation().getX())) + Math.abs((y - getLocation().getY()));
			if ((getTarget() instanceof Citizen && (((Citizen) getTarget()).getBloodLoss() != 0
					|| (((Citizen) getTarget()).getToxicity() != 0))) || getTarget() instanceof ResidentialBuilding)
				getTarget().getDisaster().setActive(true);
			target = r;
			setDistanceToTarget(distance);
			setState(UnitState.RESPONDING);
		}
	}

}
