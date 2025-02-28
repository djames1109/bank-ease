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


-- Insert sample data into bank_ease_schema.user
INSERT INTO bank_ease_schema.user (id,
                                   first_name,
                                   last_name,
                                   email,
                                   username,
                                   password,
                                   active,
                                   role,
                                   created_date_time,
                                   last_updated_date_time,
                                   version)
VALUES (nextval('bank_ease_schema.user_seq'), 'John', 'Doe', 'john.doe@example.com', 'johndoe',
        '$2a$16$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36UiRcHMoE8/B/OTM5Lrh2.', true, 'ADMIN', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Jane', 'Smith', 'jane.smith@example.com', 'janesmith',
        '$2a$16$iUVTliz/6lVClBlK5HQv5uZnHFRyBgOYH3IY25/Uenc5SfetnuaVq', true, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Robert', 'Brown', 'robert.brown@example.com', 'robertbrown',
        '$2a$16$H.oVbHRgKcXhqeGJASfn2uOMxu.ZF1xEnJXeivNumHV.ZU8xfcHse', false, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Emily', 'Davis', 'emily.davis@example.com', 'emilydavis',
        '$2a$16$hGyu/xbvAwUmlvrNEZBMfOHN7MU/k.GURQOdpwFt2NiPTgB19sBCW', true, 'ADMIN', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Michael', 'Miller', 'michael.miller@example.com', 'michaelmiller',
        '$2a$16$jbYg9jsfYpJhTQZvR2kp4ukbs5cgZZuZFYFN3qRLQLB2uds3nR5ha', true, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Sarah', 'Johnson', 'sarah.johnson@example.com', 'sarahjohnson',
        '$2a$16$nUP1flj2PQV5OgRQXK73ggeMw4O8ThnZ2FMLKZvVa3EDOy6Od.xmb', false, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'William', 'Taylor', 'william.taylor@example.com', 'williamtaylor',
        '$2a$16$3v3mJLSQiJX3MpMZGfibyENBB2.yuZ.O1OcqNDnkx8k0LZ2HtLZQG', true, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Sophia', 'Moore', 'sophia.moore@example.com', 'sophiamoore',
        '$2a$16$xrG5FgfUbQO1OU/rkOFIHuc9/GYkx3QKcWfT4.eRlsloJYZZosnEG', true, 'ADMIN', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'James', 'Anderson', 'james.anderson@example.com', 'jamesanderson',
        '$2a$16$jEtS9ebS/TRzTJGI6lZ4BM0O8TL82INGUvbYiSMEuChLq3m2Nv8Pi', true, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Chloe', 'Martinez', 'chloe.martinez@example.com', 'chloemartinez',
        '$2a$16$wvjSp5MbU8ZRiHQCyhh0J.wKoTINhIfFD2s45Pk.miPSNL0U9d.r2', false, 'USER', now(), now(), 1);
