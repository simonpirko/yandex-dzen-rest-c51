INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('user', 'pass', 'Simon', 'Pirko', 'test@mail.ru', '+375298881100', 'ACTIVE');

INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('admin', 'pass', 'Simon', 'Pirko', 'test@mail.ru', '+375298881100', 'ACTIVE');

INSERT INTO roles (type_of_role, user_id) VALUES ('USER', 1);

INSERT INTO roles (type_of_role, user_id) VALUES ('ADMIN', 2);

