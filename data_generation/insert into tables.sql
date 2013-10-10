insert into fare_code values(1, 100, 'Domestic');
insert into fare_code values(2, 200, 'Domestic2');
insert into fare_code values(3, 300, 'International/Special');

select randomnumber(4) from dual connect by level <= 10
create sequence fares_seq START WITH 1 INCREMENT BY 1 nomaxvalue;
begin
for c1 in 1..62 loop
insert into fares values ((c1-1)+ 1000, randomnumber(4));
end loop;
end;

insert into airport  (
select airport_id, name, latitude_deg, longitude_deg, continent, iso_country, iso_region,
municipality, iata_code, local_code
from airport_staging where type = 'large_airport');

insert into flight_leg (
select * from staging2_flight_leg);

----------------------------
--INSERT INTO leg_instance--
----------------------------
exec reset_sequence ('leg_instance_seq');

declare 
  date_sum date;
  in_date date := SYSDATE;
begin 
  for datecounter in 1..60 loop
  date_sum := in_date + datecounter;
    for licounter in 1..62 loop      
      insert into leg_instance
      values (leg_instance_seq.nextval,date_sum);
    end loop;
  end loop;
end;

select * from flight_routes
--------------------------------------
-- INSERT INTO instance_of (WORKING)--
--------------------------------------
begin
  for instance_offset in 1..60 loop
    for instance_id in 1..62 loop
      insert into instance_of
      values (1000 + (instance_id - 1), 62 * (instance_offset - 1) + instance_id);
    end loop;
  end loop;
end;

-----------------------------
--INSERT INTO airplane_type--
-----------------------------

insert into airplane_type values(1, 'Boeing 737-900', 173, 530);
insert into airplane_type values(2, 'Boeing 747-400', 374, 567);
insert into airplane_type values(3, 'Embraer ERJ-145', 50, 530);

------------------------
--INSERT INTO airplane--
------------------------

create sequence airplane_id_seq START WITH 1 INCREMENT BY 1 nomaxvalue;
exec reset_sequence('airplane_id_seq');
begin
  
  for counter1 in 1..18 loop
    insert into airplane values (10000 + airplane_id_seq.nextval, 200);
  end loop;
  
  for counter2 in 1..22 loop
    insert into airplane values (10000 + airplane_id_seq.nextval, 100);
  end loop;
  
  for counter3 in 1..22 loop
    insert into airplane values (10000 + airplane_id_seq.nextval, 50);
  end loop;
end;


update airplane 
set total_seats = 100
where total_seats = 150
-----------------------
--INSERT INTO type_of--
-----------------------

create or replace function checkPlane
  (id_in IN number)
  return number
IS
  seat_count number (9, 0);
  out_id number(9,0);
  
  cursor c1 is 
  select total_seats
  from airplane
  where airplane_id = id_in;
begin
  open c1;
  fetch c1 into seat_count;
  close c1;
  
  if seat_count = 350 then
    out_id := 1;
    
  elsif seat_count = 150 then
    out_id := 2;
    
  else
    out_id := 3;
  
  end if;
  
  return out_id;
end;

select * from has_reservation

begin 
  for counter in 1..62 loop
    insert into type_of values(10000+counter, checkplane(10000+counter));
  end loop;
end;

select * from type_of
------------------------------
--EXPERIMENTAL CODE UNDER HERE--
------------------------------

create or replace function getPersonType(user_id number)
  return number
is
begin
  if (number )
  return trunc(dbms_random.value(1,limiter));
end;

select randomNumber(101) from dual connect by level <= 1000;

begin
  for instance_offset in 1..60 loop
    for instance_id in 1..62 loop
      insert into flys
      values (randomNumber(101), 62 * (instance_offset - 1) + instance_id);
    end loop;
  end loop;
end;


insert into pilot
(select entity_id from person where person_type = 1)

select * from pilot
select * from flys  join pilot on flys.pilot_id = pilot.pilot_id
join person on person.entity_id = pilot.pilot_id

insert into customer
(select entity_id from person where person_type = 2)

select * from customer

------------------------------
--EXPERIMENTAL CODE UNDER HERE--
------------------------------

insert into person
(select 'number', gender, title, givenname, middleinitial, surname, 
streetaddress, city, zipcode, country, countryfull, emailaddress, username
password, telephonenumber, maidenname, birthday, cctype, ccnumber
cvv2, ccexpires, nationalid , 2 from fakenames)

begin

  update person set person_type = 1 
  where entity_id <= 100

end;
select count(*) from person where person_type = 1
select * from person natural join flys


begin 
  for counter in 100001..327000 loop
    insert into reservation values (counter);
  end loop;
end;

select * from  reservation order by res_id desc
begin
  for counter in 1..350 loop
    insert into seat values (counter);
  end loop;
end;

commit;



CREATE SEQUENCE has_res_seq START WITH 1 INCREMENT BY 1 nomaxvalue;


-------------------------------
--INSERT INTO has_reservation--
-------------------------------
exec reset_sequence('has_res_seq')
declare
  seat_count number (9,0);
begin
for counter in 1..3720 loop
  seat_count := check_capacity(counter);
  if seat_count = 200 then
    seat_count := 150;
  elsif seat_count = 100 then
    seat_count := 85;
  else
    seat_count := 40;
  end if;
  for seatCounter in 1..seat_count loop
    insert into has_reservation values (has_res_seq.nextval, counter);
  end loop;
end loop;
end;

select * from has_reservation h join leg_instance l on h.leg_instance_id
= l.leg_instance_id

CREATE SEQUENCE has_seat_seq START WITH 1 INCREMENT BY 1 nomaxvalue;

exec reset_sequence('has_seat_seq');

select check_capacity(2) from dual

declare
  seat_count number (9,0);
begin
  for counter in 1..3720 loop
    seat_count := check_capacity(counter);
    if seat_count = 200 then
      seat_count := 150;
    elsif seat_count = 100 then
      seat_count := 85;
    else
      seat_count := 40;
    end if;
    for incounter in 1..seat_count loop
      insert into has_seat values (has_seat_seq.nextval, incounter);
    end loop;
  end loop;
end;

select * from leg_instance

begin 
for counter in 1..150 loop
  insert into has_seat values (has_seat_seq.nextval, counter);
end loop;
end;

CREATE SEQUENCE books_seq START WITH 1 INCREMENT BY 1 nomaxvalue;
exec reset_sequence('books_seq');

drop table books

begin
for counter in 1..160 loop
for peoplecounter in 101..2137 loop
insert into books values (peoplecounter, books_seq.nextval);
end loop;
end loop;
end;