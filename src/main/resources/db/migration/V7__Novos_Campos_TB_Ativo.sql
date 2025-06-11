ALTER TABLE ativo
ADD COLUMN status CHAR(1) COMMENT 'A: Ativo, I: Inativo, R: Restricao',
ADD COLUMN observacao VARCHAR(250);

UPDATE ativo SET status = 'A';