package org.angiedev.schoolfinder.geolocation;

import java.io.IOException;

import org.angiedev.schoolfinder.geolocation.GeoLocation;

/**
 * GeoLoctionService is a interface for a component which enables a user to lookup 
 * the geolocation (latitude/longitude) for an address.
 * 
 * @author Angela Gordon
 */
public interface GeoLocator {
	/**
	  * Returns the geo location for the passed in address
	  * @param address 		street address
	  * @param city 		city	
	  * @param stateCode 	two letter state code
	  * @param zip			zip code
	  * @return 			geo location of address
	  */
	public GeoLocation getGeoLocationForAddress(String address, String city, String stateCode, String zip)
		 	throws IOException;
}
