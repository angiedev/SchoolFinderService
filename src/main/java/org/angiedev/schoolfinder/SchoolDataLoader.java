package org.angiedev.schoolfinder;


import java.util.Scanner;
import org.angiedev.schoolfinder.domain.District;
import org.angiedev.schoolfinder.domain.DistrictRepository;
import org.angiedev.schoolfinder.domain.School;
import org.angiedev.schoolfinder.domain.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;



/**
 * SchoolDataLoader loads the School Finder database with the school data provided by 
 * the NCES (National Center for Education Statistics).
 * <p>
 * The loader parses the school data file which provides our application with the school, 
 * location and district information for private and public elementary and secondary schools 
 * in the United States and adds the data into our database.  This loader should only be run 
 * once when first initializing our application.
 * <p>
 * Input Data Expected (separated by tabs):
 *<ul>
 *   <li>NCES School Id 
 *   <li>LEA ID (district Id)
 *   <li>District Name
 *   <li>School Name
 *   <li>Street Address
 *   <li>City
 *   <li>State
 *   <li>Zip
 *   <li>Status
 *   <li>Low Grade
 *   <li>High Grade
 *  </ul>
 *  <p>
 *  The first line of the input file will be skipped since it contains the data headers.
 *  <p>
 *   
 * @author Angela Gordon
 */
//@SpringBootApplication (No longer using because need to exclude SchoolGeoLocationLoader and SchoolFinderApplication)
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SchoolGeoLocationLoader.class, SchoolFinderApplication.class}))
@EnableAutoConfiguration
public class SchoolDataLoader implements CommandLineRunner {

	// Data field positions in input file 
	private static final int NCES_ID = 0, LEA_ID = 1, DISTRICT = 2, SCHOOL = 3, 
			ADDRESS = 4, CITY = 5, STATE = 6, ZIP = 7, STATUS = 8, LOW_GRADE = 9, 
			HIGH_GRADE = 10;
    
    @Autowired
    private DistrictRepository districtRepository;
    
    @Autowired
    private SchoolRepository schoolRepository;
    
    /**
     * Main method kicked off by Spring boot
     */
	public static void main(String[] args) {
		SpringApplication.run(SchoolDataLoader.class, args);
	}
	
	/**
     * Kicks off the data load using the passed in data file
     * @param args	First argument identifies the data file that needs to be loaded
     * 				(i.e. /data/output/Parsed-Sch14pre.txt).
     */
	@Override
	public void run(String... args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage: java SchoolDataLoader <dataFileName>");
			return;
		}
		
		parseAndLoadData(args[0]);
	
	}
	/*
	 * Parses and loads school data into the School Finder database.
	 */
	private void parseAndLoadData(String inputFile) {
		
		String s = null;
		String lastLeaId = null;
		District district = null;
		
		int numSchools = 0, numNewSchools = 0;
		int numDistricts = 0, numNewDistricts = 0;
		
		try (Scanner scanner = new Scanner(this.getClass().getResourceAsStream(inputFile))) {
			if (scanner.hasNext()) {
				scanner.nextLine(); // skip header data
			}
			while (scanner.hasNext()) {
				s = scanner.nextLine();
				String[] tokens = s.split("\t");
				
				// don't repeatedly try to create a district already created in input file 
				// (note: records in input file are listed in order of districts)
				if (!tokens[LEA_ID].equals(lastLeaId)) {
					numDistricts++;
					district = districtRepository.findOneByLeaId(tokens[LEA_ID]);
					
					// if district doesn't already exist then create it 
					if (district == null) {
						district = new District(tokens[LEA_ID], tokens[DISTRICT]);
						districtRepository.save(district);
						numNewDistricts++;
					} 
					lastLeaId = district.getLeaId();
				}
				
				numSchools++;
				School school = schoolRepository.findOneByNcesId(tokens[NCES_ID]);
				
				if (school == null)  {
					school = new School(tokens[NCES_ID], tokens[SCHOOL], 
						tokens[ADDRESS], tokens[CITY], tokens[STATE], tokens[ZIP], 
						Integer.parseInt(tokens[STATUS]), tokens[LOW_GRADE], tokens[HIGH_GRADE], district);
					schoolRepository.save(school);
					numNewSchools++;
				}
			}
		} finally {
			System.out.println("Load results:");
			System.out.println("-----------------------------------------");
			System.out.println("# of Districts processed: " + numDistricts);
			System.out.println("# of Districts added to DB: " + numNewDistricts);
			System.out.println("# of Schools processed: " + numSchools);
			System.out.println("# of Schools added to DB: " + numNewSchools);
		}
	}

}
