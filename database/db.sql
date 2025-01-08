CREATE
DATABASE airline_db;
USE
airline_db;
create table tbl_users
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
create table tbl_airports
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
    created_at      timestamp,
    updated_at      timestamp
);
create table tbl_airplanes
(
    id                varchar(45) primary key,
    type              varchar(20)  not null,
    name              varchar(100) not null,
    year_manufactured int          not null,
    speed_limit       int          not null,
    total_seats       int          not null,
    status            varchar(20)  not null,
    created_at        timestamp,
    updated_at        timestamp
);
CREATE TABLE tbL_airplane_seat_classes
(
    id          varchar(45) primary key,
    type        varchar(25) not null,
    name        varchar(50) NOT NULL,
    description varchar(255)
);
create table tbl_flights
(
    id                   varchar(45) primary key,
    airplane_id          varchar(45) not null,
    departure_airport_id varchar(45) not null,
    arrival_airport_id   varchar(45) not null,
    departure_time       DATETIME    NOT NULL,
    arrival_time         DATETIME    NOT NULL,
    description          varchar(255),
    status               varchar(20) not null,
    created_at           timestamp,
    updated_at           timestamp
);
create table tbl_flight_schedule_seat_classes
(
    id                 varchar(45) primary key,
    flight_schedule_id varchar(45)    not null,
    seat_class         varchar(50)    not null,
    price              decimal(10, 2) not null,
    currency           varchar(10)    not null,
    created_at         timestamp,
    updated_at         timestamp
);
create table tbl_tickets
(
    id                 varchar(45) primary key,
    flight_schedule_id varchar(45)    not null,
    child_seats        int default 0,
    adult_seats        int default 0,
    total_price        decimal(10, 2) not null,
    currency           varchar(10)    not null,
    contact_email      varchar(100),
    expiration_time    datetime       not null,
    payment_id         varchar(45),
    status             varchar(20)    not null,
    created_at         timestamp,
    updated_at         timestamp
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
    discount_type       varchar(20)    not null,
    discount_value      decimal(10, 2) not null,
    max_discount_amount decimal(10, 2),
    start_date          datetime       not null,
    end_date            datetime       not null,
    usage_limit         int,
    usage_count         int default 0,
    conditions          text,
    status              varchar(20)    not null,
    created_at          timestamp,
    updated_at          timestamp
);
create table tbl_user_discounts
(
    id          varchar(45) primary key,
    user_id     varchar(45) not null,
    discount_id varchar(45) not null,
    assigned_at timestamp,
    used_at     timestamp,
    status      varchar(20) not null default 'ACTIVE',
    created_at  timestamp,
    updated_at  timestamp
);
-
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
    status          varchar(20)    not null,
    created_at      timestamp,
    updated_at      timestamp
);
