package com.example.calculus.service;


import com.example.calculus.utils.Utils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;




public class CalculusServiceSpelImpl implements CalculusService{

	@Override
	public Double calculate(String input) throws Exception {
		String decodedString = Utils.decodeBase64(input);
		ExpressionParser parser = new SpelExpressionParser();
		return  parser.parseExpression(decodedString).getValue(Double.class);
		
	}
	
	

}
