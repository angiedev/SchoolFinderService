package org.angiedev.schoolfinder.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  District is a data model representing a District (Local Education Agency (LEA)) responsible for a school. 
 *  
 *  @author Angela Gordon
 */
@Entity 
@Table(name="district")
public class District {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="district_id", nullable=false, unique=true)
	private int districtId;
	
	@Column(name="lea_id", nullable=false, length=7, unique=true)
	private String leaId; // local education agency id defined by NCES (Natl Center for Education Statistics)
	
	@Column(name="name", nullable=false, length=120)
	private String name; 
	

	public District() {
	}
	
	/**
	 * Creates a district with the passed in name and leaId.
	 * @param leaId		unique local education agency id defined by NCES (Natl Center for Education Statistics).
	 * @param name		name of district
	 */
	public District(String leaId, String name) {
		this.name = name;
		this.leaId = leaId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getLeaId() {
		return leaId;
	}

	public void setLeaId(String leaId) {
		this.leaId = leaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + districtId;
		result = prime * result + ((leaId == null) ? 0 : leaId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		District other = (District)obj;
		if (districtId != other.districtId)
			return false;
		if (leaId == null) {
			if (other.leaId != null)
				return false;
		} else if (!leaId.equals(other.leaId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "District [districtId=" + districtId + ", leaId=" + leaId + ", name=" + name + "]";
	}
	
}
