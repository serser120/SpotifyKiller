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

create table singers_albums
(
    singer_id bigserial not null,
    album_id  bigserial not null
);
alter table singers_albums
    add constraint singers_albums_pk primary key (singer_id, album_id);

create table groups_singers
(
    group_id  bigserial not null,
    singer_id bigserial not null
);
alter table groups_singers
    add constraint groups_singers_pk primary key (group_id, singer_id);

create table groups_songs
(
    group_id bigserial not null,
    song_id  bigserial not null
);
alter table groups_songs
    add constraint groups_songs_pk primary key (group_id, song_id);

create table groups_albums
(
    group_id bigserial not null,
    album_id bigserial not null
);
alter table groups_albums
    add constraint groups_albums_pk primary key (group_id, album_id);

create table albums_songs
(
    album_id bigserial not null,
    song_id  bigserial not null
);
alter table albums_songs
    add constraint albums_songs_pk primary key (album_id, song_id);