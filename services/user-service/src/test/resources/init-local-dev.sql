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


-- username@PASSWORD e.g. : johndoe@PASSWORD
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
        '$2a$10$ViOzv8Sc/y7s5tSSyAkqGumWBVYHJ59vEqqQ0uyK9IcKslnrvVQjy', true, 'ADMIN', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Jane', 'Smith', 'jane.smith@example.com', 'janesmith',
        '$2a$10$CycToZO1LZh.f/RvQikJ0OeDZdbPoYFV45YH8sptPdJvzWgjUykFu', true, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Robert', 'Brown', 'robert.brown@example.com', 'robertbrown',
        '$2a$10$ELZ1qF5GtO5MuATHPqlxPuXwlB6XQ/QMbYH3tLehPKtBdAmiRiV.2', false, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Emily', 'Davis', 'emily.davis@example.com', 'emilydavis',
        '$2a$10$ICeupkzVW7UGDQMZkYHk1OR8XAM1JFt7lFJ0ePBQLNDHyqx6PFcza', true, 'ADMIN', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Michael', 'Miller', 'michael.miller@example.com', 'michaelmiller',
        '$2a$10$B5w2y58vu1DU2rl.pM8T3e5nf.XBaF6TapJr//Sj/BoC6BuDsQG/.', true, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Sarah', 'Johnson', 'sarah.johnson@example.com', 'sarahjohnson',
        '$2a$10$vHBvJFKXF.yqixlqBoNQjun1prub/Wc8jPYYWDTm5zPO/s30pqI4y', false, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'William', 'Taylor', 'william.taylor@example.com', 'williamtaylor',
        '$2a$10$bJXJxiB9CiHwXgeJsDcSpeSxnhpCHlUkn6xa42azApY7IpicRalf6', true, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Sophia', 'Moore', 'sophia.moore@example.com', 'sophiamoore',
        '$2a$10$9420g1qb/BDTDMYcZeX90O5LPsd8VSi2YFGskyzCLV6WYVVJZv0za', true, 'ADMIN', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'James', 'Anderson', 'james.anderson@example.com', 'jamesanderson',
        '$2a$10$.KUp/4XCN6trzJdLYqAN5eiwhHj.wUiKKQDWAZCeGtRN3WZn9b6ZK', true, 'USER', now(), now(), 1),
       (nextval('bank_ease_schema.user_seq'), 'Chloe', 'Martinez', 'chloe.martinez@example.com', 'chloemartinez',
        '$2a$10$CrQloE0ysBlUzE3vcCpW8uWbfGSMB11GE9tbBjgOXg/CyvUMDb1Le', false, 'USER', now(), now(), 1);
