INSERT INTO USERS(email, login, name, birthday) VALUES ( 'test1@test.ru', 'test1' , 'test1', '2000-01-01');
INSERT INTO USERS(email, login, name, birthday) VALUES ( 'test2@test.ru', 'test2' , 'test2', '1990-01-01');

INSERT INTO MPA(name, description) VALUES ( 'G', 'нет возрастных ограничений' );
INSERT INTO MPA(NAME, DESCRIPTION) VALUES ( 'PG', 'детям рекомендуется смотреть фильм с родителями' );
INSERT INTO MPA(NAME, DESCRIPTION) VALUES ( 'PG-13', 'детям до 13 лет просмотр не желателен' );
INSERT INTO MPA(NAME, DESCRIPTION) VALUES ( 'R', ' лицам до 17 лет просматривать фильм можно только в присутствии взрослого' );
INSERT INTO MPA(NAME, DESCRIPTION) VALUES ( 'NC-17', 'лицам до 18 лет просмотр запрещён' );


INSERT INTO GENRES(NAME) VALUES ( 'Комедия' );
INSERT INTO GENRES(NAME) VALUES ( 'Драма' );
INSERT INTO GENRES(NAME) VALUES ( 'Мультфильм' );
INSERT INTO GENRES(NAME) VALUES ( 'Триллер' );
INSERT INTO GENRES(NAME) VALUES ( 'Документальный' );
INSERT INTO GENRES(NAME) VALUES ( 'Боевик' );

INSERT INTO FILMS(NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
VALUES (
           'Терминатор',
           'История противостояния солдата Кайла Риза и киборга-терминатора, прибывших в 1984-й год из пост-апокалиптического будущего, где миром правят машины-убийцы, а человечество находится на грани вымирания. Цель киборга: убить девушку по имени Сара Коннор, чей ещё нерождённый сын к 2029 году выиграет войну человечества с машинами. Цель Риза: спасти Сару и остановить Терминатора любой ценой.',
           '1984-01-01',
           108,
           4
       );

INSERT INTO FILM_GENRE(FILM_ID, GENRE_ID) VALUES ( 1, 4);
INSERT INTO FILM_GENRE(FILM_ID, GENRE_ID) VALUES ( 1, 6);

INSERT INTO LIKES(FILM_ID, USER_ID) VALUES ( 1, 1 );