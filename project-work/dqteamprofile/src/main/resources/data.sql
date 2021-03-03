delete from team;
delete from team_role;
delete from team_member;

insert into team (id, name, team_lead_name, product_owner_name, product_center_name, curator_name, date_start, date_end)
values (1, 'team 1', 'team lead 1', 'product owner 1', 'product center 1', 'curator 1', '20210115', '20501231');

insert into team_role(id, name)
values (1, 'developer'), (2, 'tester'), (3, 'analyst');

insert into team_member(name, team_role_id, team_id, percentage_of_participation, date_start, date_end)
values ('team member 1', 1, 1, 100, '20210113', '20501231'),
       ('team member 2', 2, 1, 100, '20210114', '20501231'),
       ('team member 3', 3, 1, 100, '20210115', '20501231');
