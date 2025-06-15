CREATE TABLE produto_financeiro (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL COMMENT  'Caixinha Nubank, Diferenciado Itaú',
  instituicao VARCHAR(100) NOT NULL COMMENT 'Nubank, Itaú',
  descricao TEXT,
  data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO produto_financeiro (nome, instituicao, descricao, data_criacao)
VALUES
  ('Caixinha Nubank', 'Nubank', 'Produto de investimento com liquidez diária', NOW()),
  ('Diferenciado Itaú', 'Itaú', 'Produto com rendimento superior ao CDI', NOW());

CREATE TABLE renda_fixa (
  id INT AUTO_INCREMENT PRIMARY KEY,
  produto_financeiro_id INT,
  renda DECIMAL(15,2) DEFAULT NULL,
  aplicado DECIMAL(15,2) DEFAULT NULL,
  rentabilidade DECIMAL(5,2) DEFAULT NULL COMMENT 'Em percentual ex: 0.82 para 0,82%',
  comparacao DECIMAL(5,2) DEFAULT NULL COMMENT '% do CDI',
  comparacao_referencia  varchar(10) DEFAULT NULL COMMENT 'Normalmente vai ser CDI',
  data_referencia char(7) COMMENT 'Seguir o padrao YYYY-MM',
  data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT FK_investimento_renda_fixa FOREIGN KEY (produto_financeiro_id) REFERENCES produto_financeiro(id)
);