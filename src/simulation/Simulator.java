package simulation;

import java.util.ArrayList;

import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Simulator {
	
	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;
	
	public Simulator() throws IOException {
		world = new Address[10][10];
		intialize();
		buildings = new ArrayList<ResidentialBuilding>();
		citizens = new ArrayList<Citizen>();
		plannedDisasters = new ArrayList<Disaster>();
		executedDisasters = new ArrayList<Disaster>();
		emergencyUnits = new ArrayList<Unit>();
		loadBuildings("buildings.csv");
		loadCitizens("citizens.csv");
		loadUnits("units.csv");
		loadDisasters("disasters.csv");
	}
	
	private void intialize() {
		for(int i = 0; i < world.length; i++)
			for(int j = 0;j < world[i].length; j++)
				world [i][j] = new Address(i, j);
	}
	
	private void loadUnits(String filePath)throws IOException {
		String currentLine = "";
		FileReader fileReader= new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {		
			String [] result= currentLine.split(",");
			switch(result[0]){
				case "AMB": emergencyUnits.add(new Ambulance(result[1],
						world[0][0], Integer.parseInt(result[2])));
							break;
							
				case "DCU": emergencyUnits.add(new DiseaseControlUnit(result[1],
						world[0][0], Integer.parseInt(result[2])));
							break;
							
				case "EVC": emergencyUnits.add(new Evacuator(result[1],
						world[0][0], Integer.parseInt(result[2]), Integer.parseInt(result[3])));
							break;
							
				case "FTK": emergencyUnits.add(new FireTruck(result[1],
						world[0][0], Integer.parseInt(result[2])));
							break;
							
				case "GCU": emergencyUnits.add(new GasControlUnit(result[1],
						world[0][0], Integer.parseInt(result[2])));
							break;	
			}
		}
		br.close();	
	}
	
	private void loadBuildings(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {		
			String [] result = currentLine.split(",");
			 buildings.add(new ResidentialBuilding(
					 world[Integer.parseInt(result[0])][Integer.parseInt(result[1])]));				
		}
		br.close();
	}
	
	private void loadCitizens(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {		
			String [] result = currentLine.split(",");
			Citizen citizen = new Citizen(
					world[Integer.parseInt(result[0])][Integer.parseInt(result[1])],
					result[2], result[3], Integer.parseInt(result[4]));
			citizens.add(citizen);	
			 ResidentialBuilding building = 
					 getBuilding(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
			 if(building != null)
				 building.getOccupants().add(citizen);
		}
		br.close();
	}
	
	private void loadDisasters(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {		
			String [] result = currentLine.split(",");
			switch(result[1]) {
				case "FIR": plannedDisasters.add(new Fire(Integer.parseInt(result[0]),
									getBuilding(Integer.parseInt(result[2]),
											Integer.parseInt(result[3]))));
							break;
							
				case "INJ": plannedDisasters.add(new Injury(Integer.parseInt(result[0]),
							getCitizen(result[2])));
							break;
							
				case "INF": plannedDisasters.add(new Infection(Integer.parseInt(result[0]),
						getCitizen(result[2])));
							break;
							
				case "GLK": plannedDisasters.add(new GasLeak(Integer.parseInt(result[0]),
						getBuilding(Integer.parseInt(result[2]),
						Integer.parseInt(result[3]))));
							break;
			}
		}
		br.close();
	}
	
	private ResidentialBuilding getBuilding(int x, int y) {
		ResidentialBuilding building = null;
		for(int i = 0;i < buildings.size(); i++) {
			building = (ResidentialBuilding) buildings.get(i);
			Address location = building.getLocation();
			if(location.getX() == x && location.getY() == y)
				return building;
		}
		return building;
	}
	
	private Citizen getCitizen(String id) {
		Citizen citizen = null;
		for(int i = 0;i < citizens.size(); i++) {
			citizen = (Citizen) citizens.get(i);
			String l = citizen.getNationalID();
			if(l.equals(id))
				return citizen;
		}
		return citizen;
	}
	
}
