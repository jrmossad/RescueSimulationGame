package controller;

import java.io.IOException;
import java.util.ArrayList;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Simulator;

public class CommandCenter {
	
	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;
	
	public CommandCenter() throws IOException {
		engine = new Simulator();
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
	}
	
}
