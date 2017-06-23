package org.angiedev.schoolfinder.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.angiedev.schoolfinder.domain.School;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=org.angiedev.schoolfinder.SchoolFinderApplication.class)
@ActiveProfiles("test")
@Transactional
public class SchoolRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager; 
	
	@Autowired 
	SchoolRepository schoolRepository;

	/*
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
 */
	
	@Test
	public void should_find_school_by_nces_id() {
		
		District district = new District("0634590", "San Jose Unified School District");
		entityManager.persist(district);
		
		School school =  new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
				"SAN JOSE", "CA", "95120",1,"KG", "05",district);
		entityManager.persist(school);
		
		School found = schoolRepository.findOneByNcesId("1");
		assertThat(found).isNotNull();
		assertThat(found.getNcesId()).isEqualTo(school.getNcesId());
		assertThat(found.getName()).isEqualTo(school.getName());
		assertThat(found.getStreetAddress()).isEqualTo(school.getStreetAddress());
		assertThat(found.getCity()).isEqualTo(school.getCity());
		assertThat(found.getStateCode()).isEqualTo(school.getStateCode());
		assertThat(found.getStatus()).isEqualTo(school.getStatus());
		assertThat(found.getLowGrade()).isEqualTo(school.getLowGrade());
		assertThat(found.getHighGrade()).isEqualTo(school.getHighGrade());		
		assertThat(found.getDistrict()).isEqualTo(school.getDistrict());
	}
	
	@Test
	public void should_not_find_school_by_nces_id_if_not_exist() {
		School found = schoolRepository.findOneByNcesId("NA");
		assertThat(found).isNull();
	}
	
	@Test 
	public void should_find_schools_by_state_code_and_without_latitude()  {
		District district = new District("0634590", "San Jose Unified School District");
		entityManager.persist(district);
		
		School school =  new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
				"SAN JOSE", "CA", "95120",1,"KG", "05",district);
		entityManager.persist(school);
		
		List<School> foundSchools = schoolRepository.findByStateCodeAndLatitudeIsNull("CA");
		assertThat(foundSchools.size()).isEqualTo(1);
	}
	
	@Test 
	public void should_not_find_schools_by_state_code_and_with_latitude(){
		
		District district = new District("0634590", "San Jose Unified School District");
		entityManager.persist(district);
		
		School school =  new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05",district, 37.2205341, -121.8690651);
		entityManager.persist(school);
		
		List<School> foundSchools = schoolRepository.findByStateCodeAndLatitudeIsNull("CA");
		assertThat(foundSchools.size()).isEqualTo(0);
	
	}
	
	@Test
	public void should_find_schools_within_average_radius() {
		District district = new District("0634590", "San Jose Unified School District");
		entityManager.persist(district);
		
		School school1 =  new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05", district, 37.2205341,-121.8690651);
		entityManager.persist(school1);
		
		School school2 = new School("2", "WILLIAMS ELEMENTARY", "1150 RAJKOVICH WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05",district, 37.2077983, -121.8515333);
		schoolRepository.save(school2);
		
		School school3 = new School("3", "BOOKSIN ELEMENTARY", "1590 DRY CREEK RD.",
			"SAN JOSE", "CA", "95125",1,"KG", "05", district, 37.2881426, -121.9058075);
		schoolRepository.save(school3);
	
		List<School> foundSchools = schoolRepository.findNearGeoLocation(37.2205341,-121.8690651, 10);
		
		assertThat(foundSchools.size()).isEqualTo(3);
		assertThat(foundSchools).contains(school1, school2, school3);
	}
	
	@Test
	public void should_find_schools_within_small_radius() {
		District district = new District("0634590", "San Jose Unified School District");
		entityManager.persist(district);
		
		School school1 =  new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05", district, 37.2205341,-121.8690651);
		entityManager.persist(school1);
		
		School school2 = new School("2", "WILLIAMS ELEMENTARY", "1150 RAJKOVICH WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05",district, 37.2077983, -121.8515333);
		schoolRepository.save(school2);
		
		School school3 = new School("3", "BOOKSIN ELEMENTARY", "1590 DRY CREEK RD.",
			"SAN JOSE", "CA", "95125",1,"KG", "05", district, 37.2881426, -121.9058075);
		schoolRepository.save(school3);
	
		List<School> foundSchools = schoolRepository.findNearGeoLocation(37.2205341,-121.8690651, 2);
		
		assertThat(foundSchools.size()).isEqualTo(2);
		assertThat(foundSchools).contains(school1, school2);
	}
	
	@Test
	public void should_not_find_schools_outside_of_radius() {
		District district = new District("0634590", "San Jose Unified School District");
		entityManager.persist(district);
		
		School school1 =  new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05", district, 37.2205341,-121.8690651);
		entityManager.persist(school1);
		
		School school2 = new School("2", "WILLIAMS ELEMENTARY", "1150 RAJKOVICH WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05",district, 37.2077983, -121.8515333);
		schoolRepository.save(school2);
		
		School school3 = new School("3", "BOOKSIN ELEMENTARY", "1590 DRY CREEK RD.",
			"SAN JOSE", "CA", "95125",1,"KG", "05", district, 37.2881426, -121.9058075);
		schoolRepository.save(school3);
	
		List<School> foundSchools = schoolRepository.findNearGeoLocation(37.270167, -122.014982, 5);
		
		assertThat(foundSchools.size()).isEqualTo(0);
	}

	@Test
	public void should_find_schools_within_radius_matching_search_string() {
		District district = new District("0634590", "San Jose Unified School District");
		entityManager.persist(district);
		
		School school1 =  new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05", district, 37.2205341,-121.8690651);
		entityManager.persist(school1);
		
		School school2 = new School("2", "WILLIAMS ELEMENTARY", "1150 RAJKOVICH WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05",district, 37.2077983, -121.8515333);
		schoolRepository.save(school2);
		
		School school3 = new School("3", "BOOKSIN ELEMENTARY", "1590 DRY CREEK RD.",
			"SAN JOSE", "CA", "95125",1,"KG", "05", district, 37.2881426, -121.9058075);
		schoolRepository.save(school3);
		
		List<School> foundSchools = schoolRepository
				.findNearGeoLocationWithNameMatchingSearchString(37.2205341,-121.8690651, 10, "Elementary");
		assertThat(foundSchools.size()).isEqualTo(3);
		assertThat(foundSchools).contains(school1, school2, school3);
	}
	
	@Test
	public void should_not_find_schools_within_radius_nonmatching_search_string() {
		District district = new District("0634590", "San Jose Unified School District");
		entityManager.persist(district);
		
		School school1 =  new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05", district, 37.2205341,-121.8690651);
		entityManager.persist(school1);
		
		School school2 = new School("2", "WILLIAMS ELEMENTARY", "1150 RAJKOVICH WAY",
			"SAN JOSE", "CA", "95120",1,"KG", "05",district, 37.2077983, -121.8515333);
		schoolRepository.save(school2);
		
		School school3 = new School("3", "BOOKSIN ELEMENTARY", "1590 DRY CREEK RD.",
			"SAN JOSE", "CA", "95125",1,"KG", "05", district, 37.2881426, -121.9058075);
		schoolRepository.save(school3);
		
		List<School> foundSchools = schoolRepository
				.findNearGeoLocationWithNameMatchingSearchString(37.2205341,-121.8690651, 10, "High");
		assertThat(foundSchools.size()).isEqualTo(0);
	}
	
}
