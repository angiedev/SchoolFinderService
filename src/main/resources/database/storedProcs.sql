
DELIMITER //

DROP PROCEDURE IF EXISTS `GetSchoolsNearGeoLocation` //
CREATE PROCEDURE GetSchoolsNearGeoLocation( 
   IN latitudeVal FLOAT(10,6), 
   IN longitudeVal FLOAT(10,6), 
   IN radiusVal INT)
BEGIN
   SELECT school_id, nces_id, name, district_id, street_address, city, state_code,
      zip, status, low_grade, high_grade, longitude, latitude, 
      3956 * 2 * ASIN(SQRT( POWER(SIN((latitudeVal - latitude)*pi()/180/2),2)
	  + COS(latitudeVal*pi()/180 ) * COS(latitude*pi()/180) 
      *POWER(SIN((longitudeVal-longitude)*pi()/180/2),2))) as distance
   FROM school 
   WHERE  longitude between (longitudeVal- radiusVal/cos(radians(latitudeVal))*69) and
      (longitudeVal+ radiusVal/cos(radians(latitudeVal))*69) AND
      latitude between (latitudeVal-(radiusVal/69)) and (latitudeVal+(radiusVal/69))
   HAVING distance < radiusVal order by name;
END //
DELIMITER ;
DELIMITER //
DROP PROCEDURE IF EXISTS `GetSchoolsNearGeoLocationWithNameMatchingSearchString` //
CREATE PROCEDURE GetSchoolsNearGeoLocationWithNameMatchingSearchString( 
   IN latitudeVal FLOAT(10,6), 
   IN longitudeVal FLOAT(10,6), 
   IN radiusVal INT,
   IN searchString VARCHAR(200))
BEGIN
   SELECT school_id, nces_id, name, district_id, street_address, city, state_code,
      zip, status, low_grade, high_grade, longitude, latitude, 
      3956 * 2 * ASIN(SQRT( POWER(SIN((latitudeVal - latitude)*pi()/180/2),2)
	  + COS(latitudeVal*pi()/180 ) * COS(latitude*pi()/180) 
      *POWER(SIN((longitudeVal-longitude)*pi()/180/2),2))) as distance
   FROM school 
   WHERE  longitude between (longitudeVal- radiusVal/cos(radians(latitudeVal))*69) and
      (longitudeVal+ radiusVal/cos(radians(latitudeVal))*69) AND
      latitude between (latitudeVal-(radiusVal/69)) and (latitudeVal+(radiusVal/69)) and 
      name like CONCAT('%',searchString,'%')
   HAVING distance < radiusVal order by name;
END //
DELIMITER ;