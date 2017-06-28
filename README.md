# School Finder Service API

## Overview

The School Finder Service is a REST based web service API which returns address and grade level data for private and public schools in the United States. The data used to support this service was obtained from the National Center for Educational Statistics.

Communication is done over HTTP using GET requests.  The response data is formatted as JSON and the content type is application/json.

## School Finder Requests 

School finder requests are sent using GET using the following URLs:


* **To retrieve a list of schools based on a given geo location and search radius**

   http://[hostname]:[port]/schools?lat=[latitude]&long=[longitude]&radius=[radius]

   with
   * **latitude**: latitude of position to center search from 
   * **longitude**: longitude of position to center search from 
   * **radius**: search radius in miles
 
* **To retrieve a list of schools based on a given geo location, search radius and school name**

   http://[hostname]:[port]/schools?lat=[latitude]&long=[longitude]&radius=[radius]&schoolName=[schoolName]

   with
   * **latitude**: latitude of position to center search from 
   * **longitude**: longitude of position to center search from 
   * **radius**: search radius in miles 
   * **schoolName**: name of school to search for (All schools whose names contain the passed in schoolName string will be returned.)


* **To retrieve a school by the National Center Educaton Statistics** 

   http://[hostname]:[port]/schools/[ncesId] 
   
   with
   * **ncesId**: School Id used to idenitfy a school by the National Center for Educational Statistics.
   * 
   
**School finder responses are returned as a json object as shown:**

Result set for multiple schools:
```javascript
{
  [
    {"schoolId":14072,
     "ncesId":"063459005707",
     "name":"Castillero Middle",
     "streetAddress":"6384 Leyland Park Dr.",
     "city":"San Jose",
     "stateCode":"CA",
     "zip":"95120",
     "status":1,
     "lowGrade":"06",
     "highGrade":"08",
     "longitude":-121.881851,
     "latitude":37.220993,
     "district":
        {"districtId":2029,
        "leaId":"0634590",
        "name":"SAN JOSE UNIFIED"}
    },
    {"schoolId":14084,
    "ncesId":"063459005727",
    "name":"Los Alamitos Elementary",
    "streetAddress":"6130 Silberman Ave.",
    "city":"San Jose",
    "stateCode":"CA",
    "zip":"95120",
    "status":1,
    "lowGrade":"KG",
    "highGrade":"05",
    "longitude":-121.881416,
    "latitude":37.227291,
    "district":
        {"districtId":2029,
         "leaId":"0634590",
         "name":"SAN JOSE UNIFIED"}
    }
  ]
}
```

Result set for single school : 
```javascript
{
    "schoolId":5733,
    "ncesId":"060000113793",
    "name":"Scale Leadership Academy",
    "streetAddress":"1206 Lincoln Ave.",
    "city":"Pasadena, Ca",
    "stateCode":"CA",
    "zip":"91103",
    "status":3,
    "lowGrade":"06",
    "highGrade":"08",
    "longitude":-118.159081,
    "latitude":34.166985,
    "district":
        {
            "districtId":1182,
            "leaId":"0600001",
            "name":"ACTON-AGUA DULCE UNIFIED"
        }
}
```
