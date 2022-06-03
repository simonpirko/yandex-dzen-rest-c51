create table category (
    id bigint auto_increment,
    count_subscriber bigint,
    description varchar(255),
    image varchar(255),
    name varchar(255),
    status integer,
    post_id bigint,
    primary key (id)) engine=InnoDB;

create table comments (
    id bigint auto_increment,
    create_date datetime(6),
    description varchar(255),
    status integer,
    post_id bigint,
    primary key (id)) engine=InnoDB;

create table dislikes (
    id bigint auto_increment,
    post_id bigint,
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table likes (
    id bigint auto_increment,
    post_id bigint,
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table posts (
    id bigint auto_increment,
    contents varchar(255),
    create_date datetime(6),
    number_of_reads bigint,
    post_type integer,
    status integer,
    title varchar(255) not null,
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table roles (
    id bigint auto_increment,
    type_of_role varchar(255),
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table subscribers (
    id bigint auto_increment,
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table tags (
    id bigint auto_increment,
    name varchar(255) not null,
    status integer,
    post_id bigint,
    primary key (id)) engine=InnoDB;

create table users (
    id bigint auto_increment,
    email varchar(255),
    first_name varchar(25),
    last_name varchar(25),
    password varchar(255) not null,
    phone varchar(255),
    status varchar(255),
    username varchar(25),
    primary key (id)) engine=InnoDB;

alter table category
    add constraint category_posts_fk
    foreign key (post_id) references posts (id);

alter table comments
    add constraint comments_posts_fk
    foreign key (post_id) references posts (id);

alter table dislikes
    add constraint dislikes_posts_fk
    foreign key (post_id) references posts (id);

alter table dislikes
    add constraint dislikes_users_fk
    foreign key (user_id) references users (id);

alter table likes
    add constraint likes_posts_fk
    foreign key (post_id) references posts (id);

alter table likes
    add constraint likes_users_fk
    foreign key (user_id) references users (id);

alter table posts
    add constraint posts_users_fk
    foreign key (user_id) references users (id);

alter table roles
    add constraint roles_users_fk
    foreign key (user_id) references users (id);

alter table subscribers
    add constraint subscribers_users_fk
    foreign key (user_id) references users (id);

alter table tags
    add constraint tags_posts_fk
    foreign key (post_id) references posts (id);
