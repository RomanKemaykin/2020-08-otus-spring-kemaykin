insert into authors (name)
values ('author one'), ('author two'), ('author three');

insert into genres (name)
values ('genre one'), ('genre two'), ('genre three');

insert into books (title, author_id, genre_id)
values ('book one', 1, 1), ('book two', 2, 2), ('book three', 3, 3);

insert into book_comments (comment, book_id)
values ('book one comment1', 1), ('book one comment2', 1), ('book three comment1', 3);

insert into users (username, password, enabled)
values ('admin', '$2y$12$PlFwF6zgPp/0BTD6/.JnxuUaDJGEPSISAGXQ7mnrBJSyM7szbX/gK', 1),
       ('u1', '$2y$12$PlFwF6zgPp/0BTD6/.JnxuUaDJGEPSISAGXQ7mnrBJSyM7szbX/gK', 1),
       ('u2', '$2y$12$PlFwF6zgPp/0BTD6/.JnxuUaDJGEPSISAGXQ7mnrBJSyM7szbX/gK', 1);

insert into authorities(role)
values ('ROLE_ADMIN'), ('ROLE_USER');

insert into users_authorities_relation (user_id, authority_id)
values (1, 1), (2, 2), (3, 2);

--------------------------------- ACL -----------------------------------------

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 0, 'ROLE_ADMIN'),
(2, 1, 'u1'),
(3, 1, 'u2');

INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.library.models.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
-- owner = ROLE_ADMIN
(1, 1, 1, NULL, 1, 0),
(2, 1, 2, NULL, 1, 0),
(3, 1, 3, NULL, 1, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
-- ROLE_ADMIN
(1, 1, 1, 1, 16, 1, 1, 1),
(2, 2, 1, 1, 16, 1, 1, 1),
(3, 3, 1, 1, 16, 1, 1, 1),
-- u1
(4, 1, 2, 2, 1, 1, 1, 1),
(5, 2, 2, 2, 1, 1, 1, 1);
