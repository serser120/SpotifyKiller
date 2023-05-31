drop table singers;
drop table groups;
drop table albums;
drop table songs;
drop table singers_songs;
drop table singers_albums;
drop table groups_singers;
drop table groups_songs;
drop table groups_albums;
drop table albums_songs;

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
    song   bytea
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

insert into songs(name, photo, length, song) VALUES ('Sonne', '0x010203', 111, '0x010203');
insert into songs(name, photo, length, song) VALUES ('Deutschland', '0x010203', 222, '0x010203');
insert into songs(name, photo, length, song) VALUES ('Moskau', '0x010203', 333, '0x010203');
insert into songs(name, photo, length, song) VALUES ('Совпадения', '0x010203', 444, '0x010203');
insert into songs(name, photo, length, song) VALUES ('Mastermind', '0x010203', 555, '0x010203');

insert into albums(name, photo, number_of_plays) VALUES ('Вижу', '0x010203', 111);
insert into albums(name, photo, number_of_plays) VALUES ('Mutter', '0x010203', 222);
insert into albums(name, photo, number_of_plays) VALUES ('Rammstein', '0x010203', 333);
insert into albums(name, photo, number_of_plays) VALUES ('Reise, Reise', '0x010203', 444);
insert into albums(name, photo, number_of_plays) VALUES ('Mastermind', '0x010203', 555);

insert into singers(name, photo, number_of_plays) VALUES ('Till Lindemann', '0x010203', 111);
insert into singers(name, photo, number_of_plays) VALUES ('PALC', '0x010203', 222);
insert into singers(name, photo, number_of_plays) VALUES ('Дарья Павлович', '0x010203', 333);

insert into groups(name, photo, number_of_plays) VALUES ('Tardigrade Inferno', '0x010203', 333);
insert into groups(name, photo, number_of_plays) VALUES ('Rammstein', '0x010203', 111);

insert into groups_singers(group_id, singer_id) values (1, 3);
insert into groups_singers(group_id, singer_id) values (2, 1);

insert into groups_albums(group_id, album_id) values (1, 5);
insert into groups_albums(group_id, album_id) values (2, 2);
insert into groups_albums(group_id, album_id) values (2, 3);
insert into groups_albums(group_id, album_id) values (2, 4);

insert into groups_songs(group_id, song_id) values (1, 5);
insert into groups_songs(group_id, song_id) values (2, 1);
insert into groups_songs(group_id, song_id) values (2, 2);
insert into groups_songs(group_id, song_id) values (2, 3);

insert into singers_albums(singer_id, album_id) values (1, 2);
insert into singers_albums(singer_id, album_id) values (1, 3);
insert into singers_albums(singer_id, album_id) values (1, 4);
insert into singers_albums(singer_id, album_id) values (2, 1);
insert into singers_albums(singer_id, album_id) values (3, 5);

insert into singers_songs(singer_id, song_id) values (1, 1);
insert into singers_songs(singer_id, song_id) values (1, 2);
insert into singers_songs(singer_id, song_id) values (1, 3);
insert into singers_songs(singer_id, song_id) values (2, 4);
insert into singers_songs(singer_id, song_id) values (3, 5);

insert into albums_songs(album_id, song_id) values (1, 4);
insert into albums_songs(album_id, song_id) values (2, 1);
insert into albums_songs(album_id, song_id) values (3, 2);
insert into albums_songs(album_id, song_id) values (4, 3);
insert into albums_songs(album_id, song_id) values (5, 5);
