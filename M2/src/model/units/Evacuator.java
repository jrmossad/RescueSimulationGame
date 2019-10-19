package model.units;

import java.util.ArrayList;
import java.util.Iterator;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class Evacuator extends PoliceUnit {

	public Evacuator(String unitID, Address location, int stepsPerCycle, WorldListener worldListener, int maxCapacity) {
		super(unitID, location, stepsPerCycle, worldListener, maxCapacity);
	}

	@Override
	public void treat() {
		super.treat();
		if (getTarget() instanceof ResidentialBuilding) {
			ResidentialBuilding building = (ResidentialBuilding) getTarget();
			if (building.getStructuralIntegrity() > 0 && getPassengers().size() < getMaxCapacity()
					&& getLocation() == building.getLocation() && building.getOccupants().size() > 0) {
				int j = getPassengers().size();
				for (Iterator<Citizen> i = building.getOccupants().iterator(); i.hasNext() && j < getMaxCapacity();) {
					Citizen citizen = i.next();
					if (citizen.getState() != CitizenState.DECEASED) {
						getPassengers().add(citizen);
						i.remove();
						j++;
					}
				}
				Address location = getTarget().getLocation();
				int x = location.getX();
				int y = location.getY();
				int distance = Math.abs(x) + Math.abs(y);
				setDistanceToBase(Math.max(distance, 0));
			} else if (getPassengers().size() > 0) {
				if (getDistanceToBase() > 0) {
					int distanceToBase = getDistanceToBase() - getStepsPerCycle();
					setDistanceToBase(Math.max(distanceToBase, 0));
					if (getDistanceToBase() == 0)
						getWorldListener().assignAddress(this, 0, 0);
				} else if (getDistanceToBase() == 0) {
					getWorldListener().assignAddress(this, 0, 0);
					for (int i = 0; i < getPassengers().size(); i++) {
						Citizen citizen = getPassengers().get(i);
						getWorldListener().assignAddress(citizen, 0, 0);
						citizen.setState(CitizenState.RESCUED);
					}
					getPassengers().clear();
					Address location = building.getLocation();
					int x = location.getX();
					int y = location.getY();
					int distance = Math.abs(x) + Math.abs(y);
					setDistanceToTarget(distance);
				}
			}
			if ((building.getStructuralIntegrity() == 0 || building.getOccupants().size() == 0
					|| !(checkAlive(building.getOccupants()))) && getPassengers().size() == 0)
				jobsDone();
		}
	}

	private boolean checkAlive(ArrayList<Citizen> citizens) {
		for (Iterator<Citizen> i = citizens.iterator(); i.hasNext();)
			if (i.next().getState() != CitizenState.DECEASED)
				return true;
		return false;
	}

}
