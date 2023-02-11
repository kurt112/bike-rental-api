insert into store(longitude,latitude,name,radius,scope_color,scope_edge_color) values ('121.134693','14.676109','Default Name','200','green','black');

#password - erikbikeshop
# no password admin
INSERT INTO user (id,birthdate, cellphone, created_at, email, first_name, gender, is_account_not_expired, is_account_not_locked, is_credential_not_expired, is_enable, last_name, middle_name, user_password, updated_at, role, is_renting)
VALUES
    (null,'2023-01-08 04:05:53.000000', '0912345674545', '2023-01-08 04:06:08.000000',
     'system@email.com', 'system',
     null, false, false, false, false,
     'admin', 'bot',
     '$2a$10$Pi2RbX6El53f5pEaxjcXguiB6t0Gyppx6iQX7OM2de4ZvygQfRVI.',
     '2023-01-08 04:06:50.000000', 'admin', false);

INSERT INTO user (id, birthdate, cellphone, created_at, email, first_name, gender, is_account_not_expired, is_account_not_locked, is_credential_not_expired, is_enable, last_name, middle_name, user_password, updated_at, role,is_renting) VALUES (null,'2023-01-08 04:05:53.000000', '09617134338', '2023-01-08 04:06:08.000000', 'admin@email.com', 'erik', null, true, true, true, true, 'admin', 'bikeshop', '$2a$10$Pi2RbX6El53f5pEaxjcXguiB6t0Gyppx6iQX7OM2de4ZvygQfRVI.', '2023-01-08 04:06:50.000000', 'admin', false);


