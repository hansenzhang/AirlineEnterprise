create or replace function is_taken
  (res_id_in in number, seat_id_in in number)
  return number
is
  out_id number (9,0);
  leg_instance_id number (9,0);
  
  cursor c1 is
    
     select count(*) from has_reservation h
     join reservation r on h.res_id = r.res_id
     join has_seat on has_seat.res_id = h.res_id
     where has_seat.seat_id = seat_id_in
     and h.leg_instance_id = fetch_flight_id (res_id_in);
     
     --join has_seat on has_seat.res_id = r.res_id
     --where h.leg_instance_id = leg_instance_id_in;
  
begin
  leg_instance_id := fetch_flight_id(res_id_in);
  open c1;
  fetch c1 into out_id;
  close c1; 
  return out_id;
end;
select * from has_seat
select * from fares
select * from flight_leg order by leg_id
commit;
select is_taken (52, 1) from dual
select fetch_flight_id (51) from dual;
select check_current_flight(2289) from dual;

select * from reservation natural join has_reservation 

insert into has_reservation values (52, 2289)
insert into has_seat values (52, 1)

select * from flight_leg
select * from flight_times
select * from leg_instance

select * from seat

select distinct dest_airport, a1.name from flight_leg fl join airport a1
on dest_airport = a1.iata_code order by dest_airport

create or replace trigger check_seats
before update or insert on has_seat
for each row
declare
  max_seats number (9,0);
  cur_capacity number (9,0);
  leg_id number (9,0);
begin
  leg_id := fetch_flight_id(:new.res_id);
  max_seats := check_capacity(leg_id);
  cur_capacity := check_current_flight(leg_id);
  
  if cur_capacity >= max_seats then
    raise_application_error(-20000,'No space in flight');
  end if;
  
  if is_taken(:new.res_id, :new.seat_id) != 0 then
    raise_application_error(-20000,'Seat is already taken');
  end if;
end;

select randomnumber(11) from dual connect by level <=10

select * from person order by entity_id desc
insert into has_seat values (44, 1)
select * from person order by entity_id desc
begin
for counter in 1..40 loop
  insert into has_seat values (counter, counter);
end loop;
end;
select * from has_seat

select * from fare_code

select n from (select rownum n from dual connect by level <= 200)
where n not in
(select has_seat.seat_id from has_seat
join reservation r on has_seat.res_id = r.res_id
join has_reservation h on h.res_id = r.res_id 
join leg_instance li on li.leg_instance_id = h.leg_instance_id
where li.leg_instance_id = 1) order by n
select check_capacity(1000) from dual
select * from has_seat

delete has_seat 
select * from has_seat
select * from reservation r join has_reservation h on h.res_id = r.res_id
join has_seat on has_seat.res_id = r.res_id
delete has_seat
insert into has_seat values (50, 51)
select * from has_seat
delete from has_seat where res_id < 50
delete has_seat
insert into has_seat values (49, 50);


insert into has_reservation values (51, 2288)
select * from has_reservation
insert into has_seat 
show errors

insert into has_reservation values (1,1)
select * from has_reservation


delete has_reservation
select * from has_reservation

  select * from assigned
  select total_seats from leg_instance 
  join assigned on leg_instance.leg_instance_id = assigned.leg_instance_id
  join airplane on airplane.airplane_id = assigned.airplane_id


declare
flight_leg_no number := 1000;
begin
  for counter in 1..62 loop
    update staging2_flight_leg
    set leg_id =  (flight_leg_no + counter);
end loop;
end;

select * from staging2_flight_leg order by leg_id

begin
for counter in 1..60 loop
insert into tempnumbers (leg_id)
(select leg_id from flight_leg);
end loop;
end;
delete instance_of
select * from instance_of order by leg_instance_id

select * from tempnumbers

create table tempnumbers (
    leg_id number (9,0)
)

update instance_of 
set leg_id = (select * from tempnumbers)

select * from has_reservation



select * from seat
begin
for counter in 1..3720 loop
  insert into instance_of (leg_instance_id)
  values (random_seq.nextval);
end loop;
end;

select * from leg_instance order by leg_instance_id

delete instance_of
select * from instance_of order by leg_instance_id

select * from flight_leg order by leg_id

update staging2_flight_leg
set leg_id = 1005
where leg_id = 1100

select * from flight_leg order by leg_id


select * from instance_of

select * from leg_instance natural join instance_of
natural join flight_leg
where leg_instance_id <= 62 order by leg_id


select * from staging_flight_leg order by leg_id

select * from fakenames order by "number"

create table flight_leg_backup( 
  leg_id number(9,0),
  dest_airport varchar (10),
  src_airport varchar(10)
);

insert into person
(select "number", gender, title, givenname, middleinitial, surname, 
streetaddress, city, state, zipcode, country, countryfull, emailaddress, 
username, password, telephonenumber, maidenname, birthday, cctype, ccnumber,
cvv2, ccexpires, nationalid, 2 from fakenames)

select * from person order by entity_id

select * from legs

select * from reservation

