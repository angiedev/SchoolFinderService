package org.angiedev.schoolfinder.geolocation;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=org.angiedev.schoolfinder.TestApplicationConfiguration.class)
@ActiveProfiles("test")
public class GoogleGeoLocatorTest {

	@Autowired 
	private GoogleGeoLocator geoLocator;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetGeoLocationForValidAddress() throws Exception {
		GeoLocation geoLocation = geoLocator.getGeoLocationForAddress(
				"6477 Almaden Expressway", "San Jose", "CA", "95120");
		assertNotNull("Geolocation should not be null", geoLocation);
		assertNotNull("Latitude should not be null", geoLocation.getLatitude());
		assertNotNull("Longitude should not be null", geoLocation.getLatitude());
	}
	
	@Test(expected = IOException.class)
	@SuppressWarnings("unused")
	public void testGetGeoLocationForNotFoundAddress() throws Exception {
		GeoLocation geoLocation = geoLocator.getGeoLocationForAddress(
				"100 DS HALL", "HERLONG", "CA", "96113");
	}
}
