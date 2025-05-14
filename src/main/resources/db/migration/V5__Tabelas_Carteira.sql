CREATE TABLE carteira (
  id int(11) NOT NULL AUTO_INCREMENT,
  ativo_id int(11) DEFAULT NULL,
  preco_pago double DEFAULT NULL,
  preco_atual double DEFAULT NULL,
  valorizacao double DEFAULT NULL,
  quantidade int(11) NOT NULL,
  total_investido double DEFAULT NULL,
  total_atualizado double DEFAULT NULL,
  data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_carteira_ativo_id FOREIGN KEY (ativo_id) REFERENCES ativo (id)
);


CREATE TABLE tag (
  id int(11) NOT NULL AUTO_INCREMENT,
  tipo VARCHAR(100) NOT NULL,
  nome VARCHAR(100) UNIQUE NOT NULL,
  descricao VARCHAR(250) NOT NULL,
  data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE tags_carteira (
  id_carteira INT(11) NOT NULL,
  id_tag INT(11) NOT NULL,
  PRIMARY KEY (id_carteira, id_tag),
  CONSTRAINT fk_tags_carteira_carteira FOREIGN KEY (id_carteira) REFERENCES carteira(id),
  CONSTRAINT fk_tags_carteira_tag FOREIGN KEY (id_tag) REFERENCES tag(id)
);