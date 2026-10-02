insert into roles (description, name) values ('Role de Docente Adjunto', 'ROLE_AUXLIAR_DOCENTE');
insert into roles (description, name) values ('Role de Docente Investigador', 'ROLE_DOCENTE_TITULAR');
insert into roles (description, name) values ('Role de Jefe de Trabajos Practicos', 'ROLE_DOCENTE_PRACTICO');
insert into roles (description, name) values ('Role deL decano de la facultad', 'ROLE_DECANO');
insert into roles (description, name) values ('Role del personal de maestranza', 'ROLE_MAESTRANZA');
insert into roles (description, name) values ('Role de usuario basico', 'ROLE_USER');
insert into roles (description, name) values ('Role de administrador total', 'ROLE_ADMIN');

insert into personas (nombre, apellido, edad, direccion) values ('nestor', 'costantini', 35, 'Juan Godoy');
insert into users (email, password, active, persona_id) values ('costantini.nestor.m@gmail.com', '$2a$10$Ly/C19x2Azber23SBHzx1.5ssfzgZGT7sQNiee4Jd1hmjHC8wlKLa', 1, 1)


insert into personas (nombre, apellido, edad, direccion) values ('Pepe', 'Argento', 50, 'Barrio del bajo flores');
insert into users (email, password, active, persona_id) values ('pepito.pistolero@gmail.com', '$2a$10$Ly/C19x2Azber23SBHzx1.5ssfzgZGT7sQNiee4Jd1hmjHC8wlKLa', 1, 2)


insert into user_roles (role_id, user_id) values (7, 1)
insert into user_roles (role_id, user_id) values (4, 1)

insert into user_roles (role_id, user_id) values (6, 2)
