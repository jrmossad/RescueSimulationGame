package exceptions;

import model.disasters.Disaster;

@SuppressWarnings("serial")
public abstract class DisasterException extends SimulationException {

	private Disaster disaster;
	
	public Disaster getDisaster() {
		return disaster;
	}

	public DisasterException(Disaster disaster) {
		this.disaster = disaster;
	}

	public DisasterException(Disaster disaster, String message) {
		super(message);
		this.disaster = disaster;
	}

}
