package org.angiedev.schoolfinder.controller;


import java.util.List;

import org.angiedev.schoolfinder.domain.School;
import org.angiedev.schoolfinder.service.SchoolFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolFinderController {

	@Autowired
	private SchoolFinderService schoolFinderService;
	
	
	@GetMapping("/schools")
	public List<School> searchForSchools(
			@RequestParam(value="schoolName", required=false) String searchString,
			@RequestParam("lat") double latitude,
			@RequestParam("long") double longitude, 
			@RequestParam("radius") int searchRadius) {
		
		if (searchString == null) {
			return schoolFinderService.getSchools(latitude, longitude, searchRadius);
		} else {
			return schoolFinderService.getSchools(latitude, longitude, searchRadius, searchString);
		}
	}	
		
	/**
	 * Retrieves the school identified by the passed in NCES id
	 * @param ncesId	unique NCES id identifying the school 
	 *                  (Id assigned by the National Center for Education Statistics)
	 * @return			school with the passed in NCES Id
	 * 
	 */
	@GetMapping("/schools/{ncesId}")
	public School getSchoolByNcesId(@PathVariable String ncesId) {
		return schoolFinderService.getSchoolByNcesId(ncesId);
	}
	
}
