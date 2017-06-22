package org.angiedev.schoolfinder.geolocation;

import java.io.IOException;

import org.angiedev.schoolfinder.geolocation.LatLongGeoLocatorProperties;
import org.angiedev.schoolfinder.geolocation.GeoLocation;
import org.angiedev.schoolfinder.geolocation.json.LatLongGeocodingLookupResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

/**
 * LatLongGeoLocationService is a service which enables a user to lookup 
 * a geolocation (latitude/longitude) for an address.  This service uses 
 * the LatLon.io geocoding api.
 * @author Angela Gordon
 *
 */
@Component(value="LatLong")
public class LatLongGeoLocator implements GeoLocator {
	private final RestOperations rest;
	private final String latLongGeoLocatorUrl;
	
	public LatLongGeoLocator( final RestTemplateBuilder builder, final LatLongGeoLocatorProperties props ) {
		 rest = builder.setReadTimeout(props.getReadTimeout())
		 	.setConnectTimeout(props.getConnectTimeout())
		 	.build();
		 latLongGeoLocatorUrl = props.getLatLongGeoLocatorUrl();
	 }
	 	 
	@Override
	public GeoLocation getGeoLocationForAddress(String address, String city, 
			String stateCode, String zip) throws IOException {
					
		LatLongGeocodingLookupResult result = rest.getForObject(latLongGeoLocatorUrl, 
			LatLongGeocodingLookupResult.class, address, city, stateCode, zip);
		
		if (result.getStatus().equals("OK")) {
			return result.getGeoLocation();
		} else {
			throw new IOException("Unable to get GeoLocation for address: " + address 
				+ "," + city + "," + stateCode + ".  LatLongGeoCode API returned status: " +
				result.getStatus());
		}
	}
}
