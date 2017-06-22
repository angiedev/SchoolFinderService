package org.angiedev.schoolfinder.service;


import java.util.List;

import org.angiedev.schoolfinder.domain.School;
import org.angiedev.schoolfinder.domain.SchoolRepository;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolFinderService {

	@Autowired 
	private SchoolRepository schoolRepository;
	
	/**
	 * Retrieves the list of schools that are near the specified location (in
	 * latitude and longitude) within the passed in search radius.
	 * @param latitude		latitude of location to search from 
	 * @param longitude		longitude of location to search from
	 * @param searchRadius	search radius in miles to search within
	 * @param maxNumResults	maximum number of schools to return
	 * @return				list of schools located within search radius of search location
	 * 
	 */
	public List<School> getSchools(double latitude, double longitude, int searchRadius) {
		List<School> schools = schoolRepository.findNearGeoLocation(latitude, longitude, searchRadius);
		fixCapitalization(schools);
		return schools;
	}
	
	/**
	 * Retrieves the list of schools that are near the specified location (in
	 * latitude and longitude) within the passed in search radius and whose 
	 * name contain the passed in search string.  
	 * @param latitude		latitude of location to search from 
	 * @param longitude		longitude of location to search from
	 * @param searchRadius	search radius in miles to search within
	 * @param searchString	search string to find at start of the school's name
	 * @return				list of schools located within search radius of search location
	 * 
	 */
	public List<School> getSchools(double latitude, double longitude, int searchRadius,
			String searchString) {
		List <School> schools = schoolRepository
				.findNearGeoLocationWithNameMatchingSearchString(latitude, longitude, 
				searchRadius, searchString);
		fixCapitalization(schools);
		return schools;
	}
	
	/**
	 * Retrieves the school identified by the passed in NCES id
	 * @param ncesId	unique NCES id identifying the school 
	 *                  (Id assigned by the National Center for Education Statistics)
	 * @return			school with the passed in NCES Id
	 * 
	 */
	public School getSchoolByNcesId(String ncesId) {
		School school = schoolRepository.findOneByNcesId(ncesId);
		fixCapitalization(school);
		return school;
	}
	
	/* Modifies the words in each school name, street address and city to begin 
	 * with a capitalized letter followed by lower case instead of being in all caps
	 */
	private void fixCapitalization(List<School> schools ) {
		for (School s: schools) {
			fixCapitalization(s);
		}
	}
	
	/* Modifies the words in the school's name, street address, city to begin 
	 * with a capitalized letter followed by lower case instead of being in all caps
	 */
	private void fixCapitalization(School s) {
		char [] delims = {'(', ' '};
		s.setName(WordUtils.capitalizeFully(s.getName(), delims));
		s.setCity(WordUtils.capitalizeFully(s.getCity(), delims));
		s.setStreetAddress(WordUtils.capitalizeFully(s.getStreetAddress(), delims));
	}
}
