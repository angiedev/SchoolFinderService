package org.angiedev.schoolfinder.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import org.angiedev.schoolfinder.domain.School;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=org.angiedev.schoolfinder.TestApplicationConfiguration.class)
@ActiveProfiles("test")
public class SchoolRepositoryTest {

	@Autowired 
	SchoolRepository schoolRepository;
	
	@Autowired 
	DistrictRepository districtRepository;
	
	private District district;
	private School school1, school2, school3, school4;
	
	@Before 
	public void setUp() {
		
		district = new District("0634590", "San Jose Unified School District");
		districtRepository.save(district);
		
		school1 = new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
				"SAN JOSE", "CA", "95120",1,"KG", "05",district);
		school1.setLatitude( 37.2205341);
		school1.setLongitude(-121.8690651);
		schoolRepository.save(school1);
		
		school2 = new School("2", "WILLIAMS ELEMENTARY", "1150 RAJKOVICH WAY",
				"SAN JOSE", "CA", "95120",1,"KG", "05",district);
		school2.setLatitude( 37.2077983);
		school2.setLongitude(-121.8515333);
		schoolRepository.save(school2);
		
		school3 = new School("3", "BOOKSIN ELEMENTARY", "1590 DRY CREEK RD.",
				"SAN JOSE", "CA", "95125",1,"KG", "05",district);
		school3.setLatitude(37.2881426);
		school3.setLongitude(-121.9058075);
		schoolRepository.save(school3);
		
		school4 = new School("4", "LAKE FOREST ELEMENTARY", "21801 PITTSFORD DR.",
				"LAKE FOREST", "CA", "92630",1,"KG", "06",district);
		schoolRepository.save(school4);

	}
	
	
	@Test
	public void findOneWithValidNcesId() {
		School s = schoolRepository.findOneByNcesId("1");
		assertNotNull("Should have found school", s);
		assertEquals("Should have correct ncesId", school1.getNcesId(), s.getNcesId());
		assertEquals("Should have correct name", school1.getName(), s.getName());
		assertEquals("Should have correct address", school1.getStreetAddress(), s.getStreetAddress());
		assertEquals("Should have correct city", school1.getCity(), s.getCity());
		assertEquals("Should have correct state code", school1.getStateCode(), s.getStateCode());
		assertEquals("Should have correct zip", school1.getZip(), s.getZip());
		assertEquals("Should have correct status", school1.getStatus(), s.getStatus());
		assertEquals("Should have correct low grade", school1.getLowGrade(), s.getLowGrade());
		assertEquals("Should have correct high grade", school1.getHighGrade(), s.getHighGrade());
		assertEquals("Should have correct district", school1.getDistrict().getDistrictId(), s.getDistrict().getDistrictId());
	}
	
	@Test
	public void findOneWithInvalidNcesId() {
		School s = schoolRepository.findOneByNcesId("NA");
		assertNull("Should not have found school", s);
	}
	
	@Test 
	public void findByStateCodeAndLatitudeIsNullOnSchoolsWithoutLatitude() {
		List<School> schools = schoolRepository.findByStateCodeAndLatitudeIsNull("CA");
		assertEquals("Should have found 1 schools without latitude", 1, schools.size());
	}
	
	@Test 
	public void findByStateCodeAndLatitudeIsNullOnSchoolsWithLatitiude() {
		
		school4.setLatitude( 33.6527242);
		school4.setLongitude( -117.6725555);
		schoolRepository.save(school4);
		
		List<School> schools = schoolRepository.findByStateCodeAndLatitudeIsNull("CA");
		assertEquals("Should have found 0 schools without latitude", 0, schools.size());
	}
	
	@Test
	public void findNearGeoLocation() {
		List<School> schools = schoolRepository.findNearGeoLocation(37.2205341,-121.8690651, 10);
		assertEquals("Should have found 3 schools within 10 mile radius", 3, schools.size());
		assertTrue("Should have found Simonds within 10 mile radius", schools.contains(school1));
		assertTrue("Should have found Williams within 10 mile radius", schools.contains(school2));
		assertTrue("Should have found Booksin within 10 mile radius", schools.contains(school3));
		
		schools = schoolRepository.findNearGeoLocation(37.2205341,-121.8690651, 2);
		assertEquals("Should have found 2 schools within 2 mile radius", 2, schools.size());
		assertTrue("Should have found Simonds within 2 mile radius", schools.contains(school1));
		assertTrue("Should have found Williams within 2 mile radius", schools.contains(school2));
	
		school4.setLatitude(33.6527242);
		school4.setLongitude(-117.6725555);
		schoolRepository.save(school4);
		
		schools = schoolRepository.findNearGeoLocation(33.65272421,-117.6725555, 1);
		assertEquals("Should have found 1 schools within 1 mile radius", 1, schools.size());
		assertTrue("Should have found Lake Forest Elementary within 1 mile radius", schools.contains(school4));
	}
	
	@Test
	public void findNearGeoLocationWithoutMatches() {
		List<School> schools = schoolRepository.findNearGeoLocation(40.2,-130.4, 10);
		assertEquals("Should have found 0 schools near location", 0, schools.size());
	}
	
	@Test
	public void findNearGeoLocationWithMatchingSearchString() {
		List<School> schools = schoolRepository
				.findNearGeoLocationWithNameMatchingSearchString(37.2205341,-121.8690651, 10, "Simonds");
		assertEquals("Should have found 1 school within 10 mile radius with Simonds search string", 
				1, schools.size());
		assertTrue("Should have found Simonds within 10 mile radius with Simonds search string", 
				schools.contains(school1));
	
		schools = schoolRepository
				.findNearGeoLocationWithNameMatchingSearchString(37.2205341,-121.8690651, 10, "Elementary");
		assertEquals("Should have found 3 schools within 10 mile radius with Elementary Search String",
				3, schools.size());
		assertTrue("Should have found Simonds within 10 mile radius with Elementary Search String", 
				schools.contains(school1));
		assertTrue("Should have found Williams within 10 mile radius with Elementary Search String", 
				schools.contains(school2));
		assertTrue("Should have found Bookskin within 10 mile radius with Elementary Search String", 
				schools.contains(school3));
	
	}
	
	@Test
	public void findNearGeoLocationWithNameMatchingSearchStringWithoutMatches() {
		List<School> schools = schoolRepository
				.findNearGeoLocationWithNameMatchingSearchString(40.2,-130.4, 10, "High");
		assertEquals("Should have found 0 schools within 10 mile radius with High Search String", 0, schools.size());
	}
	
	
	@After 
	public void tearDown() {
		schoolRepository.delete(school1);
		schoolRepository.delete(school2);
		schoolRepository.delete(school3);
		schoolRepository.delete(school4);
		districtRepository.delete(district);
	}

}
