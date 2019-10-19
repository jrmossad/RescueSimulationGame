package model.infrastructure;

import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
import java.util.ArrayList;
import model.disasters.Disaster;
import model.people.Citizen;

public class ResidentialBuilding implements Simulatable, Rescuable {
	
	private Address location;
	private int structuralIntegrity;
	private int fireDamage;
	private int gasLevel;
	private int foundationDamage;
	private ArrayList<Citizen> occupants;
	private Disaster disaster;
	
	public ResidentialBuilding(Address location) {
		this.location = location;
		this.structuralIntegrity = 100;
		this.fireDamage = 0;
		this.gasLevel = 0;
		this.foundationDamage = 0;
		occupants = new ArrayList<Citizen>();
	}

	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}

	public void setStructuralIntegrity(int structuralIntegrity) {
		this.structuralIntegrity = structuralIntegrity;
	}

	public int getFireDamage() {
		return fireDamage;
	}

	public void setFireDamage(int fireDamage) {
		this.fireDamage = fireDamage;
	}

	public int getGasLevel() {
		return gasLevel;
	}

	public void setGasLevel(int gasLevel) {
		this.gasLevel = gasLevel;
	}

	public int getFoundationDamage() {
		return foundationDamage;
	}

	public void setFoundationDamage(int foundationDamage) {
		this.foundationDamage = foundationDamage;
	}

	public Address getLocation() {
		return location;
	}

	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}

	public Disaster getDisaster() {
		return disaster;
	}
	
}
