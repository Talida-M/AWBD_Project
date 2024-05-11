insert into authorities(id, authority)
values (1, 'ROLE_PACIENT');

insert into authorities(id, authority)
values (2, 'ROLE_SPECIALIST');
insert into authorities(id, authority)
values (3,'ROLE_ADMIN');

alter table appointment
modify column appointment_type varchar(255) not null;