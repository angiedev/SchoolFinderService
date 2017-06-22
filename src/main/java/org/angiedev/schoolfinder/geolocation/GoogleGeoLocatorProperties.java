package org.angiedev.schoolfinder.geolocation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("google.geolocator")
public class GoogleGeoLocatorProperties {
	
	private int readTimeout;
	private int connectTimeout;
	private String googleGeoLocatorUrl;
	
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
	public String getGoogleGeoLocatorUrl() {
		return googleGeoLocatorUrl;
	}
	public void setGoogleGeoLocatorUrl(String googleGeoLocatorUrl) {
		this.googleGeoLocatorUrl = googleGeoLocatorUrl;
	}

}
