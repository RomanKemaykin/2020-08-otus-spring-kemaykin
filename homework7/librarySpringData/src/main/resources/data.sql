insert into authors (name)
values ('author one'), ('author two'), ('author three');

insert into genres (name)
values ('genre one'), ('genre two'), ('genre three');

insert into books (title, author_id, genre_id)
values ('book one', 1, 1), ('book two', 2, 2), ('book three', 3, 3);

insert into book_comments (comment, book_id)
values ('book one comment1', 1), ('book one comment2', 1), ('book three comment1', 3);
