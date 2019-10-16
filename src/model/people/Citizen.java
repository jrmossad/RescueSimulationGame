package model.people;

import model.disasters.Disaster;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class Citizen implements Rescuable, Simulatable{

	private CitizenState state;
	private Disaster disaster;
	private Address location;
	private String nationalID;
	private String name;
	private int age;
	private int hp;
	private int bloodLoss;
	private int toxicity;
	
	public Citizen(Address location, String nationalID, String name, int age) {
		this.location = location;
		this.nationalID = nationalID;
		this.name = name;
		this.age = age;
		this.state = CitizenState.SAFE;
		this.hp = 100;
		this.bloodLoss = 0;
		this.toxicity = 0;
	}	
	
	public CitizenState getState() {
		return state;
	}

	public void setState(CitizenState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getBloodLoss() {
		return bloodLoss;
	}

	public void setBloodLoss(int bloodLoss) {
		this.bloodLoss = bloodLoss;
	}

	public int getToxicity() {
		return toxicity;
	}

	public void setToxicity(int toxicity) {
		this.toxicity = toxicity;
	}

	public Disaster getDisaster() {
		return disaster;
	}

	public String getNationalID() {
		return nationalID;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
	
}
