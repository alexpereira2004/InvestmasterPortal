-- invest_master.ativo definition

CREATE TABLE `ativo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `nome_completo` varchar(255) DEFAULT NULL,
  `cnpj` varchar(30) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL COMMENT 'A: Acao\r\nAO: (3) Ações ordinárias\r\nAP: (4) Ações preferenciais\r\nAPA: (5) preferenciais classe A\r\nAPB: (6) Preferenciais classe B\r\nU: (11) Unit\r\nF: FII \r\nB: BDR\r\nFonte: https://guru.com.vc/bolsa-de-valores/codigos-diferenca-e-tipos-de-acoes/#:~:text=A%20da%20Usiminas.-,A%C3%A7%C3%B5es%20que%20terminam%20com%206,preferencial%20classe%20B%20da%20Eletrobras.',
  `caminho` varchar(255) DEFAULT NULL,
  `seguindo` char(1) DEFAULT 'N' COMMENT 'S: Sim N: Nao',
  `data_atualizacao` datetime DEFAULT NULL,
  `data_criacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


-- invest_master.basic_entity definition

CREATE TABLE `basic_entity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_atualizacao` date DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  PRIMARY KEY (`id`)
);


-- invest_master.indicador definition

CREATE TABLE `indicador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `descricao` text,
  `nome` varchar(255) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `tipo_valor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


-- invest_master.indice definition

CREATE TABLE `indice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `caminho` varchar(255) DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


-- invest_master.media_dividendos_acoes definition

CREATE TABLE `media_dividendos_acoes` (
  `ano` int(11) NOT NULL,
  `media` double DEFAULT NULL,
  `meses` int(11) DEFAULT NULL,
  `valor_total` double DEFAULT NULL,
  PRIMARY KEY (`ano`)
);


-- invest_master.projecao definition

CREATE TABLE `projecao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ano` int(4) NOT NULL,
  `mes` int(2) DEFAULT NULL,
  `tipo` varchar(5) NOT NULL,
  `valor` double DEFAULT NULL,
  `valor_alcancado` double DEFAULT NULL,
  `efetivado` tinyint(1) DEFAULT '0',
  `observacao` varchar(255) DEFAULT NULL,
  `data_atualizacao` datetime DEFAULT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `totalizador` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
);


-- invest_master.v_dividendo_resultado definition

CREATE TABLE `v_dividendo_resultado` (
  `ano` varchar(255) NOT NULL,
  `acoes` double DEFAULT NULL,
  `fundos` double DEFAULT NULL,
  `outros` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`ano`)
);


-- invest_master.vendas definition

CREATE TABLE `vendas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aquisicao` datetime DEFAULT NULL,
  `diferenca` double DEFAULT NULL,
  `indicacao` varchar(255) DEFAULT NULL,
  `preco_pago` double DEFAULT NULL,
  `preco_venda` double DEFAULT NULL,
  `quantidade` int(11) NOT NULL,
  `rendimento` double DEFAULT NULL,
  `total_final` double DEFAULT NULL,
  `total_investido` double DEFAULT NULL,
  `venda` datetime DEFAULT NULL,
  `ativo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


-- invest_master.ativo_indice definition

CREATE TABLE `ativo_indice` (
  `ativo_id` int(11) NOT NULL,
  `indice_id` int(11) NOT NULL,
  PRIMARY KEY (`ativo_id`,`indice_id`),
  KEY `FKgl7m90ewf32u0qko6g42n05i7` (`indice_id`),
  CONSTRAINT `FKgl7m90ewf32u0qko6g42n05i7` FOREIGN KEY (`indice_id`) REFERENCES `indice` (`id`),
  CONSTRAINT `FKhb1ipnvatqbyn0p5j751wo4np` FOREIGN KEY (`ativo_id`) REFERENCES `ativo` (`id`)
);


-- invest_master.cotacao definition

