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

drop table if exists book_comments;
create table book_comments(
    id bigserial primary key,
    comment varchar(255),
    book_id bigint references books(id) on delete cascade
);
