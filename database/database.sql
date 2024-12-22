CREATE
DATABASE rail_go_db;
USE
rail_go_db;
CREATE TABLE tbl_users
(
    id              varchar(36) primary key,
    first_name      varchar(50)  not null,
    last_name       varchar(50)  not null,
    date_of_birth   datetime     not null,
    gender          varchar(36)  not null,
    role            varchar(36)  not null,
    phone_number    varchar(15)  not null unique,
    email           varchar(100) not null,
    hashed_password varchar(255) not null,
    created_at      timestamp    not null,
    updated_at      timestamp    not null
);
CREATE TABLE tbl_user_tokens
(
    id                       varchar(36) primary key,
    account_id               varchar(36)  not null,
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
    id              varchar(36) primary key,
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
    id           varchar(36) primary key,
    type         varchar(50),
    station_a_id varchar(36)    not null,
    station_b_id varchar(36)    not null,
    speed_limit  decimal(5, 2),
    distance_km  decimal(10, 2) not null,
    status       varchar(20),
    created_at   timestamp      not null,
    updated_at   timestamp      not null
);
CREATE TABLE tbl_trains
(
    id                  varchar(36) primary key,
    type                varchar(20)    not null,
    name                varchar(100)   not null,
    year_manufactured   int            not null,
    speed_limit         int            not null, -- Speed limit (km/h)
    total_seats         int            not null,
    status              varchar(20)    not null,
    created_at          timestamp      not null,
    updated_at          timestamp      not null
);
CREATE TABLE tbl_train_schedules
(
    id                   varchar(36) primary key,
    creator_id           varchar(36)    not null,
    train_id             varchar(36)    not null,
    departure_station_id varchar(36)    not null,
    arrival_station_id   varchar(36)    not null,
    departure_time       datetime       not null,
    arrival_time         datetime       not null,
    total_stops          int default 0,
    ticket_price         decimal(10, 2) not null,
    currency             varchar(10)    not null,
    status               varchar(20)    not null,
    created_at           timestamp      not null,
    updated_at           timestamp      not null
);
CREATE TABLE tbl_train_schedule_routes
(
    id           varchar(36) primary key,
    schedule_id  varchar(36)    not null,
    station_id   varchar(36)    not null,
    arrival_time datetime       not null,
    ticket_price decimal(10, 2) not null,
    currency     varchar(10)    not null,
    created_at   timestamp      not null,
    updated_at   timestamp      not null
);
CREATE TABLE tbl_tickets
(
    id               varchar(36) primary key,
    user_id          varchar(36),
    schedule_id      varchar(36)    not null,
    start_station_id varchar(36)    not null,
    end_station_id   varchar(36)    not null,
    return_ticket_id varchar(36),
    total_price      decimal(10, 2) not null,
    currency         varchar(10)    not null,
    discount_id      varchar(36),
    status           varchar(20)    not null,
    created_at       timestamp      not null,
    updated_at       timestamp      not null
);
