package org.angiedev.schoolfinder.geolocation;

/**
 * School is a data model representing a geo-location for an address.  
 * A geo-location is composed of longitude and latitude values.
 * @author Angela Gordon
 */
public class GeoLocation {

	private double latitude;
	private double longitude;

	/**
	 * Default constructor for GeoLocation object
	 */
	public GeoLocation() {
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
