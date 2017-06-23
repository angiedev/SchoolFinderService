package org.angiedev.schoolfinder.geolocation;

import java.io.IOException;

import org.angiedev.schoolfinder.geolocation.GoogleGeoLocatorProperties;
import org.angiedev.schoolfinder.geolocation.json.GoogleGeocodingLookupResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;



/**
 * GoogleGeoLocationService is a service which enables a user to lookup 
 * a geolocation (latitude/longitude) for an address.  This service uses 
 * the Google Maps geocoding api.
 * @author Angela Gordon
 *
 */
@Component(value="Google")
public class GoogleGeoLocator implements GeoLocator {

	private final RestOperations rest;
	private final String googleGeoLocatorUrl;
	
	public GoogleGeoLocator( final RestTemplateBuilder builder, final GoogleGeoLocatorProperties props ) {
		 rest = builder.setReadTimeout(props.getReadTimeout())
		 	.setConnectTimeout(props.getConnectTimeout())
		 	.build();
		 googleGeoLocatorUrl = props.getUrl();
	 }
	 
	@Override
	public GeoLocation getGeoLocationForAddress(String address, String city, String stateCode, String zip) throws IOException {
		
		// Remove leading #'s from street address since google apis don't support them
		if (address.startsWith("#")) {
			address = address.substring(1);
		}
		
		GoogleGeocodingLookupResult result = rest.getForObject(googleGeoLocatorUrl, 
			GoogleGeocodingLookupResult.class, address, city, stateCode, zip);
		
		if (result.getStatus().equals("OK")) {
			return result.getGeoLocation();
		} else {
			throw new IOException("Unable to get GeoLocation for address: " + address 
			+ "," + city + "," + stateCode + ".  GoogleGeoCode API returned status: " +
			result.getStatus());
		}
	}
}
