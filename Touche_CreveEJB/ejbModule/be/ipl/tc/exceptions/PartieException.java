package be.ipl.tc.exceptions;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PartieException extends RuntimeException {
	
	public PartieException() { }
	
	public PartieException(String msg) {
		super(msg);
	}

}
