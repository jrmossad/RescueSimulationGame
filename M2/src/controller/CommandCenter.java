package controller;

import java.util.ArrayList;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;

public class CommandCenter implements SOSListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;

	public CommandCenter() throws Exception {
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
		engine = new Simulator(this);
	}

	@Override
	public void receiveSOSCall(Rescuable r) {
		if (r instanceof Citizen) {
			Citizen citizen = (Citizen) r;
			visibleCitizens.add(citizen);
		} else if (r instanceof ResidentialBuilding) {
			ResidentialBuilding building = (ResidentialBuilding) r;
			visibleBuildings.add(building);
		}
	}

}
