CREATE
DATABASE rail_go_db;
USE
rail_go_db;
CREATE TABLE tbl_users
(
    id              varchar(45) primary key,
    first_name      varchar(50)  not null,
    last_name       varchar(50)  not null,
    date_of_birth   datetime     not null,
    gender          varchar(45)  not null,
    role            varchar(45)  not null,
    phone_number    varchar(15)  not null unique,
    email           varchar(100) not null,
    hashed_password varchar(255) not null,
    created_at      timestamp    not null,
    updated_at      timestamp    not null
);
CREATE TABLE tbl_user_tokens
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
    created_at               timestamp    not null,
    updated_at               timestamp    not null
);
CREATE TABLE tbl_stations
(
    id              varchar(45) primary key,
    name            varchar(100) not null,
    country         varchar(100) not null,
    city            varchar(100) not null,
    latitude        DECIMAL(10, 8),
    longitude       DECIMAL(11, 8),
    phone_number    varchar(100),
    email           varchar(100),
    website         varchar(100),
    operating_hours varchar(100),
    status          varchar(20),
    created_at      timestamp    not null,
    updated_at      timestamp    not null
);
CREATE TABLE tbl_station_routes
(
    id           varchar(45) primary key,
    type         varchar(50),
    station_a_id varchar(45)    not null,
    station_b_id varchar(45)    not null,
    speed_limit  decimal(5, 2),
    distance_km  decimal(10, 2) not null,
    status       varchar(20),
    created_at   timestamp      not null,
    updated_at   timestamp      not null
);
CREATE TABLE tbl_trains
(
    id                varchar(45) primary key,
    type              varchar(20)  not null,
    name              varchar(100) not null,
    year_manufactured int          not null,
    speed_limit       int          not null, -- Speed limit (km/h)
    total_seats       int          not null,
    status            varchar(20)  not null,
    created_at        timestamp    not null,
    updated_at        timestamp    not null
);
CREATE TABLE tbl_train_schedules
(
    id           varchar(45) primary key,
    train_id     varchar(45)    not null,
    total_stops  int default 0,
    ticket_price decimal(10, 2) not null,
    currency     varchar(10)    not null,
    status       varchar(20)    not null,
    created_at   timestamp      not null,
    updated_at   timestamp      not null
);
CREATE TABLE tbl_train_schedule_stops
(
    id              varchar(45) primary key,
    schedule_id     varchar(45)    not null,
    stop_order      int            not null,
    station_id      varchar(45)    not null,
    departure_time  datetime       not null,
    next_station_id varchar(45)    not null,
    arrival_time    datetime       not null,
    available_seats int            not null,
    ticket_price    decimal(10, 2) not null,
    currency        varchar(10)    not null,
    created_at      timestamp      not null,
    updated_at      timestamp      not null
);
CREATE TABLE tbl_tickets
(
    id                varchar(45) primary key,
    train_schedule_id varchar(45)    not null,
    start_station_id  varchar(45)    not null,
    end_station_id    varchar(45)    not null,
    child_seats       int default 0,
    adult_seats       int default 0,
    senior_seats      int default 0,
    total_price       decimal(10, 2) not null,
    currency          varchar(10)    not null,
    contact_email     varchar(100),
    expiration_time   datetime       not null,
    status            varchar(20)    not null,
    created_at        timestamp      not null,
    updated_at        timestamp      not null
);
CREATE TABLE tbl_ticket_passengers
(
    id            varchar(45) primary key,
    first_name    varchar(50) not null,
    last_name     varchar(50) not null,
    date_of_birth datetime    not null,
    created_at    timestamp   not null,
    updated_at    timestamp   not null
);
CREATE TABLE tbl_payments (

);
