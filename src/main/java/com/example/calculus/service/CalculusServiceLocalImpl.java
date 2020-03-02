package com.example.calculus.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;


import com.example.calculus.model.Bracket;
import com.example.calculus.model.Expression;
import com.example.calculus.model.Operator;
import com.example.calculus.utils.Utils;

public class CalculusServiceLocalImpl implements CalculusService {

	@Override
	public Double calculate(String input) throws Exception {
		List<SimpleEntry<String, Object>> parsedInput = parse(input);

		Expression expression = tokenize(parsedInput);
		if (expression != null) {
			return evaluate(expression);
		} else {
			throw new Exception("Unprocessable input!");
		}

	}

	protected Double evaluate(Expression expression) throws Exception {
		if (expression.getOperand2() == null && expression.getOperand1() instanceof Double) {
			return (Double) expression.getOperand1();
		}

		if (expression.getOperand1() == null && expression.getOperand2() instanceof Double) {
			return (Double) expression.getOperand2();
		}
		if (expression.getOperand2() == null && expression.getOperand1() instanceof Expression) {
			return evaluate((Expression) expression.getOperand1());
		}

		if (expression.getOperand1() == null && expression.getOperand2() instanceof Expression) {
			return evaluate((Expression) expression.getOperand2());
		}

		if (expression.getOperand1() instanceof Expression && expression.getOperand2() instanceof Expression) {
			Object opr1 = evaluate((Expression) expression.getOperand1());
			Object opr2 = evaluate((Expression) expression.getOperand2());
			return evaluate(new Expression(opr1, expression.getOperator(), opr2));
		}

		Double opr1 = (Double) expression.getOperand1();
		Double opr2 = (Double) expression.getOperand2();

		switch (expression.getOperator()) {
		case MULTIPLY:

			return opr1 * opr2;
		case DIVIDE:
			return opr1 / opr2;

		case PLUS:

			return opr1 + opr2;
		case MINUS:

			return opr1 - opr2;
		default:
			throw new Exception("Unknown operator!");
		}

	}

	protected List<SimpleEntry<String, Object>> parse(int position, List<SimpleEntry<String, Object>> result,
			String expression) throws Exception {

		if (position > expression.length()) {
			return result;
		}
		int pos = position;
		char expressionChar = expression.charAt(pos);

		switch (expressionChar) {
		case '(':
			result.add(new SimpleEntry<String, Object>("bracket", Bracket.OPEN));
			pos++;
			break;

		case ')':
			result.add(new SimpleEntry<String, Object>("bracket", Bracket.CLOSE));
			pos++;
			break;

		case '*':
			result.add(new SimpleEntry<String, Object>("operator", Operator.MULTIPLY));
			pos++;
			break;
		case '/':
			result.add(new SimpleEntry<String, Object>("operator", Operator.DIVIDE));
			pos++;
			break;
		case '+':
			result.add(new SimpleEntry<String, Object>("operator", Operator.PLUS));
			pos++;
			break;
		case '-':
			result.add(new SimpleEntry<String, Object>("operator", Operator.MINUS));
			pos++;
			break;
		default:
			StringBuilder buildNumber = new StringBuilder();

			while (pos < expression.length() && (Character.isDigit(expressionChar) || expressionChar == '.' || expressionChar == ',') ) {
				buildNumber.append(expressionChar);
				pos++;
				if (pos < expression.length()) {
					expressionChar = expression.charAt(pos);
				}
			}

			if (buildNumber.length() == 0) {
				throw new Exception("Unknown character "+expressionChar);
			} else {
				try{
					result.add(new SimpleEntry<String, Object>("constant", Double.valueOf(buildNumber.toString())));
				}catch (Exception ex){
					throw new Exception("Invalid input!");
				}

			}

		}
		
		if (pos >= expression.length()) {
			return result;
		}else {
			return parse(pos, result, expression);
		}

	}

	protected List<SimpleEntry<String, Object>> parse(String expression) throws Exception {

		String decodedString = Utils.decodeBase64(expression);

		// remove white spaces
		decodedString = decodedString.replaceAll("\\s+", "");

		return parse(0, new ArrayList<>(), decodedString);

	}

