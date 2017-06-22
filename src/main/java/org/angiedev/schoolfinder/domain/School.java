package org.angiedev.schoolfinder.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.angiedev.schoolfinder.domain.District;


/**
 *  School is a data model representing a School within a district.
 *  
 *  @author Angela Gordon
 */

@Entity
@Table(name="school")
public class School {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="school_id", nullable=false, unique=true)
	private int schoolId; 
	
	@Column(name="nces_id", nullable=false, unique=true, length=12)
	private String ncesId;
	
	@Column(name="name", nullable=false, length=200)
	private String name;
	
	@Column(name="street_address", nullable=false, length=100)
	private String streetAddress;
	
	@Column(name="city", nullable=false, length=40)
	private String city;
	
	@Column(name="state_code", nullable=false, length=2)
	private String stateCode;
	
	@Column(name="zip", nullable=false, length=12)
	private String zip;
	
	/** AN	NCES code for the school status:
      1 = School was operational at the time of the last report and is currently operational.
      2 = School has closed since the time of the last report.
      3 = School has been opened since the time of the last report.
      4 = School was in existence, but not reported in a previous year�s CCD school universe 
          survey, and is now being added.
      5 = School was listed in previous year�s CCD school universe as being affiliated with a different 
          education agency.
      6 = School is temporarily closed and may reopen within 3 years.
      7 = School is scheduled to be operational within 2 years.
      8 = School was closed on a previous year�s file but has reopened.
      **/
	@Column(name="status", nullable=false)
	private int status;
	
	@Column(name="low_grade", nullable=false, length=2)
	private String lowGrade;
	
	@Column(name="high_grade", nullable=false, length=2)
	private String highGrade;

	@Column(name="longitude",columnDefinition="FLOAT(10,6)" )
	private Double longitude;
	
	@Column(name="latitude",columnDefinition="FLOAT(10,6)" )
	private Double latitude;
	
	@ManyToOne
	@JoinColumn(name="district_id")
	private District district;
	
	/** 
	 * Creates an instance of a school.
	 * @param ncesId		unique id for school assigned by the National Center for Education Statistics (NCES).
	 * @param name			name of school.
	 * @param streetAddress	street address of school.
	 * @param city			the city the school is located in.
	 * @param state_code	the state the school is located in.
	 * @param zip			the zip code the school is located in.
	 * @param status		the status provided by the NCES for the school. 
	 * @param lowGrade		the lowest grade being taught at the school.
	 * @param highGrade 	the highest grade being taught at the school.
	 * @param district		the district the school belongs to.
	 */
	public School(String ncesId, String name, String streetAddress, String city, String stateCode, 
			String zip, int status, String lowGrade, String highGrade, District district) {
		this.ncesId = ncesId;
		this.name = name;
		this.streetAddress = streetAddress;
		this.city = city;
		this.stateCode = stateCode;
		this.zip = zip;
		this.status = status;
		this.lowGrade = lowGrade;
		this.highGrade=highGrade;
		this.district = district;
	}
	
	public School() {
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getNcesId() {
		return ncesId;
	}

	public void setNcesId(String ncesId) {
		this.ncesId = ncesId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLowGrade() {
		return lowGrade;
	}

	public void setLowGrade(String lowGrade) {
		this.lowGrade = lowGrade;
	}

	public String getHighGrade() {
		return highGrade;
	}

	public void setHighGrade(String highGrade) {
		this.highGrade = highGrade;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((highGrade == null) ? 0 : highGrade.hashCode());
		result = prime * result + ((lowGrade == null) ? 0 : lowGrade.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ncesId == null) ? 0 : ncesId.hashCode());
		result = prime * result + schoolId;
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + status;
		result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		School other = (School) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (highGrade == null) {
			if (other.highGrade != null)
				return false;
		} else if (!highGrade.equals(other.highGrade))
			return false;
		if (lowGrade == null) {
			if (other.lowGrade != null)
				return false;
		} else if (!lowGrade.equals(other.lowGrade))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ncesId == null) {
			if (other.ncesId != null)
				return false;
		} else if (!ncesId.equals(other.ncesId))
			return false;
		if (schoolId != other.schoolId)
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (status != other.status)
			return false;
		if (streetAddress == null) {
			if (other.streetAddress != null)
				return false;
		} else if (!streetAddress.equals(other.streetAddress))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "School [schoolId=" + schoolId + ", ncesId=" + ncesId + ", name=" + name + ", streetAddress="
				+ streetAddress + ", city=" + city + ", stateCode=" + stateCode + ", zip=" + zip + ", status=" + status
				+ ", lowGrade=" + lowGrade + ", highGrade=" + highGrade + ", longitude=" + longitude + ", latitude="
				+ latitude + ", district=" + district + "]";
	}
	
}
