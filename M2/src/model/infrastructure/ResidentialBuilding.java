package model.infrastructure;

import java.util.ArrayList;
import model.disasters.Disaster;
import model.events.SOSListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
import java.security.SecureRandom;

public class ResidentialBuilding implements Rescuable, Simulatable {

	private Address location;
	private int structuralIntegrity;
	private int fireDamage;
	private int gasLevel;
	private int foundationDamage;
	private ArrayList<Citizen> occupants;
	private Disaster disaster;
	private SOSListener emergencyService;

	public ResidentialBuilding(Address location) {
		this.location = location;
		this.structuralIntegrity = 100;
		occupants = new ArrayList<Citizen>();
	}

	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}

	public void setStructuralIntegrity(int structuralIntegrity) {
		if (structuralIntegrity >= 0 && structuralIntegrity <= 100)
			this.structuralIntegrity = structuralIntegrity;
		else if (structuralIntegrity > 100)
			this.structuralIntegrity = 100;
		else if (structuralIntegrity < 0)
			this.structuralIntegrity = 0;

		if (this.structuralIntegrity == 0)
			deathofOccupants();
	}

	public int getFireDamage() {
		return fireDamage;
	}

	public void setFireDamage(int fireDamage) {
		if (fireDamage >= 0 && fireDamage <= 100)
			this.fireDamage = fireDamage;
		else if (fireDamage > 100)
			this.fireDamage = 100;
		else if (fireDamage < 0)
			this.fireDamage = 0;
	}

	public int getGasLevel() {
		return gasLevel;
	}

	public void setGasLevel(int gasLevel) {
		if (gasLevel >= 0 && gasLevel <= 100)
			this.gasLevel = gasLevel;
		else if (gasLevel > 100)
			this.gasLevel = 100;
		else if (gasLevel < 0)
			this.gasLevel = 0;

		if (this.gasLevel == 100)
			deathofOccupants();
	}

	public int getFoundationDamage() {
		return foundationDamage;
	}

	public void setFoundationDamage(int foundationDamage) {
		if (foundationDamage >= 0 && foundationDamage <= 100)
			this.foundationDamage = foundationDamage;
		else if (foundationDamage > 100)
			this.foundationDamage = 100;
		else if (foundationDamage < 0)
			this.foundationDamage = 0;

		if (this.foundationDamage == 100)
			setStructuralIntegrity(0);
	}

	@Override
	public Address getLocation() {
		return location;
	}

	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}

	@Override
	public Disaster getDisaster() {
		return disaster;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	@Override
	public void cycleStep() {
		if (getFoundationDamage() > 0) {
			SecureRandom random = new SecureRandom();
			int decreaseNumber = random.nextInt(6) + 5;
			setStructuralIntegrity(getStructuralIntegrity() - decreaseNumber);
		}
		if (getFireDamage() > 0 && getFireDamage() < 30)
			setStructuralIntegrity(getStructuralIntegrity() - 3);
		else if (getFireDamage() >= 30 && getFireDamage() < 70)
			setStructuralIntegrity(getStructuralIntegrity() - 5);
		else if (getFireDamage() >= 70 && getFireDamage() <= 100)
			setStructuralIntegrity(getStructuralIntegrity() - 7);
	}

	@Override
	public void struckBy(Disaster d) {
		this.disaster = d;
		emergencyService.receiveSOSCall(this);
	}

	private void deathofOccupants() {
		for (int i = 0; i < occupants.size(); i++)
			occupants.get(i).setHp(0);
	}

}
