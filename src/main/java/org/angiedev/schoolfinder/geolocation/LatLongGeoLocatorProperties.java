package org.angiedev.schoolfinder.geolocation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("latlong.geolocator")
public class LatLongGeoLocatorProperties {
	
	private int readTimeout;
	private int connectTimeout;
	private String latLongGeoLocatorUrl;
	
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
	public String getLatLongGeoLocatorUrl() {
		return latLongGeoLocatorUrl;
	}
	public void setLatLongGeoLocatorUrl(String latLongLocatorUrl) {
		this.latLongGeoLocatorUrl = latLongLocatorUrl;
	}

}
