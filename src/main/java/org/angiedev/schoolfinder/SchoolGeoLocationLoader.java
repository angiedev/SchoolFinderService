package org.angiedev.schoolfinder;

import java.io.IOException;
import java.util.List;

import org.angiedev.schoolfinder.domain.School;
import org.angiedev.schoolfinder.domain.SchoolRepository;
import org.angiedev.schoolfinder.geolocation.GeoLocation;
import org.angiedev.schoolfinder.geolocation.GeoLocator;
import org.angiedev.schoolfinder.geolocation.GoogleOverQueryLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * SchoolGeoLocationLoader lookups and adds geo-location (longitude and latitude)
 * data for schools in the SchoolFinder database that do not already have geo-location
 * data available.  The physical addresses are retrieved from the SchoolFinder 
 * database for schools that do not already have geo-location data.  Only schools that
 * are located in one of the states identified in the passed in stateCodeList parameter
 * will be checked for missing geo-location data.  The geo-location data is retrieved 
 * for these schools using Google's geocoding API and added into the SchoolFinder database. 
 * <p>
 * The loader is kicked off for a subset of states at a time due to the daily  
 * limitation of calling the Google GeoCoding API. (Only allowed 2500 API calls  
 * per day)
 * <p>
 * Geo-location data is needed to support the retrieval of schools based on 
 * a client's provided geo-location and search radius.
 * <p>
 * Usage: SchoolGeoLocationLoader &lt;stateCodeList&gt;
 * <ul>
 * <li>stateCodeList: a comma separated list of state codes (i.e. "CA,OR") 
 *  </ul>
 * @author Angela Gordon
 */
//@SpringBootApplication (No longer using because need to exclude SchoolDataLoader and SchoolFinderApplication)
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SchoolDataLoader.class, SchoolFinderApplication.class}))
@EnableAutoConfiguration
public class SchoolGeoLocationLoader implements CommandLineRunner {

	 @Autowired
	 private SchoolRepository schoolRepository;
	 
	 @Autowired
	 @Qualifier("Google")
	 private GeoLocator geoLocator;
	 	 
	 
	
    /**
     * Main method kicked off by Spring boot
     */
	public static void main(String[] args) {
		SpringApplication.run(SchoolGeoLocationLoader.class, args).close();
		
	}
	
	 /**
	  * Kicks off the geo location data loader for the schools in the states 
	  * identified by the passed in argument
	  * @param args First argument identifies the state codes of the schools that 
	  *  			geo-location data should be loaded for.
	  */
	@Override
	public void run(String... args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage: java SchoolGeoLocationLoader <stateCode[,stateCode]*>");
			return;
		}
		
		loadGeoLocationData(args[0]);
		
	}	 
	 
	 /** 
	  * Retrieves the schools that need geo location data, looks up the geo-location data and 
	  * stores the geo-location data into the database.
	  * @param stateCodeList a comma separated list of state codes of the schools 
	  *  		that geo-location data should be loaded for.
	  */
	 public void loadGeoLocationData(String stateCodeList) {
		 
		 int numFailures = 0;
		 int numSuccess = 0;
		 outer:
		 for (String stateCode: stateCodeList.split(",")) {
			 System.out.println("Getting geo codes for schools in: " + stateCode);
			// find addresses that need lat/long data 
			 List<School> schools = schoolRepository.findByStateCodeAndLatitudeIsNull(stateCode);
			 
			 // for each address call Geo Location Service to get lat/long data for the school
			 // based on the school's address, then save the lat/long in our db
		
			 for (School school : schools) {
				 try {
					 GeoLocation geoLocation = geoLocator.getGeoLocationForAddress(
						school.getStreetAddress().replace("'", " "), school.getCity(), school.getStateCode(),school.getZip());
					 school.setLatitude(geoLocation.getLatitude());
					 school.setLongitude(geoLocation.getLongitude());
					 schoolRepository.save(school);
					 numSuccess++;
				 } catch (GoogleOverQueryLimitException e) {
					 System.out.println("Query limit reached when loading geo data for school " + school);
					 numFailures++;
					 break outer;
				 } catch (IOException e) {
					 System.out.println("Unable to complete load of geo location data for school " + school + "  Exception thrown: " +e);
					 numFailures++;
					 continue;
				 }
			 }
		 }
		 System.out.println("Retrieved Geo location for " + numSuccess + " schools");
		 System.out.println("Unable to retrieve Geo location for " + numFailures + " schools");
		 
	 }


}
	 
	

