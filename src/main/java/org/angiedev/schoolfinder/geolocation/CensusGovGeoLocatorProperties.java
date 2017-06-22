package org.angiedev.schoolfinder.geolocation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("censusgov.geolocator")
public class CensusGovGeoLocatorProperties {
	private int readTimeout;
	private int connectTimeout;
	private String censusGovGeoLocatorUrl;
	
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public String getCensusGovGeoLocatorUrl() {
		return censusGovGeoLocatorUrl;
	}
	public void setCensusGovGeoLocatorUrl(String censusGovGeoLocatorUrl) {
		this.censusGovGeoLocatorUrl = censusGovGeoLocatorUrl;
	}

}
