create table espaco(
    id serial primary key,
    nome varchar(50) not null,
    coord varchar (50) not null
);

create table utilizador(
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled bool not null
);

create table permissao(
    username varchar(50) not null,
    cargo varchar(50) not null,
    constraint fk_permissao_utilizador foreign key(username) references utilizador(username)
);

create unique index ix_permissao_utilizador on permissao(username,cargo);

create table registo(
    id serial primary key,
    date date not null,
    espaco integer not null,
    utilizador varchar(50) not null,
    nivel integer not null
);