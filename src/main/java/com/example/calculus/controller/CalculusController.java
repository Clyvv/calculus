package com.example.calculus.controller;

import java.io.UnsupportedEncodingException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.calculus.dto.CalculusErrorResponse;
import com.example.calculus.dto.CalculusSuccessResponse;
import com.example.calculus.service.CalculusService;



@RestController
@RequestMapping("/api/v1/calculus")
public class CalculusController {
	
	@Autowired
	private CalculusService calculusService;
	
	@GetMapping
    public ResponseEntity<?> get(@RequestParam(name = "query") String query) throws UnsupportedEncodingException {

		try {
			Double result = calculusService.calculate(query);
			return ResponseEntity.ok().body(new CalculusSuccessResponse(false, result));
		}catch (Exception ex){
			return ResponseEntity.unprocessableEntity().body(new CalculusErrorResponse(true, ex.getMessage()));
	}
		
    }

}
