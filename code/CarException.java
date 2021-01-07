package car;

public class CarException extends Exception{

	/**
	 * CarException constructor with customised message
	 * 
	 * @param message - a simple message explaining why the exception has occurred
	 * @see #CarException()
	 */
	public CarException(String message) {
		super(message);
	}
	
	/**
	 * Default constructor
	 */
	public CarException() {
		super("Something went wrong");
	}
}
