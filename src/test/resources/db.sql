create schema if not exists public;

CREATE SEQUENCE if not exists public.car_seq START 1 INCREMENT 50;

create table public.car
(
    id           bigint         not null    constraint car_pkey     primary key,
    brand        varchar(255),
    car_number   varchar(255),
    color        varchar(255),
    release_year integer,
    created      timestamp(6)
);

alter table public.car
    owner to postgres;