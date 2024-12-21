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
    id                varchar(36) primary key,
    type              varchar(20)  not null,
    name              varchar(100) not null,
    year_manufactured int          not null,
    speed_limit       int          not null, -- Speed limit (km/h)
    total_coaches     int          not null,
    status            varchar(20)  not null,
    created_at        timestamp    not null,
    updated_at        timestamp    not null
);
CREATE TABLE tbl_train_coaches
(
    id           varchar(36) primary key,
    train_id     varchar(36) not null,
    coach_number int         not null,
    type         varchar(20),
    seat_count   int         not null,
    created_at   timestamp   not null,
    updated_at   timestamp   not null
);
CREATE TABLE tbl_train_coach_services
(
    id          varchar(36) primary key,
    coach_id    varchar(36)  not null,
    name        varchar(100) not null,
    price       decimal(10, 2),
    currency    varchar(10)  not null,
    description text,
    status      varchar(20)  not null,
    created_at  timestamp    not null,
    updated_at  timestamp    not null
);
CREATE TABLE tbl_train_coach_seats
(
    id           varchar(36) primary key,
    coach_id     varchar(36) not null,
    code         varchar(20) not null,
    is_window    boolean default false,
    is_available boolean default true,
    created_at   timestamp   not null,
    updated_at   timestamp   not null
);
CREATE TABLE tbl_train_schedules
(
    id                   varchar(36) primary key,
    creator_id           varchar(36) not null,
    train_id             varchar(36) not null,
    departure_station_id varchar(36) not null,
    arrival_station_id   varchar(36) not null,
    departure_time       datetime    not null,
    arrival_time         datetime    not null,
    total_stops          int default 0,
    status               varchar(20) not null,
    created_at           timestamp   not null,
    updated_at           timestamp   not null
);

CREATE TABLE tbl_train_schedule_routes
(
    id           varchar(36) primary key,
    schedule_id  varchar(36) not null,
    station_id   varchar(36) not null,
    arrival_time datetime    not null,
    created_at   timestamp   not null,
    updated_at   timestamp   not null
);
CREATE TABLE tbl_discounts
(
    id               varchar(36) primary key,
    creator_id       varchar(36),
    code             varchar(50)    not null,
    description      varchar(50),
    type             varchar(20)    not null,
    value            decimal(10, 2) not null,
    start_date       datetime       not null,
    end_date         datetime       not null,
    application_type varchar(20)    not null,
    application_id   varchar(36),
    status           varchar(20),
    created_at       timestamp      not null,
    updated_at       timestamp      not null
);
CREATE TABLE tbl_tickets
(
    id               varchar(36) primary key,
    user_id          varchar(36)    not null,
    start_station_id varchar(36)    not null,
    end_station_id   varchar(36)    not null,
    seat_id          varchar(36)    not null,
    departure_time   datetime       not null,
    arrival_time     datetime       not null,
    type             varchar(20)    not null,
    return_ticket_id varchar(36),
    price            decimal(10, 2) not null,
    discount_id      varchar(36),
    status           varchar(20)    not null,
    created_at       timestamp      not null,
    updated_at       timestamp      not null
);
CREATE TABLE tbl_payments
(
    id             varchar(36) primary key,
    ticket_id      varchar(36)    not null,
    user_id        varchar(36)    not null,
    method         varchar(50)    not null,
    amount         decimal(10, 2) not null,
    date           datetime       not null,
    status         varchar(20)    not null,
    transaction_id varchar(50),
    created_at     timestamp      not null,
    updated_at     timestamp      not null
);