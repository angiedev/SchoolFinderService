package org.angiedev.schoolfinder.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * SchoolRepository is a CRUD JPA Spring Data repository used to perform 
 * database operations on School domain entities.
 */

@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {
	School findOneByNcesId(String ncesId);
	List<School> findByStateCodeAndLatitudeIsNull(String stateCode);
	
	/**
	 * Retrieves list of schools near passed in geo location within given search 
	 * radius 
	 * @param latitude	latitude position from where to search by
	 * @param longitude longitude position from where to search by
	 * @param radius    number of miles for search radius
	 * @return list of schools within given search radius of passed in geo location
	 */	
	@Query(value="call GetSchoolsNearGeoLocation(:lat, :long, :radius)", nativeQuery = true)
	List<School> findNearGeoLocation(@Param("lat") double latitude,
		@Param("long") double longitude,  @Param("radius") int radius);
	

	/**
	 * Retrieves list of schools near passed in geo location within given search 
	 * radius with name matching search string.  
	 * @param latitude	latitude position from where to search by
	 * @param longitude longitude position from where to search by
	 * @param radius    number of miles for search radius
	 * @param schoolNameSearchValue	string value to match against school name
	 * @return list of schools within given search radius of passed in geo location
	 */
	@Query(value=
		"call GetSchoolsNearGeoLocationWithNameMatchingSearchString(:lat, :long, :radius, :searchStr)", 
		nativeQuery = true)
	List<School> findNearGeoLocationWithNameMatchingSearchString(@Param("lat") double latitude,
		@Param("long") double longitude,  @Param("radius") int radius,
		@Param("searchStr") String schoolNameSearchValue);
}
