select leg_id, a2.name as src_airport, a2.iata_code as src_code,a1.name as dest_airport, a1.iata_code as dst_code
from flight_leg join airport a1 on dest_airport = a1.iata_code
join airport a2 on src_airport = a2.iata_code
where flight_leg.dest_airport = 'PEK' 
order by dst_code

select cur_date, count(*) from leg_instance group by cur_date order by cur_date

select * from leg_instance join flight_leg on leg_instance.leg_instance_id = flight_leg.leg_id where flight_leg.leg_id = 1120

select * from leg_instance order by leg_instance_id

insert into airplane_types 
select * from flight_leg

insert into staging_flight_leg
(select leg_id, dest_airport, src_airport
from flight_leg where 
src_airport = 'JFK'
)
select * from staging_flight_leg order by leg_id


create or replace procedure view_res
  (user_id in number,
  rset_out OUT SYS_REFCURSOR)
is
begin
  open rset_out for 
  select r.res_id, fl.src_airport, fl.dest_airport, l.cur_date,
  ft.dep_time, ft.arr_time, seat.seat_id, fc.fare
from person join customer c on c.customer_id = person.entity_id 
join books on books.customer_id = c.customer_id
join reservation r on r.res_id = books.res_id
join has_seat on has_seat.res_id = r.res_id
join seat on seat.seat_id = has_seat.seat_id
join has_reservation h on h.res_id = r.res_id
join leg_instance l on l.leg_instance_id = h.leg_instance_id
join flight_times ft on ft.leg_instance_id = l.leg_instance_id
join instance_of lo on lo.leg_instance_id = l.leg_instance_id
join flight_leg fl on fl.leg_id = lo.leg_id
join fares f on fl.leg_id = f.flight_id
join fare_code fc on fc.fare_code = f.fare_code
where c.customer_id = user_id;
end;

create or replace procedure view_flights
(src_airport_in in varchar2, dst_airport_in in varchar2,
date_in in varchar2, rset_out out sys_refcursor)
is
  new_id number(9,0);
begin
  select li.leg_instance_id into new_id from flight_leg fl 
    join instance_of io on fl.leg_id = io.leg_id
    join leg_instance li on li.leg_instance_id = io.leg_instance_id
    where fl.src_airport = src_airport_in
      and fl.dest_airport = dst_airport_in
      and to_char(li.cur_date) = date_in;
  
  open rset_out for select fl.src_airport, fl.dest_airport, l.cur_date,
  ft.dep_time, ft.arr_time, l.leg_instance_id
from leg_instance l 
join flight_times ft on ft.leg_instance_id = l.leg_instance_id
join instance_of lo on lo.leg_instance_id = l.leg_instance_id
join flight_leg fl on fl.leg_id = lo.leg_id
join fares f on fl.leg_id = f.flight_id
join fare_code fc on fc.fare_code = f.fare_code
  where l.leg_instance_id = new_id;
end;

select li.leg_instance_id from flight_leg fl 
  join instance_of io on fl.leg_id = io.leg_id
  join leg_instance li on li.leg_instance_id = io.leg_instance_id
where fl.src_airport = 'JFK' 
  and fl.dest_airport = 'PEK'
  and to_char(li.cur_date) = '22-APR-13';

select * from leg_instance
where cur_date is to_date('17-APR-13', 'DD-MON-YY') ;

select to_date('17-APR-13', 'DD-MON-YY') from

select r.res_id, fl.src_airport, fl.dest_airport, l.cur_date,
  ft.dep_time, ft.arr_time, seat.seat_id, fc.fare
