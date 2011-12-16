package be.ipl.tc.exceptions;

@SuppressWarnings("serial")
public class TentativeCrevaisonException extends RuntimeException {
	
	public TentativeCrevaisonException() { }
	
	public TentativeCrevaisonException(String msg) {
		super(msg);
	}
	
	public TentativeCrevaisonException(Throwable cause) {		
		super(cause);	
	}

}
