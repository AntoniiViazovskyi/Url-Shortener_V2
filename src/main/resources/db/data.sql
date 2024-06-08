INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

--password= 12345678
INSERT INTO users (email, password) VALUES ('admin@mail.com', '$2a$10$9YQbvDJsyVngyGCKw7ui7uCTbuOoPQQpTPCzIQHp13GLrvXuI4BF2');
INSERT INTO users (email, password) VALUES ('user@mail.com', '$2a$10$9YQbvDJsyVngyGCKw7ui7uCTbuOoPQQpTPCzIQHp13GLrvXuI4BF2');

INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES (2, 1);