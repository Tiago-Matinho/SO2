CREATE TABLE Utilizador (
    username varchar(255) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE Produto (
    nome_produto varchar(255) NOT NULL,
    PRIMARY KEY (nome_produto)
);

CREATE TABLE Stock (
    nome_loja varchar(255) NOT NULL,
    nome_produto varchar(255) NOT NULL,
    PRIMARY KEY (nome_loja, nome_produto),
    FOREIGN KEY (nome_produto) REFERENCES Produto(nome_produto)
);

CREATE TABLE Requisitos (
    id varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    nome_produto varchar(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (username) REFERENCES Utilizador(username),
    FOREIGN KEY (nome_produto) REFERENCES Produto(nome_produto)
);

