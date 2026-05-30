CREATE TABLE metas (
    id INT AUTO_INCREMENT,
    titulo VARCHAR(120) NOT NULL,
	descricao TEXT,
    categoria VARCHAR(50) NOT NULL COMMENT 'IA: Investimento Anual, IAB: Investimento Anual Bruto, etc',
    valor_meta DECIMAL(15, 2) NOT NULL COMMENT 'O valor principal da meta',
    ano INT NOT NULL,
    metadata JSON COMMENT 'Regras específicas da meta',
    usuario_id INT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	CONSTRAINT `FKmetas_usuario` FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);