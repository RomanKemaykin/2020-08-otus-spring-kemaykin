insert into authors (name)
values ('author one'), ('author two'), ('author three'), ('author four');

insert into genres (name)
values ('genre one'), ('genre two'), ('genre three'), ('genre four');

insert into books (title, author_id, genre_id)
values ('book one', 1, 1), ('book two', 2, 2), ('book three', 3, 3), ('book four', 4, 4);

insert into book_comments (comment, book_id)
values ('book one comment1', 1), ('book one comment2', 1), ('book one comment3', 1),
        ('book three comment1', 3), ('book three comment2', 3), ('book three comment3', 3);
