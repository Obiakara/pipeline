package com.softwareHomework.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softwareHomework.Impl.PropertiesImpl;
import com.softwareHomework.model.PostRequestBody;
import com.softwareHomework.model.Properties;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/properties")
@Tag(name = "openApi", description = "the Properties API")
public class PropertiesController {

	private final String apiKey = "cs4783FTW";

	private final Logger LOGGER = LogManager.getLogger(PropertiesController.class);

	@Autowired
	private PropertiesImpl propertiesImpl;
	
	@Operation(summary = "Find Properties", description = "Get all properties from database", tags = { "openApi" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", 
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Properties.class)))) })	
	@GetMapping
	public ResponseEntity<List<Properties>> getproperties(){

		LOGGER.info("Properties List Requested");

		List<Properties> properties = propertiesImpl.findAll();

		if(properties.size() >= 1){

			LOGGER.info("Data retrieved from database succesfuly");

			return new ResponseEntity<List<Properties>>(properties, HttpStatus.OK);
		}

		LOGGER.info("Result not found");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Post to Properties", description = "Post a property to database", tags = { "openApi" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
			@ApiResponse(responseCode = "403", description = "Forbiddenn"),
			@ApiResponse(responseCode = "404", description = "Not Found"),	
			@ApiResponse(responseCode = "201", description = "successful operation", 
			content = @Content(array = @ArraySchema(schema = @Schema(implementation = Properties.class)))
					) })
	@PostMapping
	public ResponseEntity<String> postProperties(@RequestHeader String apiAuth, @RequestBody PostRequestBody properties){

		JSONObject jsonObject = new JSONObject();

		if(!apiAuth.equals(apiKey)){

			jsonObject.put("message", "Invalid api key");
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.FORBIDDEN);
		}
		LOGGER.info(properties.toString());

		String address = properties.getAddress();
		String city = properties.getCity();
		String state = properties.getState();
		String zip = properties.getZip();

		if(address.length() > 200){


			jsonObject.put("Message", "Address is more than 200 characters");

			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.NOT_FOUND);
		}

		if(city.length() > 50){

			jsonObject.put("Message", "City is more than 50 characters");

			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.NOT_FOUND);
		}

		if(state.length() != 2){


			jsonObject.put("Message", "State is more than less than 2 characters");

			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.NOT_FOUND);
		}

		if(zip.length() > 5 || zip.length() > 10 ){

			jsonObject.put("Message", "Zip is not within range characters");
			System.out.println(jsonObject.toString());

			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.NOT_FOUND);
		}


		int insert = propertiesImpl.insertIntoProperties(address, city, state, zip);

		if(insert == 1){//ensure insert query worked
			jsonObject.put("message", "added");
		}

		LOGGER.info("Item posted succesfully");
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.CREATED);

	}


	@Operation(summary = "Get Properties by ID", description = "Get a property from database by ID", tags = { "openApi" })
	@ApiResponses(value = {

			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "403", description = "Forbiddenn"),
			@ApiResponse(responseCode = "404", description = "Not Found"),	
			@ApiResponse(responseCode = "200", description = "successful operation", 
			content = @Content(array = @ArraySchema(schema = @Schema(implementation = Properties.class)))
					) })
	@GetMapping("/{id}")
	public ResponseEntity<Properties> getPropertyByID(
			@PathVariable String id){
		
		JSONObject json = new JSONObject();

		if(!id.isEmpty()){

			try{

				Integer.parseInt(id);

			}catch (NumberFormatException ex){

				LOGGER.error("Invalid URI Parameter, Enter a valid Number");
				return new ResponseEntity<Properties>(HttpStatus.BAD_REQUEST);
			}

		}

		LOGGER.info("Property ID requested = + " + id);


		try{

			Optional<Properties> optionalProperty = propertiesImpl.findById(Integer.parseInt(id));
			Properties properties = optionalProperty.get();

			if(properties.getAddress() != null){

				return new ResponseEntity<Properties>(properties, HttpStatus.OK);

			}


			return new ResponseEntity<Properties>(HttpStatus.NOT_FOUND);

		}catch(Exception ex){

			LOGGER.error("User with specified ID not found");
			return new ResponseEntity<Properties>(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(summary = "Delete Properties by ID", description = "Delete a property from database by ID", tags = { "openApi" })
	@ApiResponses(value = {

			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "403", description = "Forbiddenn"),
			@ApiResponse(responseCode = "404", description = "Not Found"),	
			@ApiResponse(responseCode = "200", description = "successful operation" 
			//content = @Content(array = @ArraySchema(schema = @Schema(implementation = Properties.class)))
					) })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePropertyByID(@RequestHeader String apiAuth, 
			@PathVariable String id){

		JSONObject jsonObject = new JSONObject();

		if(!id.isEmpty()){

			try{

				Integer.parseInt(id);

			}catch (NumberFormatException ex){

				LOGGER.error("Invalid URI Parameter, Enter a valid Number");
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}

		}

		if(!apiAuth.equals(apiKey) || apiAuth == null || apiAuth.isEmpty()){

			jsonObject.put("message", "Invalid api key");
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.FORBIDDEN);
		}


		try{

			propertiesImpl.deleteById(Integer.parseInt(id));

		}catch(Exception ex){

			jsonObject.put("message", "Unable to delete property with the specified ID");
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.NOT_FOUND);

		}

		jsonObject.put("message", "Property deleted");
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
	}


	@Operation(summary = "update Properties by ID", description = "update a property from database by ID", tags = { "openApi" })
	@ApiResponses(value = {

			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Not Found"),	
			@ApiResponse(responseCode = "200", description = "successful operation", 
			content = @Content(array = @ArraySchema(schema = @Schema(implementation = Properties.class)))
					) })
	@PutMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<String> updatePropertyByID(@RequestHeader String apiAuth,  
			@PathVariable String id, @RequestBody PostRequestBody properties){
		JSONObject jsonObject = new JSONObject();

		if(!id.isEmpty()){

			try{

				Integer.parseInt(id);

			}catch (NumberFormatException ex){

				LOGGER.error("Invalid URI Parameter, Enter a valid Number");
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}

		}

		if(!apiAuth.equals(apiKey) || apiAuth == null || apiAuth.isEmpty()){

			jsonObject.put("message", "Invalid api key");
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
		}

		try{

			Optional<Properties> properties2 = propertiesImpl.findById(Integer.valueOf(id));

			Properties tempProperties = properties2.get();

			tempProperties.setAddress(properties.getAddress());
			tempProperties.setCity(properties.getCity());
			tempProperties.setState(properties.getState());
			tempProperties.setZip(properties.getZip());

			Properties updatedProperties = propertiesImpl.update(tempProperties);

		}catch (Exception ex){

			LOGGER.error("Record ID not found to update");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		jsonObject.putOnce("message", "Property updated");

		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);

	}

}