from person join customer c on c.customer_id = person.entity_id 
join books on books.customer_id = c.customer_id
join reservation r on r.res_id = books.res_id
join has_seat on has_seat.res_id = r.res_id
join seat on seat.seat_id = has_seat.seat_id
join has_reservation h on h.res_id = r.res_id
join leg_instance l on l.leg_instance_id = h.leg_instance_id
join flight_times ft on ft.leg_instance_id = l.leg_instance_id
join instance_of lo on lo.leg_instance_id = l.leg_instance_id
join flight_leg fl on fl.leg_id = lo.leg_id
join fares f on fl.leg_id = f.flight_id
join fare_code fc on fc.fare_code = f.fare_code
where c.customer_id = 103 order by r.res_id;
create or replace procedure check_seat
(instance_in in number,
  rset_out OUT SYS_REFCURSOR)
is 
begin
  open rset_out for 
  select n from 
  (select rownum n from dual connect by level <= check_capacity(instance_in))
    where n not in
  (select has_seat.seat_id from has_seat
  join reservation r on has_seat.res_id = r.res_id
  join has_reservation h on h.res_id = r.res_id 
  join leg_instance li on li.leg_instance_id = h.leg_instance_id
  where li.leg_instance_id = instance_in) order by n;
end;
select * from reservation r join has_reservation h
on r.res_id = h.res_id join leg_instance on leg_instance.leg_instance_id = h.leg_instance_id
where r.res_id = 327006 order by r.res_id desc
select * from has_seat 

create or replace procedure insert_reservation
(leg_instance_id_in in number,seat_id_in in number, 
customer_id_in in number)
is
  new_res_id number := has_res_seq.nextval;
begin
  insert into reservation values (new_res_id);
  insert into books values (customer_id_in, new_res_id);
  insert into has_reservation values (new_res_id, leg_instance_id_in);
  insert into has_seat values (new_res_id, seat_id_in);
end;

select * from resrvat
select * from books where customer_id = 103
create or replace procedure customer_login
(username_in in varchar2, password_in in varchar2, rset_out OUT SYS_REFCURSOR)
is
begin
  open rset_out for select entity_id, firstname from person where
    username = username_in and user_password = password_in;
end;

create or replace procedure update_customer
(cust_in in number, gender_in in varchar2, title_in in varchar2, firstname_in in varchar2,
midname_in in varchar2, lastname_in in varchar2, address_in in varchar2,
city_in in varchar2, state_in in varchar2, zipcode_in in varchar2, 
email_in in varchar2, username_in in varchar2, password_in in varchar2,
telephone_in in varchar2, maiden_name_in in varchar2, 
birthday_in in varchar2, cctype_in in varchar2, ccnumber_in in varchar2,
ccv2_in in varchar2,ccexpires_in in varchar2, social_security_in in varchar2
)
is
begin
  update person set (entity_id, genter, title, firstname, midname,lastname,
  address, city, state, zipcode, country, country_full, email, username,
  user_password, telephone, maiden_name, birthday, cctype, ccnumber,ccv2,
  ccexpires, social_security, person_type) = 
  (select cust_in, gender_in, title_in,
    firstname_in, midname_in, lastname_in, address_in,
    city_in, state_in, zipcode_in, 'US', 'United States', email_in,
    username_in, password_in, telephone_in, maiden_name_in,
    birthday_in, cctype_in, ccnumber_in, ccv2_in, ccexpires_in, 
    social_security_in, 2 from person where entity_id = cust_in
    ) where person.entity_id = cust_in;
end;

create or replace function check_user
(username_in in varchar2) return number
is
  boolean_out number;
  
  cursor c1 is
  select count(username) from person where username = username_in;
  
begin
  open c1;
  fetch c1 into boolean_out;
  close c1;
  
  if boolean_out = 0 then
    return 0;
  else
    return 1;
  end if;
end;

create or replace procedure getUserInfo
(userid_in in number, rset_out out SYS_REFCURSOR)
is
begin
  open rset_out for select genter, title, firstname, midname,
  lastname, address, city, state, zipcode, email, username,
  user_password, telephone, maiden_name, birthday, cctype, ccnumber,
  ccv2, ccexpires, social_security from person 
  where entity_id = userid_in;
end;

select * from has_seat