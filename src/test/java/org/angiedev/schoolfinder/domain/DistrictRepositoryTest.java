package org.angiedev.schoolfinder.domain;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.angiedev.schoolfinder.domain.District;
import org.angiedev.schoolfinder.domain.DistrictRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=org.angiedev.schoolfinder.SchoolFinderApplication.class)
@ActiveProfiles("test")
@Transactional
public class DistrictRepositoryTest  {

	@PersistenceContext
	private EntityManager entityManager; 
	
	@Autowired 
	DistrictRepository districtRepository;
			
	@Test
	public void should_find_district_by_lea_id(){
		District districtOne = new District("ID-1", "District One");
		entityManager.persist(districtOne);
		District found = districtRepository.findOneByLeaId("ID-1");
		assertThat(found).isNotNull();
		assertThat(found.getDistrictId()).isEqualTo(districtOne.getDistrictId());
		assertThat(found.getName()).isEqualTo(districtOne.getName());
	}
	
	@Test
	public void should_not_find_district_by_lea_id_if_not_exist() {
		District found  = districtRepository.findOneByLeaId("BAD");
		assertThat(found).isNull();
	}
	
}
