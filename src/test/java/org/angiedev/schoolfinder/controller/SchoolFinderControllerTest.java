package org.angiedev.schoolfinder.controller;

import org.angiedev.schoolfinder.service.SchoolFinderService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value=SchoolFinderController.class, secure=false)
public class SchoolFinderControllerTest {

	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean 
	private SchoolFinderService service;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
