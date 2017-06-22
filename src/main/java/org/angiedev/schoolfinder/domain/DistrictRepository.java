package org.angiedev.schoolfinder.domain;

/**
 * DistrictRepository is a CRUD JPA Spring Data repository used to perform 
 * database operations on District domain entities.
 */
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends CrudRepository<District, Long>{

	District findOneByLeaId (String leaId);
}
