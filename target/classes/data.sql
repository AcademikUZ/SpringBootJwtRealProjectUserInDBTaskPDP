insert into role(id, role_name) values (1, 'ROLE_DIRECTOR'),
                                       (2, 'ROLE_HR_MANAGER'),
                                       (3, 'ROLE_XODIM');

insert into users (id, account_non_expired, account_non_locked, credentials_non_expired, enabled, full_name, email, password, roles_id) values
(1, true, true, true, true, 'Farrux', '111mohinur.normirzayeve7@gmail.com', '$2a$10$erRKZVkTDYSQB7s1jn/Lh.k.8G6n.iCO5PpjRF4davDQQSloBOyvq', 1);

