create table espaco(
    nome varchar(50) not null primary key,
    coord varchar (50) not null
);

create table registo(
    id serial primary key,
    date date not null,
    espaco varchar(50) not null,
    utilizador varchar(50) not null,
    nivel integer not null,
    constraint fk_registo_espaco foreign key(espaco) references espaco(nome)
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

