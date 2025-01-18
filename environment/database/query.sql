create index idx_flight_route on tbl_flights(departure_airport_code, arrival_airport_code, departure_time);

select * from tbl_flight
where de