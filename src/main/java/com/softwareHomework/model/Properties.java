package com.softwareHomework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;


//@ApiModel(description = "Details of a Property")
@Entity
@Table(name = "properties")
public class Properties {
	
	@Schema(description = "Unique identifier of the property.", 
            example = "1", required = true)

	//@ApiModelProperty(notes = "The Unique ID of property")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	//@ApiModelProperty(notes = "The address of a property")
	@Schema(description = "Address of property.", 
            example = "5655 UTSA Blvd", required = true)
	private String address;
	
	@Schema(description = "Zip code of property.", 
            example = "78249", required = true)
	private String zip;
	
	@Schema(description = "City of property.", 
            example = "Austin", required = true)
	private String city;
	
	@Schema(description = "State of property.", 
            example = "TX", required = true)
	private String state;
	
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Properties [id=" + id + ", address=" + address + ", zip=" + zip + ", city=" + city + ", state=" + state
				+ "]";
	}

	
}
