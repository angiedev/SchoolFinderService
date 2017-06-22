#!/bin/sh
#
# parseSchoolData.sh parses the NCES's US Schools Data File for the following info:
# $2 - Unique NCES public school ID (7-digit NCES agency ID (LEAID) + 5-digit NCES school ID (SCHNO)
# $4 - NCES local education agency (LEA) ID.
# $8 - Name of the education agency that operates this school (district)
# $9  -Name of the school.
# $16 - School location street address.
# $17 - School location city.
# $18 - Two-letter U.S. Postal Service abbreviation of the state where the school address is located
# $19 - Five-digit U.S. Postal Service ZIP code for the location address.
# $22 - Status (1 = School was operational at the time of the last report and is currently operational.
#               2 = School has closed since the time of the last report.
#               3 = School has been opened since the time of the last report.
#               4 = School was in existence, but not reported in a previous year’s CCD school universe
#                   survey, and is nowbeing added.
#               5 = School was listed in previous year’s CCD school universe as being affiliated with a
#                   different education agency.
#               6 = School is temporarily closed and may reopen within 3 years.
#               7 = School is scheduled to be operational within 2 years.
#               8 = School was closed on a previous year’s file but has reopened.)
# $25 - Lowest Grade offered (UG = Ungraded, PK = Prekindergarten, KG = Kindergarten, 01–12 = 1st through 12th
#       grade, N = School had no students reported, M = Missing)
# $26 - Highest Grade offered
#
# Usage: parseSchoolData.sh <inputFilename> <outputFilename>
#
# Current input file is named: Sch14pre.txt and was obtained from
# http://nces.ed.gov/ccd/pubschuniv.asp (2014-15 prelim dir)
# This file contains a listing of all of the public/private elementary and secondary schools in the
# United states in 2014-15.
#
# Current output file is named: Parsed-Sch14pre.txt.
#
# So script was run with:
#
# sh parseSchoolData.sh ../resources/data/input/Sch14pre.txt ../resources/data/output/Parsed-Sch14pre.txt
#
# This script should not have to be run again unless a new initial data input file is provided
# prior to initial launch of our application.

awk -F\t '{print $2 "\t" $4 "\t" $8 "\t" $9 "\t" $16 "\t" $17 "\t" $18 "\t" $19 "\t" $22 "\t" $25 "\t" $26 }' $1 > $2
