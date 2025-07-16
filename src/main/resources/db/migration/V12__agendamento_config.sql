CREATE TABLE agendamento_config (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    cron VARCHAR(50),
    status char(1) DEFAULT NULL COMMENT 'A: Ativo, I: Inativo, R: Restrição'
);