select * from flight_leg
create table person(
    entity_id number(9,0),
    genter varchar2 (6) not null,
    title varchar2 (6) not null,
    firstname varchar2 (20) not null,
    midname varchar2 (1),
    lastname varchar2 (32) not null,
    address varchar2 (128), 
    city varchar2 (100),
    state varchar2 (22),
    zipcode varchar2 (15),
    country varchar2 (2),    
    country_full varchar2 (100),
    email varchar2 (128),
    username varchar2 (32),
    user_password varchar2 (48),
    telephone varchar2 (25),
    maiden_name varchar2 (20),
    birthday varchar(10),
    cctype varchar2 (10),
    ccnumber varchar2 (16),
    ccv2 varchar2 (3),
    ccexpires varchar2 (10),
    social_security varchar2 (15) not null,
    person_type number (9,0),
    constraint pk_entity primary key (entity_id)
);

select * from assigned

drop table has_seat
select * from has_seat

select * from instance_of

select distinct * from leg_instance join instance_of on instance_of.leg_instance_id = 
instance_of.leg_instance_id 
join flight_leg on instance_of.leg_id = flight_leg.leg_id
join has_reservation h on h.leg_instance_id = leg_instance.leg_instance_id
join reservation r on r.res_id = h.res_id 

select r.res_id, h.leg_instance_id, l.cur_date from reservation r
join has_reservation h on r.res_id = h.res_id
join leg_instance l on l.leg_instance_id = h.leg_instance_id
where r.res_id = 5451

select * from flys order by leg_instance_id
select * from leg_instance

select * from planes

select * from assigned order by leg_instance_id
select * from leg_instance natural join instance_of natural join flight_leg
natural join assigned natural join airplane

delete has_seat
select * from has_seat natural join reservation
natural join has_reservation
select * from has_seat
select * from has_reservation
select * from flight_leg where dest_airport = 'PEK' 
or src_airport = 'PEK' order by leg_id
select * from has_seat
select check_capacity(3677) from dual;
select check_capacity(fetch_flight_id(324274)) from dual
delete books
select * from books order by res_id desc
select * from person where person_type = 2 order by entity_id
select * from customer order by customer_id desc


select pad_zero(to_char(mod(to_char(sysdate,'hh') +
TRUNC(DBMS_RANDOM.VALUE(1,100)),12)))||':'||pad_zero(to_char(mod(to_char(sysdate,'mi') + 
TRUNC(DBMS_RANDOM.VALUE(1,100)),60)))||':'||pad_zero(to_char(mod(to_char(sysdate,'ss') + 
TRUNC(DBMS_RANDOM.VALUE(1,100)),60))) from dual



create or replace function random_time 
return varchar2
is 
rand_time varchar2 (10);
  cursor c1 is
  select mod(to_char(sysdate,'hh') + TRUNC(DBMS_RANDOM.VALUE(1,100)),24)||':'||mod(to_char(sysdate,'mi') + TRUNC(DBMS_RANDOM.VALUE(1,100)),60)||':'||mod(to_char(sysdate,'ss') + 
TRUNC(DBMS_RANDOM.VALUE(1,100)),60) from dual;
  
begin
  open c1;
  fetch c1 into rand_time;
  close c1;
  return rand_time;
end;
select to_date(random_time(), 'HH24:MI:SS') from dual connect by level <= 10

begin
for counter in 1..3720 loop
insert into flight_times values(counter, random_time(), random_time());
end loop;
end;

select count(*) from flight_times

select * from flight_leg

select random_time() from dual connect by level <= 10;
select pad_zero('1') from dual;
create or replace function pad_zero
(number_in in varchar2) return varchar2
is
begin
  return lpad(number_in, 2, '0');
end;
create or replace function calc_distance
(src_airport in varchar2, dest_airport in varchar2)
return number
is
  src_airport_long double precision;
  src_airport_lat double precision;
  dest_airport_long double precision;
  dest_airport_lat double precision;
  
  cursor c1 is 
  select latitude from airport 
  where airport.iata_code = src_airport;
  cursor c2 is 
  select longitude from airport 
  where airport.iata_code = src_airport;
  cursor c3 is 
  select latitude from airport 
  where airport.iata_code = dest_airport;
  cursor c4 is 
  select longitude from airport 
  where airport.iata_code = dest_airport;
  
begin
  open c1;
  fetch c1 into src_airport_lat;
  close c1; 
  
  open c2;
  fetch c2 into src_airport_long;
  close c2; 
  
  open c3;
  fetch c3 into dest_airport_lat;
  close c3; 
  
  open c4;
  fetch c4 into dest_airport_long;
  close c4; 
  return calc_coordinates(src_airport_lat, src_airport_long, dest_airport_lat
  , dest_airport_long);
end;
select * from person
select * from person order by entity_id desc
select * from user_sequences
select * from airline_staging
select * from person
begin


select calc_distance('JFK', 'IAH') from dual;

select check_current_flight(123) from dual


create or replace procedure load_fare
(rset_out out sys_refcursor)
is 
begin
  open rset_out for
  select fare_code, fare, type_of from fare_code order by fare_code;
  
end;  

create or replace procedure update_fare
(fare_code_in in number, newfare_in in number)
is
begin
  update fare_code set fare = newfare_in
  where fare_code = fare_code_in;
  
end;



select * from person

create or replace procedure delete_res
(resid_in in number, seatid_in in number)
is
begin
  delete from has_seat where res_id = resid_in and seat_id = seatid_in; 
  delete from books where res_id = resid_in;
  delete from has_reservation where res_id = resid_in;
  delete from reservation where res_id = resid_in;
  
end;