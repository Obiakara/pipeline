package com.softwareHomework.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.softwareHomework.controller.PropertiesController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
	
	@Autowired
	private PropertiesController controller;
	

	@Test
	public void testController() {
		
		assertThat(controller).isNotNull();
		
	}

}
