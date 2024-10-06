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


INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("IRON MAIDEN BLACK BAND T-SHIRT - M",50, 0, 'M', "Disponibile in colore  Nero.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("REEBOK USA NAVY & RED #9 JAMES JERSEY - L",48, 0, 'M', "Maglia Reebok da basket USA navy e rossa '#9 James' completa di scollo a V.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("T-SHIRT STAMPATA ROSSA TOMMY HILFIGER - L",48, 0, 'M', "T-shirt Tommy Hilfiger stampata in rosso.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("T-SHIRT SPORTIVA BIANCA - XL",30, 0, 'M', "La maglietta sportiva è disponibile in bianco.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("T-SHIRT STAMPATA ADIDAS REEDLEY COLLEGE - S",35, 0, 'M', "T-shirt stampata Reedley College di colore nero. Dal marchio Adidas.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("T-SHIRT POLO ADIDAS - M",44, 0, 'M', "T-shirt polo in rosso con un collo davanti a bottone. Dal marchio Adidas.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("NIKE POLO T-SHIRT - M",46, 0, 'M', "T-shirt polo Nike bicolore in blu e nero con un bottone sul davanti del collo.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("NIKE PRINTED T-SHIRT - L",46, 0, 'M', "T-shirt stampata in grigio. Dal marchio Nike.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("MAGLIETTA SPORTIVA DENVER BRONCOS NFL - L",41, 0, 'M', "T-shirt sportiva NFL dei Denver Broncos di colore blu navy.", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("MAGLIA ATOMC STAMPA CANADA A MANICHE LUNGHE - L",55, 0, 'M', "Maglia multicolor Canada stampata Atomc a maniche lunghe completa di scollo a V.", "maglie");

INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("GIACCA IN PELLE SCAMOSCIATA NERA - L",116, 0, 'M', "La giacca in pelle scamosciata è disponibile in nero con fodera completa e tasche multiple.", "giacche");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("GIACCA IN NYLON CON ZIP FRONTALE E APPLICAZIONE PATCHWORK ROSSA - L",66, 0, 'M', "Giacca in nylon patchwork rosso con zip frontale completa di design applicato e tasche multiple.", "giacche");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("GIACCA IN NYLON A CONTRASTO NERO E GIALLO NHL - M",68, 0, 'M', "Giacca NHL nera e gialla in nylon completa di fodera completa e tasche multiple.", "giacche");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("GIACCA IN PELLE SCAMOSCIATA DEI NY YANKEES NERA E BLU CON ZIP SUL DAVANTI - L",55, 0, 'M', "Giacca in pelle scamosciata nera e blu NY Yankees con cerniera frontale completa di fodera completa e tasche multiple.", "giacche");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("GIUBBOTTO BOMBER NERO E ROSSO STARTER NBA BULLS CHICAGO DESIGN - S",161, 0, 'M', "Bomber Starter NBA Bulls Chicago design nero e rosso a contrasto completo di fodera trapuntata.", "giacche");


INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("FELPA CON CAPPUCCIO STAMPATA MICHIGAN - L",36, 0, 'M', "Felpa con cappuccio stampata Michigan in blu navy con coulisse al collo e una tasca.", "felpe");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("FELPA CON CAPPUCCIO STAMPATA PATCHWORK DAYTON UD - L",44, 0, 'M', "La felpa con cappuccio stampata patchwork dayton ud è disponibile in rosso con collo con coulisse e una tasca.", "felpe");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("FELPA TIE-DYE CON CAPPUCCIO - L",26, 0, 'M', "Felpa con cappuccio multicolore tie-dye con una tasca.", "felpe");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("FELPA RICAMATA OHIO STATE BUCKEYES - L",46, 0, 'M', "Felpa ricamata Ohio State Buckeyes. Disponibile in nero con cappuccio e una tasca.", "felpe");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("FELPA STAMPATA FRUIT OF THE LOOM TRINITY UNIVERSITY - XL",46, 0, 'M', "Felpa Fruit Of The Loom Trinity University bordeaux con stampa e maniche raglan.", "felpe");


INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("CAPPELLINO RICAMATO NAVY TEAM MLB - XS",26, 0, 'M', "Cappellino ricamato della squadra MLB. Disponibile in blu navy e rosso.", "accessori");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("CAPPELLINO RICAMATO NERO - XS",16, 0, 'M', "Berretto ricamato in nero.", "accessori");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("CAPPELLINO STAMPEDE - XS",22, 0, 'M', "Il cappellino Stampede è multicolore con ricami.", "accessori");


INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("PANTALONCINI DI JEANS NERI CLASSICI LEVI'S - W30 L9",36, 0, 'M', "Shorts in denim Levi's neri con tasche multiple.", "pantaloni");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("PANTALONI NERI IN DENIM ANNI '90 - W36 L13",34, 0, 'M', "Jorts in denim nero con tasche multiple.", "pantaloni");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("HUGO BOSS PANTALONI CHINO CLASSICI BEIGE - W32 L32",46, 0, 'M', "Chino classici beige completi di pieghe e tasche multiple. Del marchio Hugo Boss.", "pantaloni");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("CHINO CLASSICI NERI - W30 L30",47, 0, 'M', "Classici pantaloni chino neri completi di tasche multiple.", "pantaloni");



INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("T-SHIRT A FASCIA STAMPATA A MANICHE LUNGHE NERA E ROSA - XL",33, 0, 'F', "T-shirt con fascia a maniche lunghe Lil Uzi nera e rosa. ", "maglie");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("T-SHIRT STAMPATA GREAT BARRIER REEF NERA - XL",46, 0, 'F', "T-shirt nera con stampa Great Barrier Reef.", "maglie"); 
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("T-SHIRT STAMPATA CON DESIGN DA BASEBALL PUMA BLU NAVY E ROSSO - XL",46, 0, 'F', "T-shirt da baseball blu navy e rossa con stampa Puma completa di maniche raglan.", "maglie");

INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("THE NORTH FACE PLUM FLEECE LINED NYLON JACKET - L",146, 0, 'F', "Giacca The North Face con zip sul davanti color prugna completa di fodera in pile.", "giacche");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("GIACCA IN ECOPELLE NERA E ROSSA A CONTRASTO - 16",111, 0, 'F', "La giacca classica è disponibile in nero e rosso completa di fodera in finto shearling e tasche multiple.", "giacche");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("GIACCA IN PELLE MARRONE - L",100, 0, 'F', "Giacca in pelle monopetto marrone con fodera completa e due tasche.", "giacche");


INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("FELPA CON CAPPUCCIO STAMPATA CHAMPION VIRGINIA LACROSSE - L",38, 0, 'F', "La felpa con cappuccio stampata Champion Virginia Lacrosse è disponibile in blu navy con collo con coulisse e una tasca.", "felpe");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("FELPA CON CAPPUCCIO NIKE BLU NAVY - L",46, 0, 'F', "La felpa tinta unita è disponibile in blu navy con una tasca.", "felpe");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("FELPA RICAMATA GEOGRAFIA - L",46, 0, 'F', "Felpa con geografia ricamata. Disponibile in nero con cappuccio e una tasca.", "felpe");


INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("PANTALONI FANTASIA MULTICOLORE RAFAEL - W30 L24",46, 0, 'F', "Pantalone fantasia multicolor a vita alta.", "pantaloni");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("PANTALONI CON STAMPA FLOREALE - W32 L32",46, 0, 'F', "Pantaloni blu navy con stampa floreale e vita elasticizzata.", "pantaloni");
INSERT INTO product (nome,prezzo,quantita,sesso,descrizione,categoria) VALUES ("PANTALONI STAMPATI A RIGHE - W26 L29",46, 0, 'F', "Pantaloni stampati a righe multicolor a vita alta.", "pantaloni");



INSERT INTO immagini (codprodotto) VALUES (1);
INSERT INTO immagini (codprodotto) VALUES (1);
INSERT INTO immagini (codprodotto) VALUES (1);
INSERT INTO immagini (codprodotto) VALUES (1);
INSERT INTO immagini (codprodotto) VALUES (2);
INSERT INTO immagini (codprodotto) VALUES (2);
INSERT INTO immagini (codprodotto) VALUES (2);
INSERT INTO immagini (codprodotto) VALUES (2);
INSERT INTO immagini (codprodotto) VALUES (3);
INSERT INTO immagini (codprodotto) VALUES (3);
INSERT INTO immagini (codprodotto) VALUES (3);
INSERT INTO immagini (codprodotto) VALUES (3);
INSERT INTO immagini (codprodotto) VALUES (4);
INSERT INTO immagini (codprodotto) VALUES (4);
INSERT INTO immagini (codprodotto) VALUES (4);
INSERT INTO immagini (codprodotto) VALUES (4);
INSERT INTO immagini (codprodotto) VALUES (5);
INSERT INTO immagini (codprodotto) VALUES (5);
INSERT INTO immagini (codprodotto) VALUES (5);
INSERT INTO immagini (codprodotto) VALUES (5);
INSERT INTO immagini (codprodotto) VALUES (6);
INSERT INTO immagini (codprodotto) VALUES (6);
INSERT INTO immagini (codprodotto) VALUES (6);
INSERT INTO immagini (codprodotto) VALUES (6);
INSERT INTO immagini (codprodotto) VALUES (7);
INSERT INTO immagini (codprodotto) VALUES (7);
INSERT INTO immagini (codprodotto) VALUES (7);
INSERT INTO immagini (codprodotto) VALUES (7);
INSERT INTO immagini (codprodotto) VALUES (8);
INSERT INTO immagini (codprodotto) VALUES (8);
INSERT INTO immagini (codprodotto) VALUES (8);
INSERT INTO immagini (codprodotto) VALUES (8);
INSERT INTO immagini (codprodotto) VALUES (9);
INSERT INTO immagini (codprodotto) VALUES (9);
INSERT INTO immagini (codprodotto) VALUES (9);
INSERT INTO immagini (codprodotto) VALUES (9);
INSERT INTO immagini (codprodotto) VALUES (10);
INSERT INTO immagini (codprodotto) VALUES (10);
INSERT INTO immagini (codprodotto) VALUES (10);
INSERT INTO immagini (codprodotto) VALUES (10);
INSERT INTO immagini (codprodotto) VALUES (11);
INSERT INTO immagini (codprodotto) VALUES (11);
INSERT INTO immagini (codprodotto) VALUES (11);
INSERT INTO immagini (codprodotto) VALUES (11);
INSERT INTO immagini (codprodotto) VALUES (12);
INSERT INTO immagini (codprodotto) VALUES (12);
INSERT INTO immagini (codprodotto) VALUES (12);
INSERT INTO immagini (codprodotto) VALUES (12);
INSERT INTO immagini (codprodotto) VALUES (13);
INSERT INTO immagini (codprodotto) VALUES (13);
INSERT INTO immagini (codprodotto) VALUES (13);
INSERT INTO immagini (codprodotto) VALUES (13);
INSERT INTO immagini (codprodotto) VALUES (14);
INSERT INTO immagini (codprodotto) VALUES (14);
INSERT INTO immagini (codprodotto) VALUES (14);
INSERT INTO immagini (codprodotto) VALUES (14);
INSERT INTO immagini (codprodotto) VALUES (15);
INSERT INTO immagini (codprodotto) VALUES (15);
INSERT INTO immagini (codprodotto) VALUES (15);
INSERT INTO immagini (codprodotto) VALUES (15);
INSERT INTO immagini (codprodotto) VALUES (16);
INSERT INTO immagini (codprodotto) VALUES (16);
INSERT INTO immagini (codprodotto) VALUES (16);
INSERT INTO immagini (codprodotto) VALUES (16);
INSERT INTO immagini (codprodotto) VALUES (17);
INSERT INTO immagini (codprodotto) VALUES (17);
INSERT INTO immagini (codprodotto) VALUES (17);
INSERT INTO immagini (codprodotto) VALUES (17);
INSERT INTO immagini (codprodotto) VALUES (18);
INSERT INTO immagini (codprodotto) VALUES (18);
INSERT INTO immagini (codprodotto) VALUES (18);
INSERT INTO immagini (codprodotto) VALUES (18);
INSERT INTO immagini (codprodotto) VALUES (19);
INSERT INTO immagini (codprodotto) VALUES (19);
INSERT INTO immagini (codprodotto) VALUES (19);
INSERT INTO immagini (codprodotto) VALUES (19);
INSERT INTO immagini (codprodotto) VALUES (20);
INSERT INTO immagini (codprodotto) VALUES (20);
INSERT INTO immagini (codprodotto) VALUES (20);
INSERT INTO immagini (codprodotto) VALUES (20);
INSERT INTO immagini (codprodotto) VALUES (21);
INSERT INTO immagini (codprodotto) VALUES (21);
INSERT INTO immagini (codprodotto) VALUES (21);
INSERT INTO immagini (codprodotto) VALUES (21);
INSERT INTO immagini (codprodotto) VALUES (22);
INSERT INTO immagini (codprodotto) VALUES (22);
INSERT INTO immagini (codprodotto) VALUES (22);
INSERT INTO immagini (codprodotto) VALUES (22);
INSERT INTO immagini (codprodotto) VALUES (23);
INSERT INTO immagini (codprodotto) VALUES (23);
INSERT INTO immagini (codprodotto) VALUES (23);
INSERT INTO immagini (codprodotto) VALUES (23);
INSERT INTO immagini (codprodotto) VALUES (24);
INSERT INTO immagini (codprodotto) VALUES (24);
INSERT INTO immagini (codprodotto) VALUES (24);
INSERT INTO immagini (codprodotto) VALUES (24);
INSERT INTO immagini (codprodotto) VALUES (25);
INSERT INTO immagini (codprodotto) VALUES (25);
INSERT INTO immagini (codprodotto) VALUES (25);
INSERT INTO immagini (codprodotto) VALUES (25);
INSERT INTO immagini (codprodotto) VALUES (26);
INSERT INTO immagini (codprodotto) VALUES (26);
INSERT INTO immagini (codprodotto) VALUES (26);
INSERT INTO immagini (codprodotto) VALUES (26);
INSERT INTO immagini (codprodotto) VALUES (27);
INSERT INTO immagini (codprodotto) VALUES (27);
INSERT INTO immagini (codprodotto) VALUES (27);
INSERT INTO immagini (codprodotto) VALUES (27);
INSERT INTO immagini (codprodotto) VALUES (28);
INSERT INTO immagini (codprodotto) VALUES (28);
INSERT INTO immagini (codprodotto) VALUES (28);
INSERT INTO immagini (codprodotto) VALUES (28);
INSERT INTO immagini (codprodotto) VALUES (29);
INSERT INTO immagini (codprodotto) VALUES (29);
INSERT INTO immagini (codprodotto) VALUES (29);
INSERT INTO immagini (codprodotto) VALUES (29);
INSERT INTO immagini (codprodotto) VALUES (30);
INSERT INTO immagini (codprodotto) VALUES (30);
INSERT INTO immagini (codprodotto) VALUES (30);
INSERT INTO immagini (codprodotto) VALUES (30);
INSERT INTO immagini (codprodotto) VALUES (31);
INSERT INTO immagini (codprodotto) VALUES (31);
INSERT INTO immagini (codprodotto) VALUES (31);
INSERT INTO immagini (codprodotto) VALUES (31);
INSERT INTO immagini (codprodotto) VALUES (32);
INSERT INTO immagini (codprodotto) VALUES (32);
INSERT INTO immagini (codprodotto) VALUES (32);
INSERT INTO immagini (codprodotto) VALUES (32);
INSERT INTO immagini (codprodotto) VALUES (33);
INSERT INTO immagini (codprodotto) VALUES (33);
INSERT INTO immagini (codprodotto) VALUES (33);
INSERT INTO immagini (codprodotto) VALUES (33);
INSERT INTO immagini (codprodotto) VALUES (34);
INSERT INTO immagini (codprodotto) VALUES (34);
INSERT INTO immagini (codprodotto) VALUES (34);
INSERT INTO immagini (codprodotto) VALUES (34);
INSERT INTO immagini (codprodotto) VALUES (35);
INSERT INTO immagini (codprodotto) VALUES (35);
INSERT INTO immagini (codprodotto) VALUES (35);
INSERT INTO immagini (codprodotto) VALUES (35);
INSERT INTO immagini (codprodotto) VALUES (36);
INSERT INTO immagini (codprodotto) VALUES (36);
INSERT INTO immagini (codprodotto) VALUES (36);
INSERT INTO immagini (codprodotto) VALUES (36);
INSERT INTO immagini (codprodotto) VALUES (37);
INSERT INTO immagini (codprodotto) VALUES (37);
INSERT INTO immagini (codprodotto) VALUES (37);
INSERT INTO immagini (codprodotto) VALUES (37);
INSERT INTO immagini (codprodotto) VALUES (38);
INSERT INTO immagini (codprodotto) VALUES (38);
INSERT INTO immagini (codprodotto) VALUES (38);
INSERT INTO immagini (codprodotto) VALUES (38);
INSERT INTO immagini (codprodotto) VALUES (39);
INSERT INTO immagini (codprodotto) VALUES (39);
INSERT INTO immagini (codprodotto) VALUES (39);
INSERT INTO immagini (codprodotto) VALUES (39);


INSERT INTO utente (email, nome, cognome,tipo_account, indirizzo, citta, provincia, cap, pass) VALUES ('mario.rossi@gmail.com', 'Mario', 'Rossi', 0, 'Via Roma 1', 'Milano', 'MI', '20121', 'mario123');
INSERT INTO utente (email, nome, cognome,tipo_account, indirizzo, citta, provincia, cap, pass) VALUES ('giulia.verdi@gmail.com', 'Giulia', 'Verdi',0, 'Via Garibaldi 2', 'Firenze', 'FI', '50122', 'giulia123');
INSERT INTO utente (email, nome, cognome,tipo_account, indirizzo, citta, provincia, cap, pass) VALUES ('palmadaniela218@gmail.com', 'Daniela', 'Palma', 0, 'Contrada Carrara 13', 'Castelpoto', 'BN', '82030', 'dani123');
INSERT INTO utente (email, nome, cognome,tipo_account, indirizzo, citta, provincia, cap, pass) VALUES ('giuseppesorrentino@gmail.com', 'Giuseppe Pio', 'Sorrentino', 1, 'Via Roma 118', 'Sarno', 'SA', '84087', 'giuseppe123');
INSERT INTO utente (email, nome, cognome,tipo_account, indirizzo, citta, provincia, cap, pass) VALUES ('ilianofasolino@gmail.com', 'Iliano', 'Fasolino', 1, 'Via Vecchia Lavorate 47', 'Sarno', 'SA', '84087', 'iliano123');
INSERT INTO utente (email, nome, cognome,tipo_account, indirizzo, citta, provincia, cap, pass) VALUES ('utente@gmail.com', 'Franco', 'Riina', 0, 'Via Cannellone 11', 'Sarno', 'SA', '84087', 'utente123');
INSERT INTO ordine (dataOrdine, totale, stato, email, indirizzo, citta, provincia, cap) VALUES ("2022-11-10",22,'Consegnato', "palmadaniela218@gmail.com", 'Contrada Carrara 13', 'Castelpoto', 'BN', '82030');


INSERT INTO pagamento (id_pagamento, tipo, titolare, numero_carta, scadenza, CVV, n_Ordine) VALUES (1, 'Carta di Credito', 'Iliano Fasolino', '5354566943441223', 12-24, 666, 1);
INSERT INTO ordine (dataOrdine, totale, stato, email, indirizzo, citta, provincia, cap) VALUES ("2023-11-10",22,'In Preparazione', "giulia.verdi@gmail.com", 'Via Garibaldi 2', 'Firenze', 'FI', '50122');
INSERT INTO ordine (dataOrdine, totale, stato, email, indirizzo, citta, provincia, cap) VALUES ("2021-11-10",22,'In Preparazione', "giulia.verdi@gmail.com", 'Contrada Carrara 13', 'Castelpoto', 'BN', '82030');
