create table password_reset_token (expiry_date timestamp(6), id bigint generated by default as identity, user_id bigint not null unique, token varchar(255), primary key (id));
create table tb_game (game_year integer, score float(53), id bigint generated by default as identity, genre varchar(255), long_description TEXT, platforms varchar(255), short_description TEXT, title varchar(255), primary key (id));
create table tb_image (is_main boolean not null, game_id bigint not null, id bigint generated by default as identity, url varchar(255), primary key (id));
create table tb_pessoa (data_registro timestamp(6) not null, id bigint generated by default as identity, role varchar(20) not null check (role in ('USER','ADMIN')), email varchar(100) not null unique, nome varchar(100) not null, senha varchar(255) not null, primary key (id));
create table tb_sent_email (id bigint generated by default as identity, sent_at timestamp(6) not null, from_email varchar(255) not null, name varchar(255) not null, subject varchar(255) not null, text TEXT not null, to_email varchar(255) not null, primary key (id));
alter table if exists password_reset_token add constraint FKir5cw0c3blmosvkue8br9rodl foreign key (user_id) references tb_pessoa;
alter table if exists tb_image add constraint FKg36jgir0aa6iuboup9nhw7eyl foreign key (game_id) references tb_game;
create table password_reset_token (expiry_date timestamp(6), id bigint generated by default as identity, user_id bigint not null unique, token varchar(255), primary key (id));
create table tb_game (game_year integer, score float(53), id bigint generated by default as identity, genre varchar(255), long_description TEXT, platforms varchar(255), short_description TEXT, title varchar(255), primary key (id));
create table tb_image (is_main boolean not null, game_id bigint not null, id bigint generated by default as identity, url varchar(255), primary key (id));
create table tb_pessoa (data_registro timestamp(6) not null, id bigint generated by default as identity, role varchar(20) not null check (role in ('USER','ADMIN')), email varchar(100) not null unique, nome varchar(100) not null, senha varchar(255) not null, primary key (id));
create table tb_sent_email (id bigint generated by default as identity, sent_at timestamp(6) not null, from_email varchar(255) not null, name varchar(255) not null, subject varchar(255) not null, text TEXT not null, to_email varchar(255) not null, primary key (id));
alter table if exists password_reset_token add constraint FKir5cw0c3blmosvkue8br9rodl foreign key (user_id) references tb_pessoa;
alter table if exists tb_image add constraint FKg36jgir0aa6iuboup9nhw7eyl foreign key (game_id) references tb_game;
