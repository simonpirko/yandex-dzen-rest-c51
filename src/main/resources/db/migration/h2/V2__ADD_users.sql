INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('user', '$2a$10$b2YqFQebOT44AIAStjufWOQ8om84MAO/aJjSKhJNOWmOgeIo2ccYu', 'TestUser1', 'TestUser1', 'test@mail.ru', '+375298881100', 'ACTIVE');

INSERT INTO roles (type_of_role, user_id) VALUES ('ROLE_USER', 1);

INSERT INTO posts (contents, create_date, number_of_reads, post_type, status, title, user_id)
VALUES ('Citroen', '2022-06-03T08:53:20.7460851', 100, 2, 1, 'Citroen', 1);

INSERT INTO posts (contents, create_date, number_of_reads, post_type, status, title, user_id)
VALUES ('Audi','2022-06-03T08:53:20.7460851', 90, 2, 1, 'Audi', 1);

INSERT INTO category (count_subscriber, description, image, name, status, post_id)
VALUES (10, 'category about cars', 'imageTest1', 'cars', 1, 1);

INSERT INTO category (count_subscriber, description, image, name, status, post_id)
VALUES (11, 'category about cars', 'imageTest2', 'cars', 1, 2);

INSERT INTO comments (create_date, description, status, post_id)
VALUES ('2022-06-03T08:53:20.7460851','good car', 1, 1);

INSERT INTO comments (create_date, description, status, post_id)
VALUES ('2022-06-03T08:53:20.7460851','very good car', 1, 2);

INSERT INTO likes (post_id, user_id) VALUES (1, 1);

INSERT INTO  dislikes (post_id, user_id) VALUES (2, 1);

INSERT INTO tags (name, status, post_id) VALUES ('aboutCar', 1, 1);
INSERT INTO tags (name, status, post_id) VALUES ('aboutCar', 1, 2);


INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('admin', '$2a$10$b2YqFQebOT44AIAStjufWOQ8om84MAO/aJjSKhJNOWmOgeIo2ccYu', 'TestUser', 'TestUser', 'test@mail.ru', '+375298881100', 'ACTIVE');

INSERT INTO roles (type_of_role, user_id) VALUES ('ROLE_ADMIN', 2);
