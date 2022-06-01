INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('user', '$2a$10$b2YqFQebOT44AIAStjufWOQ8om84MAO/aJjSKhJNOWmOgeIo2ccYu', 'Simon', 'Pirko', 'test@mail.ru', '+375298881100', 'ACTIVE');

INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('admin', '$2a$10$b2YqFQebOT44AIAStjufWOQ8om84MAO/aJjSKhJNOWmOgeIo2ccYu', 'Simon', 'Pirko', 'test@mail.ru', '+375298881100', 'ACTIVE');

INSERT INTO roles (type_of_role, user_id) VALUES ('ROLE_USER', 1);

INSERT INTO roles (type_of_role, user_id) VALUES ('ROLE_ADMIN', 2);

