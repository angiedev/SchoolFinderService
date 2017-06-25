package org.angiedev.schoolfinder.controller;

import java.util.ArrayList;
import java.util.List;

import org.angiedev.schoolfinder.domain.District;
import org.angiedev.schoolfinder.domain.School;
import org.angiedev.schoolfinder.service.SchoolFinderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=org.angiedev.schoolfinder.SchoolFinderApplication.class)
@AutoConfigureMockMvc
public class SchoolFinderControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean 
	private SchoolFinderService service;
	private final double latitude = 37.22;
	private final double longitude = -121.86; 
	private final double outOfRangeLatitude = 37.78;
	private final double outOfRangeLongitude = -122.40; 
	private final int avgRadius = 10;
	private School school1, school2, school3;

	@Before 
	public void setUp() {
		
		District district = new District("0634590", "San Jose Unified School District");
		school1 =  new School("1", "Simonds Elementary", "6515 Grapevine Way",
			"San Jose", "CA", "95120",1,"KG", "05", district, 37.2205341,-121.8690651);
		school2 = new School("2", "Williams Elementary", "1150 Rajkovich Way",
			"San Jose", "CA", "95120",1,"01", "06",district, 37.2077983, -121.8515333);
		
		List<School> avgRadiusSchools = new ArrayList<School>();
		avgRadiusSchools.add(school1);
		avgRadiusSchools.add(school2);
		
		List<School> outsideOfRadiusSchools = new ArrayList<School>();
		
		List<School> simondsNameMatchSchools = new ArrayList<School>();
		simondsNameMatchSchools.add(school1);
		
		List<School> noNameMatchSchools = new ArrayList<School>();
	
		when(service.getSchools(latitude, longitude, avgRadius))
			.thenReturn(avgRadiusSchools);
		when(service.getSchools(outOfRangeLatitude, outOfRangeLongitude, avgRadius))
			.thenReturn(outsideOfRadiusSchools);
		when(service.getSchools(latitude, longitude, avgRadius, "Simonds"))
			.thenReturn(simondsNameMatchSchools);
		when(service.getSchools(latitude, longitude, avgRadius, "NOTFOUND"))
			.thenReturn(noNameMatchSchools);
		when(service.getSchoolByNcesId("2"))
			.thenReturn(school2);
		when(service.getSchoolByNcesId("NOTFOUND"))
			.thenReturn(null);
	}
	
	
	@Test 
	public void should_get_schools_within_radius() throws Exception{
		
		String queryStr = String.format("/schools?lat=%f&long=%f&radius=%d", latitude,
				longitude, avgRadius);
		
		mockMvc.perform(get(queryStr).accept(
				MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].ncesId",is("1")))
				.andExpect(jsonPath("$[0].name",is("Simonds Elementary")))
				.andExpect(jsonPath("$[0].streetAddress",is("6515 Grapevine Way")))
				.andExpect(jsonPath("$[0].city",is("San Jose")))
				.andExpect(jsonPath("$[0].stateCode",is("CA")))
				.andExpect(jsonPath("$[0].zip",is("95120")))
				.andExpect(jsonPath("$[0].lowGrade",is("KG")))
				.andExpect(jsonPath("$[0].highGrade",is("05")))
				.andExpect(jsonPath("$[1].ncesId",is("2")))
				.andExpect(jsonPath("$[1].name",is("Williams Elementary")))
				.andExpect(jsonPath("$[1].streetAddress",is("1150 Rajkovich Way")))
				.andExpect(jsonPath("$[1].city",is("San Jose")))
				.andExpect(jsonPath("$[1].stateCode",is("CA")))
				.andExpect(jsonPath("$[1].zip",is("95120")))
				.andExpect(jsonPath("$[1].lowGrade",is("01")))
				.andExpect(jsonPath("$[1].highGrade",is("06")));
	}
	
	@Test 
	public void should_not_get_schools_within_radius_if_not_exist() throws Exception{
		
		String queryStr = String.format("/schools?lat=%f&long=%f&radius=%d", outOfRangeLatitude,
				outOfRangeLongitude, avgRadius);
		
		mockMvc.perform(get(queryStr).accept(
				MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test 
	public void should_get_schools_within_radius_with_matching_name() throws Exception{
		
		String queryStr = String.format("/schools?lat=%f&long=%f&radius=%d&searchString=%s", latitude,
				longitude, avgRadius, "Simonds");
		
		mockMvc.perform(get(queryStr).accept(
				MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].ncesId",is("1")))
				.andExpect(jsonPath("$[0].name",is("Simonds Elementary")))
				.andExpect(jsonPath("$[0].streetAddress",is("6515 Grapevine Way")))
				.andExpect(jsonPath("$[0].city",is("San Jose")))
				.andExpect(jsonPath("$[0].stateCode",is("CA")))
				.andExpect(jsonPath("$[0].zip",is("95120")))
				.andExpect(jsonPath("$[0].lowGrade",is("KG")))
				.andExpect(jsonPath("$[0].highGrade",is("05")));
	}
	
	@Test 
	public void should_not_get_schools_within_radius_with_no_matching_name() throws Exception{
		
		String queryStr = String.format("/schools?lat=%f&long=%f&radius=%d&searchString=%s", latitude,
				longitude, avgRadius, "NOTFOUND");
		
		mockMvc.perform(get(queryStr).accept(
				MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test 
	public void should_get_school_by_nces_id() throws Exception{
		
		String queryStr = String.format("/schools/%s", "2");
		
		mockMvc.perform(get(queryStr).accept(
				MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.ncesId",is("2")))
				.andExpect(jsonPath("$.name",is("Williams Elementary")))
				.andExpect(jsonPath("$.streetAddress",is("1150 Rajkovich Way")))
				.andExpect(jsonPath("$.city",is("San Jose")))
				.andExpect(jsonPath("$.stateCode",is("CA")))
				.andExpect(jsonPath("$.zip",is("95120")))
				.andExpect(jsonPath("$.lowGrade",is("01")))
				.andExpect(jsonPath("$.highGrade",is("06")));
	}
	
	@Test 
	public void should_not_get_school_by_nces_id_if_not_exist() throws Exception{
		
		String queryStr = String.format("/schools/%s", "NOTFOUND");
		
		mockMvc.perform(get(queryStr).accept(
				MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
	}
	
}
