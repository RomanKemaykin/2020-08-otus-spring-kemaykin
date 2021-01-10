drop table if exists wordprocessed;
create table wordprocessed(
    id bigserial primary key,
    content varchar(255),
    type varchar(25)
);
