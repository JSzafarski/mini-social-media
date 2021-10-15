package socialmedia;

/**
 * Thrown when attempting to use an account ID that does not exit in the system.
 *
 */
public class AccountIDNotRecognisedException extends Exception {

	/**
	 * Constructs an instance of the exception containing the default message argument
	 */

	public AccountIDNotRecognisedException() {
		super("Not found an ID corresponding to any account on the system");
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public AccountIDNotRecognisedException(String message) {
		super(message);
	}

}
