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

DELETE FROM Stock *;
DELETE FROM Requisitos *;
DELETE FROM Produto *;
DELETE FROM Utilizador *;

INSERT INTO Utilizador VALUES('João');
INSERT INTO Utilizador VALUES('Tiago');
INSERT INTO Utilizador VALUES('José');


INSERT INTO Produto VALUES('Máscara Cirúrgica');
INSERT INTO Produto VALUES('Luvas');
INSERT INTO Produto VALUES('Batatas');
INSERT INTO Produto VALUES('Bananas');
INSERT INTO Produto VALUES('Água Engarrafada');
INSERT INTO Produto VALUES('Cebolas');
INSERT INTO Produto VALUES('Esparguete');
INSERT INTO Produto VALUES('Papel Higiénico');


INSERT INTO Stock VALUES('Pingo Doce', 'Batatas');
INSERT INTO Stock VALUES('Continente', 'Batatas');
INSERT INTO Stock VALUES('Lidl', 'Batatas');
INSERT INTO Stock VALUES('Pingo Doce', 'Bananas');
INSERT INTO Stock VALUES('Continente', 'Bananas');
INSERT INTO Stock VALUES('Lidl', 'Água Engarrafada');
INSERT INTO Stock VALUES('Continente', 'Cebolas');
INSERT INTO Stock VALUES('Lidl', 'Cebolas');
