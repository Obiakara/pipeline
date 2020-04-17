package com.softwareHomework.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class HelloController {
	
	@GetMapping
	public ResponseEntity<String> helloWorld(){
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", "Hello Yourself");
		
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
	}

}
