CREATE TABLE `movimento_consolidado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_aquisicao` date DEFAULT NULL,
  `estrategia` varchar(255) DEFAULT NULL,
  `preco_medio` double DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `total_investido` double DEFAULT NULL,
  `ativo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ativo_id` (`ativo_id`),
  CONSTRAINT `movimento_consolidado_ibfk_1` FOREIGN KEY (`ativo_id`) REFERENCES `ativo` (`id`)
);

DROP TABLE movimentacao;