CREATE TABLE `cotacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `importacao` datetime DEFAULT NULL,
  `preco` double DEFAULT NULL,
  `referencia` datetime DEFAULT NULL,
  `volume` varchar(255) DEFAULT NULL,
  `ativo_id` int(11) DEFAULT NULL,
  `abertura` double DEFAULT NULL,
  `variacao` double DEFAULT NULL,
  `maxima` double DEFAULT NULL,
  `minima` double DEFAULT NULL,
  `origem` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa9ux095y0uua37b70c1lvxgt0` (`ativo_id`),
  KEY `cotacao_referencia_IDX` (`referencia`) USING BTREE,
  CONSTRAINT `FKa9ux095y0uua37b70c1lvxgt0` FOREIGN KEY (`ativo_id`) REFERENCES `ativo` (`id`)
);


-- invest_master.dividendo definition

CREATE TABLE `dividendo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_recebimento` date DEFAULT NULL,
  `dividendo` double DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `valor_total` double DEFAULT NULL,
  `ativo_id` int(11) DEFAULT NULL,
  `data_atualizacao` datetime DEFAULT NULL,
  `data_criacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbww0v6grde4j9k725g26ckptq` (`ativo_id`),
  CONSTRAINT `FKbww0v6grde4j9k725g26ckptq` FOREIGN KEY (`ativo_id`) REFERENCES `ativo` (`id`)
);


-- invest_master.indicador_resultado definition

CREATE TABLE `indicador_resultado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ano` int(11) DEFAULT NULL,
  `semestre` int(11) DEFAULT NULL,
  `valor` double NOT NULL,
  `ativo_id` int(11) DEFAULT NULL,
  `indicador_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2o4d0qfpe8nhv7ywekcsf2uwl` (`ativo_id`),
  KEY `FKg4aep704lldur09b48tldfvsv` (`indicador_id`),
  CONSTRAINT `FK2o4d0qfpe8nhv7ywekcsf2uwl` FOREIGN KEY (`ativo_id`) REFERENCES `ativo` (`id`),
  CONSTRAINT `FKg4aep704lldur09b48tldfvsv` FOREIGN KEY (`indicador_id`) REFERENCES `indicador` (`id`)
);


-- invest_master.movimentacao definition

CREATE TABLE `movimentacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aquisicao` datetime DEFAULT NULL,
  `diferenca` double DEFAULT NULL,
  `indicacao` varchar(255) DEFAULT NULL,
  `preco_pago` double DEFAULT NULL,
  `preco_venda` double DEFAULT NULL,
  `quantidade` int(11) NOT NULL,
  `rendimento` double DEFAULT NULL,
  `total_final` double DEFAULT NULL,
  `total_investido` double DEFAULT NULL,
  `venda` datetime DEFAULT NULL,
  `ativo_id` int(11) DEFAULT NULL,
  `estrategia` varchar(255) DEFAULT NULL,
  `dias` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc8wr4nuh5fb1m6jebguy2wsn4` (`ativo_id`),
  CONSTRAINT `FKc8wr4nuh5fb1m6jebguy2wsn4` FOREIGN KEY (`ativo_id`) REFERENCES `ativo` (`id`)
);


-- invest_master.movimento_compra definition

CREATE TABLE `movimento_compra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_aquisicao` date DEFAULT NULL,
  `estrategia` varchar(255) DEFAULT NULL,
  `indicacao` varchar(255) DEFAULT NULL,
  `preco_pago` double DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `total_investido` double DEFAULT NULL,
  `ativo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfofmcxdxyubux8p92okhtqdal` (`ativo_id`),
  CONSTRAINT `FKfofmcxdxyubux8p92okhtqdal` FOREIGN KEY (`ativo_id`) REFERENCES `ativo` (`id`)
);


-- invest_master.movimento_venda definition

CREATE TABLE `movimento_venda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_aquisicao` date DEFAULT NULL,
  `data_venda` date DEFAULT NULL,
  `preco_pago` double DEFAULT NULL,
  `preco_venda` double DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `rendimento` double DEFAULT NULL,
  `total_final` double DEFAULT NULL,
  `total_investido` double DEFAULT NULL,
  `ativo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcxauawl9eg4ni46ou7unc171g` (`ativo_id`),
  CONSTRAINT `FKcxauawl9eg4ni46ou7unc171g` FOREIGN KEY (`ativo_id`) REFERENCES `ativo` (`id`)
);