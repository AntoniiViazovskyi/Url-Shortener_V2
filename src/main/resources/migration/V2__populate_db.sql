INSERT INTO public.roles (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO public.users (email, password) VALUES ('admin@gmail.com', '$2a$10$PCxpIOfNdkaUD0YojB0/iedpWGGiBHDJSU0tQldyCbSywgbFRa8Y6');

INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 2);