	protected Expression tokenize(List<SimpleEntry<String, Object>> expressionList) throws Exception {

		if (expressionList.size() == 0 || expressionList.size() == 2) {
			throw new Exception("Unprocessable input!");
		}

		if (expressionList.size() == 1) {
			SimpleEntry<String, Object> top = expressionList.get(0);
			if (top.getKey().contentEquals("constant")) {
				return new Expression(Double.valueOf(top.getValue().toString()), null, null);
			} else {
				throw new Exception("Unprocessable input!");
			}

		}

		if (expressionList.size() == 3) {
			SimpleEntry<String, Object> top = expressionList.get(1);
			if (top.getKey().contentEquals("operator")) {

				Double operand1 = Double.valueOf(expressionList.get(0).getValue().toString());
				Double operand2 = Double.valueOf(expressionList.get(2).getValue().toString());
				Operator op = (Operator) top.getValue();

				return new Expression(operand1, op, operand2);
			} 

		}

		List<Expression> noBrackets = removeBrackets(expressionList);

		List<Expression> multiDivs = collupseOperators(noBrackets, Arrays.asList(Operator.MULTIPLY, Operator.DIVIDE));

		List<Expression> addSubs = collupseOperators(multiDivs, Arrays.asList(Operator.PLUS, Operator.MINUS));

		return new Expression(addSubs.get(0).getOperand1(), addSubs.get(0).getOperator(), addSubs.get(0).getOperand2());
	}

	private List<Expression> collupseOperators(List<Expression> expressionList, List<Operator> operators) {
		List<Expression> result = new ArrayList<>();
		if (expressionList.size() == 0) {
			return expressionList;
		}
		if (expressionList.size() >1) {
			Expression top = expressionList.get(1);
			if (top.getOperator() != null && operators.contains(top.getOperator())) {
				result.add(new Expression(expressionList.get(0), top.getOperator(), expressionList.get(2)));
				expressionList.removeAll(expressionList.subList(0, 3));
				
				result.addAll(expressionList);
				
				return collupseOperators(result, operators);
			}else {
				result.add(expressionList.get(0));
				expressionList.remove(0);
			}
			result.addAll(collupseOperators(expressionList, operators));
		}else {
			result.add(expressionList.get(0));
		}
		
//		if (expressionList.size() == 1) {
//			result.add(expressionList.get(0));
//		} else if (expressionList.size() == 3) {
//			result.add(
//					new Expression(expressionList.get(0), expressionList.get(1).getOperator(), expressionList.get(2)));
//		} else {
//
//			Expression top = expressionList.get(1);
//			if (top.getOperator() != null && operators.contains(top.getOperator())) {
//				result.add(new Expression(expressionList.get(0), top.getOperator(), expressionList.get(2)));
//			} else {
//				result.addAll(expressionList.subList(0, 2));
//			}
//
//			expressionList.removeAll(expressionList.subList(0, 2));
//			result.addAll(collupseOperators(expressionList, operators));
//		}

		return result;
	}

	private List<Expression> removeBrackets(List<SimpleEntry<String, Object>> expressionList) throws Exception {
		List<Expression> result = new ArrayList<>();
		if (expressionList.size() == 0) {
			return result;
		}
		SimpleEntry<String, Object> top = expressionList.get(0);
		if (top.getKey().contentEquals("bracket") && top.getValue() == Bracket.OPEN) {
			SimpleEntry<List<SimpleEntry<String, Object>>, List<SimpleEntry<String, Object>>> evaluated = evaluateBrackets(
					new ArrayList<>(), expressionList.subList(1, expressionList.size()), 0);

			result.add(tokenize(evaluated.getKey()));
			result.addAll(removeBrackets(evaluated.getValue()));
			return result;
		}
		if (top.getKey().contentEquals("constant")) {
			result.add(new Expression(Double.valueOf(top.getValue().toString()), null, null));
		} else {
			result.add(new Expression(null, (Operator) top.getValue(), null));
		}
		if (expressionList.size() > 1) {
			result.addAll(removeBrackets(expressionList.subList(1, expressionList.size())));
		}

		return result;
	}

	private SimpleEntry<List<SimpleEntry<String, Object>>, List<SimpleEntry<String, Object>>> evaluateBrackets(
			List<SimpleEntry<String, Object>> left, List<SimpleEntry<String, Object>> right, int depth) {
		SimpleEntry<String, Object> top = right.get(0);

		if (top.getKey().contentEquals("bracket") && top.getValue() == Bracket.CLOSE && depth == 0) {
			Collections.reverse(left);
			right.remove(0);
			return new SimpleEntry<List<SimpleEntry<String, Object>>, List<SimpleEntry<String, Object>>>(left, right);
		}

		if (top.getKey().contentEquals("bracket") && top.getValue() == Bracket.CLOSE && depth != 0) {
			left.add(0, top);
			return evaluateBrackets(left, right.subList(1, right.size()), depth - 1);
		}

		if (top.getKey().contentEquals("bracket") && top.getValue() == Bracket.OPEN) {

			left.add(0, top);
			return evaluateBrackets(left, right.subList(1, right.size()), depth + 1);
		}
		if (left.size() > 0) {
			left.add(0, top);
		} else {
			left.add(top);
		}

		if (right.size() == 1) {
			return new SimpleEntry<List<SimpleEntry<String, Object>>, List<SimpleEntry<String, Object>>>(left,
					new ArrayList<>());
		} else {

			return evaluateBrackets(left, right.subList(1, right.size()), depth);
		}

	}

}
