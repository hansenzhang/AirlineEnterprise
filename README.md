Readme for database project:

Show interesting user ids.
put table informations in file.

Airplane table has information about latitude and longitude to calculate 
distance between airports.

Scheduled flight time should be in the project as well

Current flights map could work.

Calendar scheduling would be helpful for booking, however selection dates could be a problem

Also, getting autocomplete airports would be a bit difficult

Planned:
~2000 customers
equates to 
2500 total routes at 20 routes a day 
for 30 days for 100 customers per flight

MEANING -> each customer will have one flight per day for robustness.
Which in the real world, is a bit absurd but we'll do it anyways.


2052 total customers at 10 reservations each.
684 passengers flying per day



There are a total of ~15000 flights in the database ranging from April 16, 2013 to
June 14, 2013 (a 60 day span).  Each unique flight leg appears in this table 6 times.
in this time period on random days.  In total, there are an average of ~200 flights per day.


Scaled back proportions to only include 10 airports and 62 routes to and from airports.
Flight times will be hard-coded.
There are 3720 total unique flights in the leg_instance table, which each flight flying
once per day.  At 60 days total, there are 372,000 total flights in the database.

Pilots are assigned randomly.

All possible routes will fly every day to implement a usable scheduling interface
however the flight times will be randomized.
Times are NOT adjusted for timezones.  All times will land in the timezone of the origin.
I was going to originally calculate flight time based on airplane cruising speed 
and distance between airports but due to time constraints was not able to. 
Latitude and longitude are still in the airplane table, as well as a function to calculate distance between two airports (calc_distance).  Also, I was going to implement a map feature
to view plane location in real time, but there wasn't enough data to justify it.

We assume that all planes can land at all airports.  Airplane Ids range in the 10000s
to try to alleviate some confusion.

Each plane will fly the same route each day...
Assume that all pilots can fly all 3 planes.

Unfortunately, there are no connecting flights because I could not create an algorithm to 
insert them into the database on time to finish the project.  That is the same case for 
having reservations with multiple seats.  However, the database allows tables to have it.

Airport data was found on, bulk loaded from airport.csv into airport_staging.
Routes data is found in the route_staging table, bulk loaded from routes.csv
All of the names corresponding data was found here, file fakenames.sql
All other data generation was done in pl/sql attached in insert into tables.sql
and f.sql.  The pl/sql scripts are very sporadic.


