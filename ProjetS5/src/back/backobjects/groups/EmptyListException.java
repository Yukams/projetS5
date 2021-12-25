package back.backobjects.groups;

public class EmptyListException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyListException() {
	}

	public EmptyListException(String message) {
		super(message);
	}

	public EmptyListException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyListException(Throwable cause) {
		super(cause);
	}
}
