package com.example.calculus.dto;

public class CalculusErrorResponse {
	
	private Boolean error;
	private String message;
	public CalculusErrorResponse() {
		super();
	}
	public CalculusErrorResponse(Boolean error, String message) {
		super();
		this.error = error;
		this.message = message;
	}
	public Boolean getError() {
		return error;
	}
	public String getMessage() {
		return message;
	}
	
	

}
