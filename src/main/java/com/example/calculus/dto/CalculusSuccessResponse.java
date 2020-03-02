package com.example.calculus.dto;

public class CalculusSuccessResponse {
	
	private Boolean error;
	private Double result;
	
	
	
	public CalculusSuccessResponse() {
		super();
	}
	public CalculusSuccessResponse(Boolean error, Double result) {
		this.error = error;
		this.result = result;
	}
	public Boolean getError() {
		return error;
	}
	public Double getResult() {
		return result;
	}
	

}
