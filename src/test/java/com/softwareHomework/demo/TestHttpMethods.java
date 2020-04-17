package com.softwareHomework.demo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.softwareHomework.model.Properties;

//@SpringBootTest
public class TestHttpMethods extends AbstractTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
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
//	      String content = mvcResult.getResponse().getContentAsString();
//	      System.out.println(content);
//	      assertEquals(content, "message:added");
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
//	      String content = mvcResult.getResponse().getContentAsString();
//	      System.out.println(content);
//	      assertEquals(content, "message:added");
	   }
	  
//	  
//	   @Test
//	   public void deleteProduct() throws Exception {
//	      String uri = "/properties/18";
//	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
//	    		  .contentType(MediaType.APPLICATION_JSON_VALUE)
//	    		  .header("apiAuth", "cs4783FTW")).andReturn();
//	      int status = mvcResult.getResponse().getStatus();
//	      assertEquals(200, status);
////	      String content = mvcResult.getResponse().getContentAsString();
////	      assertEquals(content, "Product is deleted successsfully");
//	   }

}
