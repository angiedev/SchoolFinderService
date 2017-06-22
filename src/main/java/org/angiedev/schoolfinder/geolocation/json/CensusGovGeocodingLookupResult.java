package org.angiedev.schoolfinder.geolocation.json;


import java.util.List;

import org.angiedev.schoolfinder.geolocation.GeoLocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CensusGovGeocodingLookupResult is a domain class used to consume the data from the 
 * CensusGov Geocoding API.  The data returned from the API is in JSON.  Spring's 
 * rest template uses Jackson JSON processing library to translate this JSON data 
 * into a Java object.
 * <p>
 * We are interested in the geo location (latitude and longitude) data returned 
 * by the API.
 * @author Angela Gordon 
 *
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class CensusGovGeocodingLookupResult {

	private GeoLocation geoLocation = new GeoLocation();
	private String status; 
	
	public CensusGovGeocodingLookupResult(@JsonProperty("result") Result result) {
	
		if ( result.getAddresses().size() == 0) {
			this.status = "INVALID_ADDRESS: latitude/longitude values were not found";
		} else {
			geoLocation.setLatitude(result.getAddresses().get(0).getCoordinates().getLatitude());
			geoLocation.setLongitude(result.getAddresses().get(0).getCoordinates().getLongitude());
			this.status = "OK";
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

}

@JsonIgnoreProperties(ignoreUnknown=true)
class Result {
	List<AddressMatch> addresses;
	
	public Result(@JsonProperty("addressMatches") List<AddressMatch> addresses) {
		this.addresses = addresses;
	}
	
	public List<AddressMatch> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(List<AddressMatch> addresses) {
		this.addresses = addresses;
	}
}

@JsonIgnoreProperties(ignoreUnknown=true)
class AddressMatch {
	Coordinates coordinates;
	public AddressMatch(@JsonProperty("coordinates") Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
}
@JsonIgnoreProperties(ignoreUnknown=true)
class Coordinates {
	double latitude;
	double longitude;
	
	public Coordinates(@JsonProperty("y") double latitude,
			@JsonProperty("x") double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}

