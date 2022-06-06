INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('admin', '$2a$10$b2YqFQebOT44AIAStjufWOQ8om84MAO/aJjSKhJNOWmOgeIo2ccYu', 'adminFistName', 'adminLastName', 'admin@gmail.com', '+375296666666', 'ACTIVE');

INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('user', '$2a$10$b2YqFQebOT44AIAStjufWOQ8om84MAO/aJjSKhJNOWmOgeIo2ccYu', 'userFirstName', 'userLastName', 'user@gmail.com', '+3752977777777', 'ACTIVE');

INSERT INTO users (username, password, first_name, last_name, email, phone, status)
VALUES ('user2', '$2a$10$b2YqFQebOT44AIAStjufWOQ8om84MAO/aJjSKhJNOWmOgeIo2ccYu', 'userFirstName2', 'userLastName2', 'user2@gmail.com', '+375298888888', 'ACTIVE');

INSERT INTO roles (type_of_role, user_id) VALUES ('ROLE_ADMIN', 1);
INSERT INTO roles (type_of_role, user_id) VALUES ('ROLE_USER', 2);
INSERT INTO roles (type_of_role, user_id) VALUES ('ROLE_USER', 3);

INSERT INTO posts (contents, create_date, number_of_reads, post_type, status, title, user_id)
VALUES ('Citroen_content_user', '2022-06-03T08:53:20.7460851', 100, 0, 1, 'Citroen_title_user', 2);

INSERT INTO posts (contents, create_date, number_of_reads, post_type, status, title, user_id)
VALUES ('Audi_content_user','2022-06-03T08:53:21.7460851', 90, 0, 1, 'Audi_title_user', 2);

INSERT INTO posts (contents, create_date, number_of_reads, post_type, status, title, user_id)
VALUES ('Citroen_content_user1', '2022-06-03T08:53:22.7460851', 101, 1, 1, 'Citroen_title_user1', 3);

INSERT INTO posts (contents, create_date, number_of_reads, post_type, status, title, user_id)
VALUES ('Audi_content_user1','2022-06-03T08:53:23.7460851', 91, 1, 1, 'Audi_user1', 3);

INSERT INTO category (count_subscriber, description, image, name, status, post_id)
VALUES (10, 'category about cars', 'imageTest1', 'citroen', 1, 1);

INSERT INTO category (count_subscriber, description, image, name, status, post_id)
VALUES (11, 'category about cars', 'imageTest2', 'audi', 1, 2);

INSERT INTO category (count_subscriber, description, image, name, status, post_id)
VALUES (10, 'category about cars', 'imageTest1', 'citroen', 1, 3);

INSERT INTO category (count_subscriber, description, image, name, status, post_id)
VALUES (11, 'category about cars', 'imageTest2', 'audi', 1, 4);

INSERT INTO comments (create_date, description, status, post_id)
VALUES ('2022-06-03T08:54:24.7460851','good car', 1, 1);

INSERT INTO comments (create_date, description, status, post_id)
VALUES ('2022-06-03T08:59:20.7460851','very good car', 1, 2);

INSERT INTO comments (create_date, description, status, post_id)
VALUES ('2022-06-03T08:54:24.7460851','good car', 1, 3);

INSERT INTO comments (create_date, description, status, post_id)
VALUES ('2022-06-03T08:59:20.7460851','very good car', 1, 4);
--
-- INSERT INTO likes (post_id, user_id) VALUES (1, 1);
--
-- INSERT INTO  dislikes (post_id, user_id) VALUES (2, 1);
--
INSERT INTO tags (name, status, post_id) VALUES ('aboutCar', 1, 1);
INSERT INTO tags (name, status, post_id) VALUES ('aboutCar', 1, 2);
INSERT INTO tags (name, status, post_id) VALUES ('aboutCar', 1, 3);
INSERT INTO tags (name, status, post_id) VALUES ('aboutCar', 1, 4);



