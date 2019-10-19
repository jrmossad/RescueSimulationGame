package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;

public class Simulator implements WorldListener {

	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;
	private SOSListener emergencyService;

	public Simulator(SOSListener emergencyService) throws Exception {
		this.emergencyService = emergencyService;
		buildings = new ArrayList<ResidentialBuilding>();
		citizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
		plannedDisasters = new ArrayList<Disaster>();
		executedDisasters = new ArrayList<Disaster>();
		world = new Address[10][10];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				world[i][j] = new Address(i, j);
		}

		loadUnits("units.csv");
		loadBuildings("buildings.csv");
		loadCitizens("citizens.csv");
		loadDisasters("disasters.csv");

		for (int i = 0; i < buildings.size(); i++) {
			ResidentialBuilding building = buildings.get(i);
			for (int j = 0; j < citizens.size(); j++) {
				Citizen citizen = citizens.get(j);
				if (citizen.getLocation() == building.getLocation())
					building.getOccupants().add(citizen);
			}
		}
	}

	private void loadUnits(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			String id = info[1];
			int steps = Integer.parseInt(info[2]);
			switch (info[0]) {
			case "AMB":
				emergencyUnits.add(new Ambulance(id, world[0][0], steps, this));
				break;

			case "DCU":
				emergencyUnits.add(new DiseaseControlUnit(id, world[0][0], steps, this));
				break;

			case "EVC":
				emergencyUnits.add(new Evacuator(id, world[0][0], steps, this, Integer.parseInt(info[3])));
				break;

			case "FTK":
				emergencyUnits.add(new FireTruck(id, world[0][0], steps, this));
				break;

			case "GCU":
				emergencyUnits.add(new GasControlUnit(id, world[0][0], steps, this));
				break;
			}
			line = br.readLine();
		}
		br.close();
	}

	private void loadBuildings(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			int x = Integer.parseInt(info[0]);
			int y = Integer.parseInt(info[1]);
			ResidentialBuilding building = new ResidentialBuilding(world[x][y]);
			building.setEmergencyService(emergencyService);
			buildings.add(building);
			line = br.readLine();
		}
		br.close();
	}

	private void loadCitizens(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			int x = Integer.parseInt(info[0]);
			int y = Integer.parseInt(info[1]);
			String id = info[2];
			String name = info[3];
			int age = Integer.parseInt(info[4]);
			Citizen citizen = new Citizen(world[x][y], id, name, age, this);
			citizen.setEmergencyService(emergencyService);
			citizens.add(citizen);
			line = br.readLine();
		}
		br.close();
	}

	private void loadDisasters(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			int startCycle = Integer.parseInt(info[0]);
			ResidentialBuilding building = null;
			Citizen citizen = null;
			if (info.length == 3)
				citizen = getCitizenByID(info[2]);
			else {
				int x = Integer.parseInt(info[2]);
				int y = Integer.parseInt(info[3]);
				building = getBuildingByLocation(world[x][y]);
			}

			switch (info[1]) {
			case "INJ":
				plannedDisasters.add(new Injury(startCycle, citizen));
				break;

			case "INF":
				plannedDisasters.add(new Infection(startCycle, citizen));
				break;

			case "FIR":
				plannedDisasters.add(new Fire(startCycle, building));
				break;

			case "GLK":
				plannedDisasters.add(new GasLeak(startCycle, building));
				break;
			}

			line = br.readLine();
		}
		br.close();
	}

	private Citizen getCitizenByID(String id) {
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getNationalID().equals(id))
				return citizens.get(i);
		}
		return null;
	}

	private ResidentialBuilding getBuildingByLocation(Address location) {
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).getLocation() == location)
				return buildings.get(i);
		}
		return null;
	}

	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	@Override
	public void assignAddress(Simulatable sim, int x, int y) {
		Address location = world[x][y];
		if (sim instanceof Citizen) {
			Citizen citizen = (Citizen) sim;
			citizen.setLocation(location);
		} else if (sim instanceof Unit) {
			Unit unit = (Unit) sim;
			unit.setLocation(location);
		}
	}

	public boolean checkGameOver() {
		if (plannedDisasters.size() != 0)
			return false;
		for (int i = 0; i < executedDisasters.size(); i++) {
			Disaster disaster = executedDisasters.get(i);
			if (disaster.getTarget() instanceof Citizen) {
				Citizen citizen = (Citizen) disaster.getTarget();
				if (citizen.getState() != CitizenState.DECEASED)
					return false;
			} else if (disaster.getTarget() instanceof ResidentialBuilding) {
				ResidentialBuilding building = (ResidentialBuilding) disaster.getTarget();
				if (building.getStructuralIntegrity() != 0)
					return false;
			}
		}
		for (int i = 0; i < emergencyUnits.size(); i++)
			if (emergencyUnits.get(i).getState() != UnitState.IDLE)
				return false;
		return true;
	}

	public int calculateCasualties() {
		int counter = 0;
		for (int i = 0; i < citizens.size(); i++)
			if (citizens.get(i).getState() == CitizenState.DECEASED)
				counter++;
		return counter;
	}

	public void nextCycle() {
		currentCycle++;
		plannedDisasterExecute();
		unitsExecute();
		executedDisasterExecute();
		buidlingsExecute();
		citizensExecute();
	}

	private void plannedDisasterExecute() {
		for (Iterator<Disaster> i = plannedDisasters.iterator(); i.hasNext();) {
			Disaster disaster = i.next();
			if (disaster.getStartCycle() == currentCycle) {
				if (disaster instanceof Fire) {
					if (disaster.getTarget() instanceof ResidentialBuilding) {
						ResidentialBuilding building = (ResidentialBuilding) disaster.getTarget();
						int gasLevel = building.getGasLevel();
						if (gasLevel > 0 && gasLevel < 70)
							disaster = new Collapse(currentCycle, building);
						else if (gasLevel >= 70 && gasLevel <= 100) {
							building.setStructuralIntegrity(0);
							i.remove();
							continue;
						}
					}
				} else if (disaster instanceof GasLeak) {
					if (disaster.getTarget() instanceof ResidentialBuilding) {
						ResidentialBuilding building = (ResidentialBuilding) disaster.getTarget();
						Disaster fire = building.getDisaster();
						if (fire instanceof Fire && building.getFireDamage() > 0)
							disaster = new Collapse(currentCycle, building);
					}
				}
				disaster.strike();
				i.remove();
				executedDisasters.add(disaster);
			}
		}
		checkBuilding();
	}

	private void checkBuilding() {
		for (int i = 0; i < buildings.size(); i++) {
			ResidentialBuilding building = buildings.get(i);
			if (building.getFireDamage() == 100) {
				Collapse collapse = new Collapse(currentCycle, building);
				collapse.strike();
				executedDisasters.add(collapse);
			}
			if (building.getDisaster() instanceof Collapse) {
				building.setFireDamage(0);
				for (int j = 0; j < executedDisasters.size(); j++) {
					Disaster disaster = executedDisasters.get(j);
					if (!(disaster instanceof Collapse) && disaster.getTarget() == building)
						disaster.setActive(false);
				}
			}
		}
	}

	private void unitsExecute() {
		for (int i = 0; i < emergencyUnits.size(); i++)
			emergencyUnits.get(i).cycleStep();
	}

	private void executedDisasterExecute() {
		for (int i = 0; i < executedDisasters.size(); i++) {
			Disaster disaster = executedDisasters.get(i);
			if (disaster.getStartCycle() < currentCycle && disaster.isActive())
				disaster.cycleStep();
		}
	}

	private void buidlingsExecute() {
		for (int i = 0; i < buildings.size(); i++)
			buildings.get(i).cycleStep();
	}

	private void citizensExecute() {
		for (int i = 0; i < citizens.size(); i++)
			citizens.get(i).cycleStep();
	}

}
