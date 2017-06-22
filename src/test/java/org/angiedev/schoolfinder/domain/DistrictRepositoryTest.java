package org.angiedev.schoolfinder.domain;

import static org.junit.Assert.*;


import org.angiedev.schoolfinder.domain.District;
import org.angiedev.schoolfinder.domain.DistrictRepository;
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
public class DistrictRepositoryTest {

	@Autowired 
	DistrictRepository districtRepository;
	
	District  district1;
	District district2;
	District district3;
	District district4;		
			
	@Before
	public void setUp() {	
		district1 = new District("ID-1", "District One");
		district2 = new District( "ID-2", "District Two");
		district3 = new District( "ID-3", "District Three");
		district4 = new District("ID-4", "District Four");		
				
		districtRepository.save(district1);
		districtRepository.save(district2);
		districtRepository.save(district3);
		districtRepository.save(district4);
	}

	@Test
	public void findDistrictByValidLeaId() {
		District district = districtRepository.findOneByLeaId(district1.getLeaId());
		assertNotNull("Didn't find district", district);
		assertEquals("Retrieved wrong district", district1.getDistrictId(), district.getDistrictId());
		assertEquals("Retrieved wrond district name", district1.getName(), district.getName());
	}
	
	@Test
	public void findDistrictByInvalidLeaId() {
		District district  = districtRepository.findOneByLeaId("BAD");
		assertNull("Should not have found any districts", district);		
	}
	
	@After
	public void tearDown() {
		districtRepository.delete(district1);
		districtRepository.delete(district2);
		districtRepository.delete(district3);
		districtRepository.delete(district4);
	}
	
}
