/*
drop table if exists teams cascade;
create table teams(
    id bigserial primary key,
    name varchar(255),
    team_lead_name varchar(255),
    product_owner_name varchar(255),
    product_center_name varchar(255),
    curator_name varchar(255)
);

drop table if exists team_members cascade;
create table team_members(
    id bigserial primary key,
    name varchar(255),
    team_role_id bigint references team_roles(id),
    team_id bigint references teams(id) on delete cascade,
    int percentage_of_participation,

);

drop table if exists team_roles cascade;
create table team_roles(
    id bigserial primary key,
    name varchar(255)
);
*/