create table espaco(
    id serial primary key,
    nome varchar(50) not null,
    coord varchar (50) not null
);

create table users(
    username varchar(50) not null primary key,
    enabled bool not null,
    password varchar(500) not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

create table registo(
    id serial primary key,
    date date not null,
    espaco integer not null,
    utilizador_nome varchar(50) not null,
    nivel integer not null
);