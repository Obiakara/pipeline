package com.softwareHomework.demo;

import static org.junit.Assert.*;

import org.junit.After;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.softwareHomework.Impl.PropertiesImpl;
import com.softwareHomework.model.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	private PropertiesImpl propertiesImpl;
	private String address = "101 Testing Case";
	private String state = "WA";
	private String zip = "0000";
	private String city = "cityTest";
	
	Properties properties = new Properties();
	
	@Before
	public void setUp(){
		
		
		properties.setAddress(address);
		properties.setCity(city);
		properties.setState(state);
		properties.setZip(zip);
	}

	@Test
	public void testInsert(){
		int result = propertiesImpl.insertIntoProperties(address, city, state, zip);
		assertThat(result).isEqualTo(1);
	
	}

	@After
	public void afterSetUp(){
		
		propertiesImpl.deleteProperty(properties);
		
	}

}
