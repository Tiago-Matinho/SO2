create table espaco(
    id serial primary key,
    nome varchar(50) not null,
    coord varchar (50) not null
);

create table utilizador(
    nome varchar(50) primary key
);

create table registo(
    id serial primary key,
    date date not null,
    espaco integer not null,
    utilizador_nome varchar(50) not null,
    nivel integer not null
);