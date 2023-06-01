drop type genre cascade;
drop type role cascade;
drop table users cascade;
drop table singers cascade;
drop table groups cascade;
drop table albums cascade;
drop table songs cascade;
drop table singers_songs cascade;
drop table singers_albums cascade;
drop table groups_singers cascade;
drop table groups_songs cascade;
drop table groups_albums cascade;
drop table albums_songs cascade;
drop table liked_songs cascade;
drop table liked_albums cascade;
drop table liked_singers cascade;
drop table liked_groups cascade;
drop table genre_history cascade;
drop table group_history cascade;
drop table singer_history cascade;
drop table song_history cascade;


create type genre as enum ('Jazz', 'Rock', 'Folk', 'HipHop', 'CountryMusic', 'Blues', 'Classical', 'Reggae','Electronic', 'Funk');
create type role as enum ('admin', 'user');

create table users
(
    id       bigserial primary key,
    login    character varying not null,
    password character varying not null,
    email    character varying,
    role     role              not null,
    token    bigserial unique
);

create table singers
(
    id              bigserial primary key,
    name            character varying not null,
    photo           bytea,
    number_of_plays bigserial
);

create table groups
(
    id              bigserial primary key,
    name            character varying not null,
    photo           bytea,
    number_of_plays bigserial
);

create table albums
(
    id              bigserial primary key,
    name            character varying not null,
    photo           bytea,
    number_of_plays bigserial
);

create table songs
(
    id     bigserial primary key,
    name   character varying not null,
    photo  bytea,
    length int,
    song   bytea,
    genre  genre             not null
);

create table singers_songs
(
    singer_id bigserial not null,
    song_id   bigserial not null
);
alter table singers_songs
    add constraint singers_songs_pk primary key (singer_id, song_id);
alter table singers_songs
    add constraint singers_songs_con check ( not (singer_id is null and singers_songs.song_id is null));

create table singers_albums
(
    singer_id bigserial not null,
    album_id  bigserial not null
);
alter table singers_albums
    add constraint singers_albums_pk primary key (singer_id, album_id);
alter table singers_albums
    add constraint singers_albums_con check ( not (singer_id is null and album_id is null));

create table groups_singers
(
    group_id  bigserial not null,
    singer_id bigserial not null
);
alter table groups_singers
    add constraint groups_singers_pk primary key (group_id, singer_id);
alter table groups_singers
    add constraint groups_singers_con check ( not (group_id is null and singer_id is null));

create table groups_songs
(
    group_id bigserial not null,
    song_id  bigserial not null
);
alter table groups_songs
    add constraint groups_songs_pk primary key (group_id, song_id);
alter table groups_songs
    add constraint groups_songs_con check ( not (group_id is null and song_id is null));

create table groups_albums
(
    group_id bigserial not null,
    album_id bigserial not null
);
alter table groups_albums
    add constraint groups_albums_pk primary key (group_id, album_id);
alter table groups_albums
    add constraint groups_albums_con check ( not (group_id is null and album_id is null));

create table albums_songs
(
    album_id bigserial not null,
    song_id  bigserial not null
);
alter table albums_songs
    add constraint albums_songs_pk primary key (album_id, song_id);
alter table albums_songs
    add constraint albums_songs_con check ( not (album_id is null and song_id is null));

create table liked_songs
(
    user_id bigserial not null,
    song_id bigserial not null
);
alter table liked_songs
    add constraint liked_songs_pk primary key (user_id, song_id);
alter table liked_songs
    add constraint liked_songs_con check ( not (user_id is null and song_id is null));

create table liked_albums
(
    user_id  bigserial not null,
    album_id bigserial not null
);
alter table liked_albums
    add constraint liked_albums_pk primary key (user_id, album_id);
alter table liked_albums
    add constraint liked_albums_con check ( not (user_id is null and album_id is null));

create table liked_singers
(
    user_id   bigserial not null,
    singer_id bigserial not null
);
alter table liked_singers
    add constraint liked_singers_pk primary key (user_id, singer_id);
alter table liked_singers
    add constraint liked_singers_con check ( not (user_id is null and singer_id is null));

create table liked_groups
(
    user_id  bigserial not null,
    group_id bigserial not null
);
alter table liked_groups
    add constraint liked_groups_pk primary key (user_id, group_id);
alter table liked_groups
    add constraint liked_groups_con check ( not (user_id is null and group_id is null));

create table song_history
(
    user_id      bigserial not null,
    song_id      bigserial not null,
    playing_date DATE
);
alter table song_history
    add constraint song_history_pk primary key (user_id, song_id);
alter table song_history
    add constraint song_history_con check ( not (user_id is null and song_id is null));

create table genre_history
(
    user_id      bigserial not null,
    genre        genre     not null,
    playing_date DATE
);
alter table genre_history
    add constraint genre_history_pk primary key (user_id);
