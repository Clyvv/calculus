package com.example.calculus.model;


public class Expression {
	Object operand1;
	Object operand2;
	Operator operator;
	
	
	public Expression() {
		
	}


	public Expression(Object operand1, Operator operator, Object operand2) {
		super();
		this.operand1 = operand1;
		this.operator = operator;
		this.operand2 = operand2;
		
	}


	public Object getOperand1() {
		return operand1;
	}


	public Object getOperand2() {
		return operand2;
	}


	public Operator getOperator() {
		return operator;
	}
}
