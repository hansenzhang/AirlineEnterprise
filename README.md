---------
--About--
---------

Hansen Zhang - Hsz215
Readme for Enterprise Database:

All data generation is found under ~/Zhang_Hansen/data_generation
Java source files are found in ~/Zhang_hansen/java_src

Run enterprise.jar to start the program (there is no loading screen so please be
patient for slow connections).

---------
--Usage--
---------
The default view for Enterprise shows options for either Customer, Manager, or exit.

The customer JFrame is called after a user logs in using their id and password in the 
database.  A default use is provided for testing.  If the user doesn't exist then 
there is an option to create a new user. It is possible to leave the login screen by 
closing the window.  After logging in the user has the option to view their reservations, 
book a new reservation, delete a reservation, edit their profile or logout.  View user
shows a table with all current reservations for the user.  Clicking on each reservation
shows more information about the flight.  Booking a reservation selects two airports and a
date where you can then select a flight and a corresponding seat. Delete reservation
lists all current flights and deletes the user selected flight.


The manager JFrame allows the user to change fares for flights, create a new customer,
and modify current reservations.  There are only 3 fares in the database now for 
simplicity sake, but the prices can be changed when clicked on.  The create customer
JFrame is the same as the create customer on the customer side.  Modify Reservation 
requests a customer id (ranges are given in the popup itself, I didn't have enough
time to verify customer ids) and from there opens up a new JFrame that allows managers
to edit, view, and delete reservations for that customer, using the same interfaces that
the customers use.
------------------------
--Database information--
------------------------

~2000 customers total. 
62 unique flight routes between 10 airports.
Each flight route flies once per day for the next 60 days, which corresponds to a total of 
3720 flights in the next 60 days.
Each flight has a random amount of seats based on flight size (approx. average of 150)
which brings a total of about 300,000 total seats in the database.  Only about 100 seats 
are taken in each flight to allow for additional booking.  The database does not allow
multiple seats in a flight to be taken.

Pilots are assigned randomly.

All possible routes will fly every day to implement a usable scheduling interface
however the flight times will be randomized.
Times are NOT adjusted for timezones.  All times will land in the timezone of the origin.
I was going to originally calculate flight time based on airplane cruising speed 
and distance between airports but due to time constraints was not able to. 
Latitude and longitude are still in the airplane table, as well as a function to calculate distance between two airports (calc_distance).  Also, I was going to implement a map feature to view plane location in real time, but there wasn't enough data to justify it.

We assume that all planes can land at all airports.  Airplane Ids range in the 10000s
to try to alleviate some confusion.

Each plane will fly the same route each day and assume that all pilots can fly all 3 planes.

Unfortunately, there are no connecting flights because I could not create an algorithm to 
insert them into the database on time to finish the project.  That is the same case for 
having reservations with multiple seats.  However, the database allows tables to have it.


--------
--Data--
--------
Airport data was found on, bulk loaded from airport.csv into airport_staging.
Routes data is found in the route_staging table, bulk loaded from routes.csv.
Both of these files were found from http://openflights.org/data.html.

All of the names corresponding data was found in http://www.fakenamegenerator.com/order.php, where the file is fakenames.sql
All other data generation was done in pl/sql attached in insert into tables.sql
and f.sql.  The pl/sql scripts are very sporadic and hacked together, so don't rely on 
them.


