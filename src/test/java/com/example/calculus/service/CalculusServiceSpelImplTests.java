package com.example.calculus.service;

import com.example.calculus.model.Bracket;
import com.example.calculus.model.Expression;
import com.example.calculus.model.Operator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculusServiceSpelImplTests {

	CalculusServiceSpelImpl service = new CalculusServiceSpelImpl();



	@Test
	void whenParsingIsSuccessful_aResultOfTheEvaluationIsReturned() {
		String string = "MiArIDQ=";
		Double serviceResult = null;
		try {
			serviceResult = service.calculate(string);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(serviceResult, 6);
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

		assertEquals(serviceResult, -529);
	}

}
