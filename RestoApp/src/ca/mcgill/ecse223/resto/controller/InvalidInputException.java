package ca.mcgill.ecse223.resto.controller;

public class InvalidInputException extends Exception {
	
	public InvalidInputException(String errorMessage) {
		super(errorMessage);
	}
	
}
