create table GENRES
(
    GENRE_ID INTEGER auto_increment,
    NAME     CHARACTER VARYING(20) not null,
    constraint GENRES_PK
        primary key (GENRE_ID)
);

create table MPA
(
    MPA_ID      INTEGER auto_increment,
    NAME        CHARACTER VARYING(10)  not null,
    DESCRIPTION CHARACTER VARYING(100) not null,
    constraint "MPA_pk"
        primary key (MPA_ID)
);

create table FILMS
(
    FILM_ID      INTEGER auto_increment,
    NAME         CHARACTER VARYING(200) not null,
    DESCRIPTION  CHARACTER LARGE OBJECT,
    RELEASE_DATE DATE,
    DURATION     INTEGER,
    MPA_ID       INTEGER                not null,
    constraint "FILMS_pk"
        primary key (FILM_ID),
    constraint FILMS_MPA_MPA_ID_FK
        foreign key (MPA_ID) references MPA
);

create table FILM_GENRE
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint "FILM_GENRE_pk"
        primary key (GENRE_ID, FILM_ID),
    constraint "FILM_GENRE_FILMS_null_fk"
        foreign key (FILM_ID) references FILMS
            on delete cascade,
    constraint "FILM_GENRE_GENRES_null_fk"
        foreign key (GENRE_ID) references GENRES
);

create table USERS
(
    USER_ID  INTEGER auto_increment,
    EMAIL    CHARACTER VARYING(255) not null,
    LOGIN    CHARACTER VARYING(255) not null,
    NAME     CHARACTER VARYING(255) not null,
    BIRTHDAY DATE,
    constraint "USERS_pk"
        primary key (USER_ID)
);

create table FRIENDS
(
    USER_ID      INTEGER not null,
    FRIEND_ID    INTEGER not null,
    IS_CONFIRMED BOOLEAN default FALSE,
    constraint "FRIENDS_pk"
        primary key (USER_ID, FRIEND_ID),
    constraint "FRIENDS_USERS_friend_fk"
        foreign key (FRIEND_ID) references USERS
            on delete cascade,
    constraint "FRIENDS_USERS_user_fk"
        foreign key (USER_ID) references USERS
            on delete cascade
);

create index "FRIENDS_FRIEND_ID_index"
    on FRIENDS (FRIEND_ID);

create index "FRIENDS_user_id_index"
    on FRIENDS (USER_ID);

create table LIKES
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint "LIKES_pk"
        primary key (USER_ID, FILM_ID),
    constraint "LIKES_FILMS_null_fk"
        foreign key (FILM_ID) references FILMS
            on delete cascade,
    constraint "LIKES_USERS_null_fk"
        foreign key (USER_ID) references USERS
            on delete cascade
);