alter table genre_history
    add constraint genre_history_con check ( not (user_id is null and genre_history.genre is null));

create table singer_history
(
    user_id      bigserial not null,
    singer_id    bigserial not null,
    playing_date DATE
);
alter table singer_history
    add constraint singer_history_pk primary key (user_id, singer_id);
alter table singer_history
    add constraint singer_history_con check ( not (user_id is null and singer_id is null));

create table group_history
(
    user_id      bigserial not null,
    group_id     bigserial not null,
    playing_date DATE
);
alter table group_history
    add constraint group_history_pk primary key (user_id, group_id);
alter table group_history
    add constraint group_history_con check ( not (user_id is null and group_id is null));

insert into users (login, password, email, role, token)
values ('daniel', '0000', 'qwerty@mail.ru', 'user', 1);

insert into users (login, password, email, role, token)
values ('the cooler daniel', '0000', 'qwerty@mail.ru', 'admin', 2);

insert into songs(name, photo, length, song, genre)
VALUES ('Sonne', '0x010203', 180, '0x010203', 'Rock');
insert into songs(name, photo, length, song, genre)
VALUES ('Deutschland', '0x010203', 180, '0x010203', 'Jazz');
insert into songs(name, photo, length, song, genre)
VALUES ('Moskau', '0x010203', 180, '0x010203', 'Country');
insert into songs(name, photo, length, song, genre)
VALUES ('Совпадения', '0x010203', 180, '0x010203', 'Rock');
insert into songs(name, photo, length, song, genre)
VALUES ('Mastermind', '0x010203', 180, '0x010203', 'Jazz');

insert into albums(name, photo, number_of_plays)
VALUES ('Вижу', '0x010203', 111);
insert into albums(name, photo, number_of_plays)
VALUES ('Mutter', '0x010203', 222);
insert into albums(name, photo, number_of_plays)
VALUES ('Rammstein', '0x010203', 333);
insert into albums(name, photo, number_of_plays)
VALUES ('Reise, Reise', '0x010203', 444);
insert into albums(name, photo, number_of_plays)
VALUES ('Mastermind', '0x010203', 555);

insert into singers(name, photo, number_of_plays)
VALUES ('Till Lindemann', '0x010203', 111);
insert into singers(name, photo, number_of_plays)
VALUES ('PALC', '0x010203', 222);
insert into singers(name, photo, number_of_plays)
VALUES ('Дарья Павлович', '0x010203', 333);

insert into groups(name, photo, number_of_plays)
VALUES ('Tardigrade Inferno', '0x010203', 333);
insert into groups(name, photo, number_of_plays)
VALUES ('Rammstein', '0x010203', 111);

insert into groups_singers(group_id, singer_id)
values (1, 3);
insert into groups_singers(group_id, singer_id)
values (2, 1);

insert into groups_albums(group_id, album_id)
values (1, 5);
insert into groups_albums(group_id, album_id)
values (2, 2);
insert into groups_albums(group_id, album_id)
values (2, 3);
insert into groups_albums(group_id, album_id)
values (2, 4);

insert into groups_songs(group_id, song_id)
values (1, 5);
insert into groups_songs(group_id, song_id)
values (2, 1);
insert into groups_songs(group_id, song_id)
values (2, 2);
insert into groups_songs(group_id, song_id)
values (2, 3);

insert into singers_albums(singer_id, album_id)
values (1, 2);
insert into singers_albums(singer_id, album_id)
values (1, 3);
insert into singers_albums(singer_id, album_id)
values (1, 4);
insert into singers_albums(singer_id, album_id)
values (2, 1);
insert into singers_albums(singer_id, album_id)
values (3, 5);

insert into singers_songs(singer_id, song_id)
values (1, 1);
insert into singers_songs(singer_id, song_id)
values (1, 2);
insert into singers_songs(singer_id, song_id)
values (1, 3);
insert into singers_songs(singer_id, song_id)
values (2, 4);
insert into singers_songs(singer_id, song_id)
values (3, 5);

insert into albums_songs(album_id, song_id)
values (1, 4);
insert into albums_songs(album_id, song_id)
values (2, 1);
insert into albums_songs(album_id, song_id)
values (3, 2);
insert into albums_songs(album_id, song_id)
values (4, 3);
insert into albums_songs(album_id, song_id)
values (5, 5);

insert into liked_songs(user_id, song_id)
values (1, 1);
insert into liked_songs(user_id, song_id)
values (1, 4);
insert into liked_songs(user_id, song_id)
values (1, 5);

insert into liked_albums(user_id, album_id)
values (1, 1);

insert into liked_singers(user_id, singer_id)
values (1, 1);

insert into liked_groups(user_id, group_id)
values (1, 1);