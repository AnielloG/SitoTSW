DROP DATABASE IF EXISTS sito;
CREATE DATABASE sito;
USE sito;

CREATE TABLE product ( 
  id int primary key auto_increment,
  descrizione varchar(255),
  prezzo double,
  quantita tinyint DEFAULT '0' ,
  foto mediumblob,
  sesso varchar(255),
  nome varchar(255),
  categoria ENUM('giacche', 'maglie', 'felpe', 'pantaloni', 'accessori', 'cappelli'),
  iva double NOT NULL DEFAULT '22'
);

CREATE TABLE utente (
    email VARCHAR(50) PRIMARY KEY,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    tipo_account tinyint DEFAULT '0',
    indirizzo VARCHAR(100),
    citta VARCHAR(50),
    provincia VARCHAR(50),
    cap VARCHAR(10),
    pass VARCHAR(50) 
);

CREATE TABLE immagini (
  cod int primary key auto_increment,
  codprodotto int,
  img mediumblob,
  foreign key (codprodotto) references product(id)
);
CREATE TABLE ordine (
  numeroOrdine INT PRIMARY KEY auto_increment,
  dataOrdine DATE,
  totale double,
  stato ENUM('In Preparazione', 'In Consegna', 'Consegnato'),
  email VARCHAR(50),
  indirizzo VARCHAR(100),
  citta VARCHAR(50),
  provincia VARCHAR(50),
  cap VARCHAR(10),
  FOREIGN KEY (email) REFERENCES utente(email)
);

CREATE TABLE composizione(
	IVA double not null default 22,
    quantita int default 1,
    totale double,
    codP int,
    numeroO int,
    primary key(codP, numeroO),
    foreign key (codP) REFERENCES product(id),
    foreign key (numeroO) REFERENCES Ordine(numeroOrdine)
);

CREATE TABLE pagamento(
	id_pagamento int auto_increment,
    tipo ENUM('Carta di Credito', 'PayPal', 'Apple Pay'),
	titolare varchar(80),
	numero_carta varchar(16),
	scadenza varchar(5),
	CVV int,
	n_Ordine INT,
  PRIMARY KEY (id_pagamento),
  FOREIGN KEY (n_Ordine) REFERENCES ordine(numeroOrdine)
);
CREATE TABLE recensioni (
  valutazione INT,
  email VARCHAR(50),
  codp INT,
  PRIMARY KEY (codp,email),
  FOREIGN KEY (email) REFERENCES utente(email),
  FOREIGN KEY (codp) REFERENCES product(id)
);


