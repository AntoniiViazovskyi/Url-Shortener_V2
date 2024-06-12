-- Populating role table --
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

-- Populating user table --
INSERT INTO users (email, password) VALUES ('admin@gmail.com', '$2a$10$PCxpIOfNdkaUD0YojB0/iedpWGGiBHDJSU0tQldyCbSywgbFRa8Y6');

-- Populating user_role table --
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
