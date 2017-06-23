package org.angiedev.schoolfinder.service;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.angiedev.schoolfinder.domain.District;
import org.angiedev.schoolfinder.domain.DistrictRepository;
import org.angiedev.schoolfinder.domain.School;
import org.angiedev.schoolfinder.domain.SchoolRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SchoolFinderServiceTest {

	@TestConfiguration 
	static class SchoolFinderServiceTestContextConfiguration {
		@Bean 
		public SchoolFinderService schoolFinderService() {
			return new SchoolFinderService();
		}
	}

	@Autowired 
	SchoolFinderService schoolFinderService; 
	
	@MockBean 
	private SchoolRepository schoolRepository;
	
	private final double latitude = 37.22;
	private final double longitude = -121.86; 
	private final double outOfRangeLatitude = 37.78;
	private final double outOfRangeLongitude = -122.40; 
	private final int avgRadius = 10;
	private final int smallRadius = 1;
	private School school1, school2, school3;
	
	@Before 
	public void setUp() {
		
		District district = new District("0634590", "San Jose Unified School District");
		
		school1 =  new School("1", "SIMONDS (TEST) ELEMENTARY", "6515 GRAPEVINE WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05", district, 37.2205341,-121.8690651);
		school2 = new School("2", "WILLIAMS ELEMENTARY", "1150 RAJKOVICH WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05",district, 37.2077983, -121.8515333);
		school3 = new School("3", "BOOKSIN ELEMENTARY", "1590 DRY CREEK RD.",
			"SAN JOSE", "CA", "95125",1,"KG", "05", district, 37.2881426, -121.9058075);
		
		List<School> avgRadiusSchools = new ArrayList<School>();
		avgRadiusSchools.add(school1);
		avgRadiusSchools.add(school2);
		avgRadiusSchools.add(school3);
		
		List<School> smallRadiusSchools = new ArrayList<School>(); 
		smallRadiusSchools.add(school1);
		
		List<School> nameMatchSchools = new ArrayList<School>(); 
		nameMatchSchools.add(school2);
		
		List<School> emptyList = new ArrayList<School>();
		
		Mockito.when(schoolRepository.findNearGeoLocation(outOfRangeLatitude, outOfRangeLongitude, avgRadius))
			.thenReturn(emptyList);
		
		Mockito.when(schoolRepository.findNearGeoLocation(latitude, longitude, avgRadius))
		.thenReturn(avgRadiusSchools);
		
		Mockito.when(schoolRepository.findNearGeoLocation(latitude, longitude, smallRadius))
			.thenReturn(smallRadiusSchools);
		
		
		Mockito.when(schoolRepository.findNearGeoLocationWithNameMatchingSearchString(
			latitude, longitude, avgRadius, "WiLlIaMs"))
			.thenReturn(nameMatchSchools);
		
		Mockito.when(schoolRepository.findNearGeoLocationWithNameMatchingSearchString(
				latitude, longitude, avgRadius, "NOTFOUND"))
				.thenReturn(emptyList);
		
	}
	
	@Test
	public void should_find_schools_within_search_radius() throws Exception {
		List<School> foundSchools = 
			schoolFinderService.getSchools(latitude, longitude, avgRadius);
		assertThat(foundSchools.size()).isEqualTo(3);
		assertThat(foundSchools).contains(school1, school2, school3);
	}
	
	@Test
	public void should_not_find_schools_outside_of_search_radius() throws Exception {
		List<School> foundSchools = 
			schoolFinderService.getSchools(outOfRangeLatitude, outOfRangeLongitude, avgRadius);
		assertThat(foundSchools.size()).isEqualTo(0);
	}
	
	
	@Test
	public void should_find_schools_within_search_radius_with_capfix() throws Exception {
		List<School> foundSchools =
			schoolFinderService.getSchools(latitude, longitude, smallRadius);
		assertThat(foundSchools.size()).isEqualTo(1);
		assertThat(foundSchools).contains(school1);
		assertThat(foundSchools.get(0).getName()).isEqualTo("Simonds (Test) Elementary");
		assertThat(foundSchools.get(0).getCity()).isEqualTo("San Jose");
		assertThat(foundSchools.get(0).getStreetAddress()).isEqualTo("6515 Grapevine Way");
	}

	@Test
	public void should_find_schools_within_search_radius_with_matching_search_string() throws Exception {
		List<School> foundSchools = 
			schoolFinderService.getSchools(latitude, longitude, avgRadius, "WiLlIaMs");
		assertThat(foundSchools.size()).isEqualTo(1);
		assertThat(foundSchools).contains(school2);
	}
	
	@Test
	public void should_not_find_schools_within_search_radius_without_matching_search_string() throws Exception {
		List<School> foundSchools = 
			schoolFinderService.getSchools(latitude, longitude, avgRadius, "NOTFOUND");
		assertThat(foundSchools.size()).isEqualTo(0);
	}

}
