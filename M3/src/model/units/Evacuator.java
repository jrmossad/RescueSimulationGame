package model.units;

import model.disasters.Collapse;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {

	public Evacuator(String unitID, Address location, int stepsPerCycle, WorldListener worldListener, int maxCapacity) {
		super(unitID, location, stepsPerCycle, worldListener, maxCapacity);

	}

	@Override
	public void treat() {
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0 || target.getOccupants().size() == 0) {
			jobsDone();
			return;
		}

		for (int i = 0; getPassengers().size() != getMaxCapacity() && i < target.getOccupants().size(); i++) {
			getPassengers().add(target.getOccupants().remove(i));
			i--;
		}

		setDistanceToBase(target.getLocation().getX() + target.getLocation().getY());

	}

	@Override
	public boolean canTreat(Rescuable r) {
		if (r instanceof ResidentialBuilding) {
			ResidentialBuilding target = (ResidentialBuilding) r;
			if (!(target.getDisaster() instanceof Collapse))
				return false;
			else
				return true;
		} else
			return false;
	}

	@Override
	public String toString() {
		return super.toString() + String.format(" The number of Passenger: %s%n The Passengers Information: %s%n",
				getPassengers().size(), (passengersInfo().equals("") ? " No Passengers" : "\n" + passengersInfo()));
	}

	public String passengersInfo() {
		String s = "";
		for (int i = 0; i < getPassengers().size(); i++)
			s += " Passenger " + (i + 1) + ":\n************\n" + getPassengers().get(i).toString()
					+ " *****************************\n";
		return s;
	}

}
