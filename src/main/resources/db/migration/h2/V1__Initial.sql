    create table category (
        id bigint generated by default as identity,
        count_subscriber bigint not null,
        description varchar(255) not null,
        image varchar(255) not null,
        name varchar(255) not null,
        post_id bigint, primary key (id)
    );

    create table comments (
        id bigint generated by default as identity,
        create_date timestamp, description varchar(255),
        post_id bigint, primary key (id)
    );

    create table dislikes (
        id bigint generated by default as identity,
        post_id bigint, user_id bigint, primary key (id)
    );

    create table likes (
        id bigint generated by default as identity,
        post_id bigint, user_id bigint, primary key (id)
    );

    create table posts (
        id bigint generated by default as identity,
        contents varchar(255) not null,
        create_date timestamp not null,
        number_of_reads bigint not null,
        post_type integer not null,
        title varchar(255) not null,
        user_id bigint, primary key (id)
    );

    create table roles (
        id bigint generated by default as identity,
        type_of_role varchar(255),
        user_id bigint, primary key (id)
    );

    create table tags (
        id bigint generated by default as identity,
        name varchar(255) not null,
        post_id bigint, primary key (id)
    );

    create table users (
        id bigint generated by default as identity,
        username varchar(25), primary key (id),
        password varchar(255) not null,
        first_name varchar(25),
        last_name varchar(25),
        email varchar(255),
        phone varchar(255),
        status varchar(255)
    );

    alter table category
        add constraint category_post_id_fk
            foreign key (post_id) references posts;

    alter table comments
        add constraint comments_post_id_fk
            foreign key (post_id) references posts;

    alter table dislikes
        add constraint dislikes_post_id_fk
            foreign key (post_id) references posts;

    alter table dislikes
        add constraint dislikes_user_id_fk
            foreign key (user_id) references users;

    alter table likes
        add constraint likes_post_id_fk
            foreign key (post_id) references posts;

    alter table likes
        add constraint likes_user_id_fk
            foreign key (user_id) references users;

    alter table posts
        add constraint posts_user_id_fk
            foreign key (user_id) references users;

    alter table roles
        add constraint roles_user_id_fk
            foreign key (user_id) references users;

    alter table tags
        add constraint tags_post_id_fk
            foreign key (post_id) references posts;