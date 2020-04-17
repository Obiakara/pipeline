package com.softwareHomework.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.softwareHomework.Impl.PropertiesImpl;
import com.softwareHomework.controller.PropertiesController;
import com.softwareHomework.model.Properties;
//@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemoApplication extends AbstractTest{

	@Autowired
	private PropertiesController controller;
//goo
	@Autowired
	private PropertiesImpl propertiesImpl;
	private String address = "101 Testing Case";
	private String state = "WA";
	private String zip = "0000";
	private String city = "cityTest";

	Properties properties = new Properties();


	@Override
	@Before
	public void setUp() {
		super.setUp();
		properties.setAddress(address);
		properties.setCity(city);
		properties.setState(state);
		properties.setZip(zip);
	}

	@Test
	public void getPropertiesList() throws Exception {
		String uri = "/properties";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Properties[] propertiesList = super.mapFromJson(content, Properties[].class);
		assertTrue(propertiesList.length > 0);
	}

	@Test
	public void getSingleproperty() throws Exception {
		String uri = "/properties/6";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Properties property = super.mapFromJson(content, Properties.class);
		assertTrue(property.getAddress() != null);
	}

	@Test
	public void postProduct() throws Exception {

		String uri = "/properties";

		Properties product = new Properties();
		product.setAddress("202 Test Case 2");
		product.setZip("11111");
		product.setCity("jUnit");
		product.setState("CA");

		String inputJson = super.mapToJson(product);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("apiAuth", "cs4783FTW")
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);

	}


	@Test
	public void putProperty() throws Exception {

		String uri = "/properties/12";

		Properties product = new Properties();
		product.setAddress("8761 Halloway Drive");
		product.setZip("20910");
		product.setCity("Annapolis");
		product.setState("VA");

		String inputJson = super.mapToJson(product);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("apiAuth", "cs4783FTW")
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

	}


	@Test
	public void testController() {

		assertThat(controller).isNotNull();

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

	@Test
	public void postProductWrongKey() throws Exception {

		String uri = "/properties";

		Properties product = new Properties();
		product.setAddress("202 Test Case 2");
		product.setZip("11111");
		product.setCity("jUnit");
		product.setState("CA");

		String inputJson = super.mapToJson(product);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("apiAuth", "cs4783FyyTW")
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(403, status);
	}

	@Test
	public void getSinglepropertyWrongID() throws Exception {
		String uri = "/properties/555";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	public void deletePropertyWrongkey() throws Exception {

		String uri = "/properties/7";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("apiAuth", "cs4783FrrrTW")).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);

	}
	
	@Test
	public void postWrongZip() throws Exception {

		String uri = "/properties";

		Properties product = new Properties();
		product.setAddress("202 Test Case 2");
		product.setZip("949494");
		product.setCity("jUnit");
		product.setState("CA");

		String inputJson = super.mapToJson(product);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("apiAuth", "cs4783FTW")
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}

}
