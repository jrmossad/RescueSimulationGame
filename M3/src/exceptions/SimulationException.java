package exceptions;

@SuppressWarnings("serial")
public abstract class SimulationException extends Exception {

	public SimulationException() {
		
	}

	public SimulationException(String message) {
		super(message);
	}

}
