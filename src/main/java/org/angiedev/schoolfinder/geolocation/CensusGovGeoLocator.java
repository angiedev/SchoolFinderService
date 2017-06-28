package org.angiedev.schoolfinder.geolocation;

import java.io.IOException;

import org.angiedev.schoolfinder.geolocation.CensusGovGeoLocatorProperties;
import org.angiedev.schoolfinder.geolocation.GeoLocation;
import org.angiedev.schoolfinder.geolocation.GeoLocator;
import org.angiedev.schoolfinder.geolocation.json.CensusGovGeocodingLookupResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;


/**
 * CensusGovGeoLocationService is a service which enables a user to lookup 
 * a geolocation (latitude/longitude) for an address.  This service uses 
 * the US Census Bureau's geocoding api. (https://geocoding.geo.census.gov/)
 * @author Angela Gordon
 *
 */
@Component(value="CensusGov")
public class CensusGovGeoLocator implements GeoLocator {
	
	private final RestOperations rest;
	private final String censusGovGeoLocatorUrl;
	
	public CensusGovGeoLocator(final RestTemplateBuilder builder, final CensusGovGeoLocatorProperties props) {
		rest = builder.setConnectTimeout(props.getConnectTimeout())
			.setReadTimeout(props.getReadTimeout())
			.build();
		censusGovGeoLocatorUrl = props.getUrl();
	}
	
	@Override
	public GeoLocation getGeoLocationForAddress(String address, String city, 
			String stateCode, String zip) throws IOException {
		
		CensusGovGeocodingLookupResult result = null;
		
		try {
			result = rest.getForObject(censusGovGeoLocatorUrl, CensusGovGeocodingLookupResult.class, 
					address, city, stateCode, zip);
		} catch (Exception e) {
			throw new IOException("Unable to get GeoLocation for address: " + address 
				+ "," + city + "," + stateCode + ".  CensusGovGeoCode API threw exception: " + e);
		}
		if (result.getStatus().equals("OK")) {
			return result.getGeoLocation();
		} else {
			throw new IOException("Unable to get GeoLocation for address: " + address 
					+ "," + city + "," + stateCode + ".  CensusGovGeoCode API returned status: " +
						result.getStatus());
		}
	
	}
}
