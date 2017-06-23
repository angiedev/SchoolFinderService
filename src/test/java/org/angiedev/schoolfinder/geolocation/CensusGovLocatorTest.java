package org.angiedev.schoolfinder.geolocation;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=org.angiedev.schoolfinder.SchoolFinderApplication.class)
@ActiveProfiles("test")
public class CensusGovLocatorTest {

	@Autowired 
	private CensusGovGeoLocator geoLocator;

	@Test
	public void should_return_geolocation_for_address() throws Exception {
		GeoLocation geoLocation = geoLocator.getGeoLocationForAddress(
				"6477 Almaden Expressway", "San Jose", "CA", "95120");
		assertThat(geoLocation).isNotNull();
		assertThat(geoLocation.getLatitude()).isNotNull();
		assertThat(geoLocation.getLongitude()).isNotNull();
		assertThat(geoLocation.getLatitude()).isEqualTo(37.219691, withPrecision(2d));
		assertThat(geoLocation.getLongitude()).isEqualTo(-121.861593, withPrecision(2d));
	}
	
	@Test(expected = IOException.class)
	public void should_throw_exception_for_invalid_address() throws Exception {
		@SuppressWarnings("unused")
		GeoLocation geoLocation = geoLocator.getGeoLocationForAddress(
				"100 DS HALL", "HERLONG", "CA", "96113");
	}
}
