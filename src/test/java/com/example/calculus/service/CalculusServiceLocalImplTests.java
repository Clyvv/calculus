package com.example.calculus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.calculus.model.Bracket;
import com.example.calculus.model.Expression;
import com.example.calculus.model.Operator;

@SpringBootTest
public class CalculusServiceLocalImplTests {

	CalculusServiceLocalImpl service = new CalculusServiceLocalImpl();

	@Test
	void passingStringExpressions() {
		String string = "MiArIDQ=";
		List<SimpleEntry<String, Object>> serviceResult = null;
		try {
			serviceResult = service.parse(string);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(serviceResult.get(0).getKey(), "constant");
		assertEquals(serviceResult.get(0).getValue(), 2.0);
		assertEquals(serviceResult.get(1).getKey(), "operator");
		assertEquals(serviceResult.get(1).getValue(), Operator.PLUS);
		assertEquals(serviceResult.get(2).getKey(), "constant");
		assertEquals(serviceResult.get(2).getValue(), 4.0);
	}

	@Test
	void passingStringExpressions2() {
		String string = "MiArIDQw";
		List<SimpleEntry<String, Object>> serviceResult = null;
		try {
			serviceResult = service.parse(string);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(serviceResult.get(0).getKey(), "constant");
		assertEquals(serviceResult.get(0).getValue(), 2.0);
		assertEquals(serviceResult.get(1).getKey(), "operator");
		assertEquals(serviceResult.get(1).getValue(), Operator.PLUS);
		assertEquals(serviceResult.get(2).getKey(), "constant");
		assertEquals(serviceResult.get(2).getValue(), 40.0);
	}

	@Test
	void complexStringExpressionSuccessfullyParsed() {
		// 2 * (23/(33))- 23 * (23)
		String string = "MiAqICgyMy8oMzMpKS0gMjMgKiAoMjMp";
		List<SimpleEntry<String, Object>> serviceResult = null;
		try {
			serviceResult = service.parse(string);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(serviceResult.get(0).getValue(), 2.0);
		assertEquals(serviceResult.get(1).getValue(), Operator.MULTIPLY);
		assertEquals(serviceResult.get(2).getValue(), Bracket.OPEN);
		assertEquals(serviceResult.get(3).getValue(), 23.0);
		assertEquals(serviceResult.get(4).getValue(), Operator.DIVIDE);
		assertEquals(serviceResult.get(5).getValue(), Bracket.OPEN);
		assertEquals(serviceResult.get(6).getValue(), 33.0);
		assertEquals(serviceResult.get(7).getValue(), Bracket.CLOSE);
		assertEquals(serviceResult.get(8).getValue(), Bracket.CLOSE);
		assertEquals(serviceResult.get(9).getValue(), Operator.MINUS);
		assertEquals(serviceResult.get(10).getValue(), 23.0);
		assertEquals(serviceResult.get(11).getValue(), Operator.MULTIPLY);
		assertEquals(serviceResult.get(12).getValue(), Bracket.OPEN);
		assertEquals(serviceResult.get(13).getValue(), 23.0);
		assertEquals(serviceResult.get(14).getValue(), Bracket.CLOSE);
	}

	@Test
	void tokenizeExpressionStringWith2ConstantsAndOneOperator() {
		// 2+4
		List<SimpleEntry<String, Object>> expressionList = new ArrayList<>();
		expressionList.add(new SimpleEntry<String, Object>("constant", 2));
		expressionList.add(new SimpleEntry<String, Object>("operator", Operator.PLUS));
		expressionList.add(new SimpleEntry<String, Object>("constant", 4));
		Expression serviceResult = null;
		try {
			serviceResult = service.tokenize(expressionList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(serviceResult.getOperand1(), 2.0);
		assertEquals(serviceResult.getOperator(), Operator.PLUS);
		assertEquals(serviceResult.getOperand2(), 4.0);
	}

	@Test
	void whenParsingIsSuccessful_aResultOfTheEvaluationIsReturned() {
		String string = "MiArIDQ=";
		Double serviceResult = null;
		try {
			serviceResult = service.calculate(string);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(serviceResult, 6.0);
	}

	@Test
	void whenParsingIsSuccessful_aResultOfTheEvaluationIsReturned2() {
		String string = "MiAqICgyMy8oMzMpKS0gMjMgKiAoMjMp";
		Double serviceResult = null;
		try {
			serviceResult = service.calculate(string);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(serviceResult, -527.6060606060606);
	}

}
