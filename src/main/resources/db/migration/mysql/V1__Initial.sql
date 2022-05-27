create table category (
    id bigint not null auto_increment,
    count_subscriber bigint not null,
    description varchar(255) not null,
    image varchar(255) not null,
    name varchar(255) not null,
    status integer,
    post_id bigint,
    primary key (id)) engine=InnoDB;

create table comments (
    id bigint not null auto_increment,
    create_date datetime(6),
    description varchar(255),
    status integer,
    post_id bigint,
    primary key (id)) engine=InnoDB;

create table dislikes (
    id bigint not null auto_increment,
    post_id bigint,
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table likes (
    id bigint not null auto_increment,
    post_id bigint,
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table posts (
    id bigint not null auto_increment,
    contents varchar(255) not null,
    create_date datetime(6) not null,
    number_of_reads bigint not null,
    post_type integer not null,
    status integer,
    title varchar(255) not null,
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table roles (
    id bigint not null auto_increment,
    type_of_role varchar(255),
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table tags (
    id bigint not null auto_increment,
    name varchar(255) not null,
    status integer,
    post_id bigint,
    primary key (id)) engine=InnoDB;

create table users (
    id bigint not null auto_increment,
    email varchar(255),
    first_name varchar(25),
    last_name varchar(25),
    password varchar(255) not null,
    phone varchar(255),
    status varchar(255),
    username varchar(25),
    primary key (id)) engine=InnoDB;

alter table category
    add constraint category_post_id_fk foreign key (post_id) references posts (id);

alter table comments
    add constraint comments_post_id_fk foreign key (post_id) references posts (id);

alter table dislikes
    add constraint dislikes_post_id_fk foreign key (post_id) references posts (id);

alter table dislikes
    add constraint dislikes_user_id_fk foreign key (user_id) references users (id);

alter table likes
    add constraint likes_post_id_fk foreign key (post_id) references posts (id);

alter table likes
    add constraint likes_user_id_fk foreign key (user_id) references users (id);

alter table posts
    add constraint posts_user_id_fk foreign key (user_id) references users (id);

alter table roles
    add constraint roles_user_id_fk foreign key (user_id) references users (id);

alter table tags
    add constraint tags_post_id_fk foreign key (post_id) references posts (id);