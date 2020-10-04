drop table if exists authors;
create table authors(
    id bigserial primary key, 
    name varchar(255)
);

drop table if exists genres;
create table genres(
    id bigserial primary key, 
    name varchar(255)
);

drop table if exists books;
create table books(
    id bigserial primary key, 
    title varchar(255),
    author_id bigint references authors(id),
    genre_id bigint references genres(id)     
);
