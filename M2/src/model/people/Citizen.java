package model.people;

import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
import model.disasters.Disaster;
import model.events.SOSListener;
import model.events.WorldListener;

public class Citizen implements Rescuable, Simulatable {

	private CitizenState state;
	private Disaster disaster;
	private String name;
	private String nationalID;
	private int age;
	private int hp;
	private int bloodLoss;
	private int toxicity;
	private Address location;
	private SOSListener emergencyService;
	private WorldListener worldListener;

	public Citizen(Address location, String nationalID, String name, int age, WorldListener worldListener) {
		this.name = name;
		this.nationalID = nationalID;
		this.age = age;
		this.location = location;
		this.state = CitizenState.SAFE;
		this.hp = 100;
		this.worldListener = worldListener;
	}

	public CitizenState getState() {
		return state;
	}

	public void setState(CitizenState state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if (hp >= 0 && hp <= 100)
			this.hp = hp;
		else if (hp < 0)
			this.hp = 0;
		else if (hp > 100)
			this.hp = 100;

		if (this.hp == 0)
			setState(CitizenState.DECEASED);
	}

	public int getBloodLoss() {
		return bloodLoss;
	}

	public void setBloodLoss(int bloodLoss) {
		if (bloodLoss >= 0 && bloodLoss <= 100)
			this.bloodLoss = bloodLoss;
		else if (bloodLoss < 0)
			this.bloodLoss = 0;
		else if (bloodLoss > 100)
			this.bloodLoss = 100;

		if (this.bloodLoss == 100)
			setHp(0);
	}

	public int getToxicity() {
		return toxicity;
	}

	public void setToxicity(int toxicity) {
		if (toxicity >= 0 && toxicity <= 100)
			this.toxicity = toxicity;
		else if (toxicity > 100)
			this.toxicity = 100;
		else if (toxicity < 0)
			this.toxicity = 0;

		if (this.toxicity == 100)
			setHp(0);
	}

	@Override
	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	@Override
	public Disaster getDisaster() {
		return disaster;
	}

	public String getNationalID() {
		return nationalID;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	@Override
	public void cycleStep() {
		int loss = getToxicity();
		if (loss > 0 && loss < 30)
			setHp(getHp() - 5);
		else if (loss >= 30 && loss < 70)
			setHp(getHp() - 10);
		else if (loss >= 70 && loss <= 100)
			setHp(getHp() - 15);

		loss = getBloodLoss();
		if (loss > 0 && loss < 30)
			setHp(getHp() - 5);
		else if (loss >= 30 && loss < 70)
			setHp(getHp() - 10);
		else if (loss >= 70 && loss <= 100)
			setHp(getHp() - 15);
	}

	@Override
	public void struckBy(Disaster d) {
		this.disaster = d;
		setState(CitizenState.IN_TROUBLE);
		emergencyService.receiveSOSCall(this);
	}

}
