create schema if not exists bank_ease_schema;

-- Create Sequence
create sequence if not exists bank_ease_schema.user_seq
start with 1
increment by 1;

-- Create Table
create table if not exists bank_ease_schema.user (
    id bigserial not null,
    first_name varchar(256) not null,
    last_name varchar(256) not null,
    email varchar(256) not null,
    username varchar(128) not null,
    password varchar(256) not null,
    active boolean not null,
    role varchar(64) not null,
    created_date_time timestamp not null,
    last_updated_date_time timestamp not null,
    version int not null,
    constraint user_pk primary key (id),
    constraint user_unique_username unique(username),
    constraint user_unique_email unique(email)
);
