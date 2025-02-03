CREATE
    DATABASE airline_db;
use
    airline_db;
create table tbl_users
(
    id              varchar(45) primary key,
    oauth2_type     varchar(45),
    first_name      varchar(50)  not null,
    last_name       varchar(50)  not null,
    date_of_birth   datetime     not null,
    gender          varchar(45)  not null,
    role            varchar(45)  not null,
    phone_number    varchar(15)  not null unique,
    email           varchar(100) not null unique,
    hashed_password varchar(255) not null,
    is_locked       boolean default false,
    is_active       boolean default false,
    created_at      timestamp,
    updated_at      timestamp
);

create table tbl_user_tokens
(
    id                       varchar(45) primary key,
    account_id               varchar(45)  not null,
    value                    varchar(255) not null,
    expires_at               timestamp    not null,
    issued_at                timestamp    not null,
    is_revoked               boolean default false,
    refresh_token            varchar(255) not null,
    refresh_token_expires_at timestamp    not null,
    ip_address               varchar(45),
    user_agent               varchar(255),
    created_at               timestamp,
    updated_at               timestamp
);
CREATE TABLE tbl_airlines
(
    id         varchar(45) PRIMARY KEY,
    name       varchar(100) NOT NULL,
    country    varchar(100) NOT NULL,
    logo_url   varchar(255),
    status     varchar(45)  not null,
    created_at timestamp,
    updated_at timestamp
);

create table tbl_airports
(
    id         varchar(45) primary key,
    code       varchar(5)   not null,
    name       varchar(100) not null,
    country    varchar(100) not null,
    city       varchar(100) not null,
    status     varchar(45)  not null,
    created_at timestamp,
    updated_at timestamp
);
create table tbl_flights
(
    id                     varchar(45) primary key,
    airline_id             varchar(45)    not null,
    code                   varchar(30)    not null,
    departure_airport_code varchar(5)     not null,
    arrival_airport_code   varchar(5)     not null,
    departure_time         DATETIME       not null,
    arrival_time           DATETIME       not null,
    description            varchar(255),
    standard_price         decimal(10, 2) not null,
    currency               varchar(10)    not null,
    total_seats            int            not null,
    available_seats        int            not null,
    status                 varchar(45)    not null,
    version                int auto_increment,
    created_at             timestamp,
    updated_at             timestamp
);

create index idx_flight_route on tbl_flights (departure_airport_code, arrival_airport_code, departure_time);

with RecursiveFlights as (select 'connecting' as flight_type
                          from tbl_flights f1
                                   join tbl_flights f2
                                        on f1.arrival_airport_code = f2.departure_airport_code
                                            and f1.arrival_time < f2.departure_time
                          where f1.status = 'active'),
     DirectFlights as (select 'direct' as flight_type
                       from tbl_flights
                       where departure_airport_code = :departure_airport_code
                         and arrival_airport_code = :arrival_airport_code
                         and departure_time between :startDate and :endDate
                         and status = 'active')
select *
from DirectFlights
union all
select *
from RecursiveFlights;

create table tbl_tickets
(
    id              varchar(45) primary key,
    flight_id       varchar(45)    not null,
    child_seats     int default 0,
    adult_seats     int default 0,
    total_price     decimal(10, 2) not null,
    currency        varchar(10)    not null,
    contact_email   varchar(100),
    expiration_time datetime       not null,
    payment_id      varchar(45),
    status          varchar(45)    not null,
    created_at      timestamp,
    updated_at      timestamp
);
create table tbl_ticket_passengers
(
    id            varchar(45) primary key,
    full_name     varchar(100) not null,
    date_of_birth datetime     not null,
    created_at    timestamp,
    updated_at    timestamp
);
create table tbl_discounts
(
    id                  varchar(45) primary key,
    code                varchar(50) unique,
    description         text,
    type                varchar(20)    not null,
    value               decimal(10, 2) not null,
    max_discount_amount decimal(10, 2),
    start_date          datetime       not null,
    end_date            datetime       not null,
    usage_limit         int,
    usage_count         int default 0,
    status              varchar(45)    not null,
    created_at          timestamp,
    updated_at          timestamp
);
create table tbl_payments
(
    id              varchar(45) primary key,
    transaction_id  varchar(255),
    payment_gateway varchar(100),
    payment_method  varchar(50),
    payment_time    datetime,
    amount          decimal(10, 2) not null,
    currency        varchar(10)    not null,
    ip_address      varchar(45),
    user_agent      varchar(255),
    status          varchar(45)    not null,
    created_at      timestamp,
    updated_at      timestamp
);
