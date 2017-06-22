package org.angiedev.schoolfinder.geolocation.json;

import java.util.List;

import org.angiedev.schoolfinder.geolocation.GeoLocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GoogleGeocodingLookupResult is a domain class used to consume the data from the 
 * Google Geocoding API.  The data returned from the API is in JSON.  Spring's 
 * rest template uses Jackson JSON processing library to translate this JSON data 
 * into a Java object.
 * <p>
 * We are interested in the geo location (latitude and longitude) data returned 
 * by the API.
 * @author Angela Gordon 
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleGeocodingLookupResult {

	private String status; 
	private GoogleGeoLocation geoLocation;
	
	public GoogleGeocodingLookupResult(@JsonProperty("status") String status,
			@JsonProperty("results") List<GoogleGeoLocation> geoLocationData) {
		this.setStatus(status);
		if (geoLocationData.size() > 0) {
			this.setGeoLocation(geoLocationData.get(0));
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GoogleGeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GoogleGeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	
}

@JsonIgnoreProperties(ignoreUnknown=true)
class GoogleGeoLocation extends GeoLocation {

	private GoogleGeometry geometry; 
	
	public GoogleGeoLocation(@JsonProperty("geometry") GoogleGeometry geometry) {
		this.geometry = geometry;
	}

	public GoogleGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(GoogleGeometry geometry) {
		this.geometry = geometry;
	}
	
	@Override
	public double getLatitude() {
		return geometry.getLocation().getLatitude();
	}
	
	@Override 
	public double getLongitude() {
		return geometry.getLocation().getLongitude();
	}
	
}

@JsonIgnoreProperties(ignoreUnknown=true)
class GoogleGeometry {
	private GoogleLocation location;
	
	public GoogleGeometry(@JsonProperty("location") GoogleLocation location) {
		this.location = location;	
	}

	public GoogleLocation getLocation() {
		return location;
	}

	public void setLocation(GoogleLocation location) {
		this.location = location;
	}
}


@JsonIgnoreProperties(ignoreUnknown=true)
class GoogleLocation {
	
	private double latitude;
	private double longitude;
	
	public GoogleLocation(@JsonProperty("lat") double latitude,
			@JsonProperty("lng") double longitude) {
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

