
CREATE TABLE pessoa (
	id int(11) NOT NULL AUTO_INCREMENT,
	nome varchar(255) DEFAULT NULL,
	nascimento date,
	nacionalidade varchar(40) DEFAULT NULL,
	documento varchar(40) DEFAULT NULL,
	genero varchar(1) DEFAULT NULL,
	status  varchar(1) DEFAULT NULL COMMENT 'A: Ativo | I: Inativo',
	data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE endereco (
    id int(11) NOT NULL AUTO_INCREMENT,
    principal varchar(1) DEFAULT NULL,
    rua VARCHAR(100),
    numero VARCHAR(10),
    complemento VARCHAR(50),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(2),
    cep VARCHAR(10),
    tipo_endereco VARCHAR(10) COMMENT 'C: Comercial | R: Residencial',
    observacao VARCHAR(100),
    pessoa_id INT,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);


CREATE TABLE contato (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pessoa_id INT,
    tipo_contato VARCHAR(2) NOT NULL COMMENT 'W: Whats | T: Telefone | E: Email',
    valor VARCHAR(50),
    descricao VARCHAR(100) COMMENT 'Campo opcional para observações, como "Celular pessoal"',
	data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);



CREATE TABLE usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    status  varchar(1) DEFAULT NULL COMMENT 'A: Ativo | I: Inativo',
    pessoa_id INT UNIQUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

