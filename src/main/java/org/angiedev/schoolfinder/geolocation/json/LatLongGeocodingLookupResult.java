package org.angiedev.schoolfinder.geolocation.json;


import org.angiedev.schoolfinder.geolocation.GeoLocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * LatLongLookupResult is a domain class used to consume the data from the 
 * LatLong.io Geocoding API.  The data returned from the API is in JSON.  Spring's 
 * rest template uses Jackson JSON processing library to translate this JSON data 
 * into a Java object.
 * <p>
 * We are interested in the geo location (latitude and longitude) data returned 
 * by the API.
 * @author Angela Gordon 
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class LatLongGeocodingLookupResult {

	private GeoLocation geoLocation = new GeoLocation();
	private String status; 
	
	public LatLongGeocodingLookupResult(@JsonProperty("lat") double latitude,
			@JsonProperty("lon") double longitude) {
		geoLocation.setLatitude(latitude);
		geoLocation.setLongitude(longitude);
		this.status = "OK";
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