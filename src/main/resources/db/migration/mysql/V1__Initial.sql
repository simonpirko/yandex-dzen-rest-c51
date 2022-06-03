create table category (
    id bigint auto_increment,
    count_subscriber bigint not null,
    description varchar(255) not null,
    image varchar(255) not null,
    name varchar(255) not null,
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
    contents varchar(255) not null,
    create_date datetime(6) not null,
    number_of_reads bigint not null,
    post_type integer not null,
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

alter table category add constraint FKnllx3rg2tvqfnfp3b87slegxn foreign key (post_id) references posts (id)
alter table comments add constraint FKh4c7lvsc298whoyd4w9ta25cr foreign key (post_id) references posts (id)
alter table dislikes add constraint FKqt3ojfulp3juwx1gnnvjoqao3 foreign key (post_id) references posts (id)
alter table dislikes add constraint FKdej1eqv0prsavr1warenj7ceb foreign key (user_id) references users (id)
alter table likes add constraint FKry8tnr4x2vwemv2bb0h5hyl0x foreign key (post_id) references posts (id)
alter table likes add constraint FKnvx9seeqqyy71bij291pwiwrg foreign key (user_id) references users (id)
alter table posts add constraint FK5lidm6cqbc7u4xhqpxm898qme foreign key (user_id) references users (id)
alter table roles add constraint FK97mxvrajhkq19dmvboprimeg1 foreign key (user_id) references users (id)
alter table subscribers add constraint FKll9lhik8xj3ep6ahtdt7me7pu foreign key (user_id) references users (id)
alter table tags add constraint FKmvt35fe5neg814lnu9ie18rvt foreign key (post_id) references posts (id)
