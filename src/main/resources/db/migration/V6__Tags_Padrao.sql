INSERT INTO tag (tipo, nome, descricao) VALUES
('CA', 'Própria', 'Tag referente a investimento próprio'),
('CA', 'Swing Trade', 'Tag para operações de curto prazo'),
('CA', 'Dividendo', 'Tag para ativos que pagam dividendos'),
('CA', 'Suno Dividendo', 'Tag para ativos recomendados pela Suno'),
('CA', 'Valorizar', 'Tag para ativos com expectativa de valorização'),
('CA', 'Trimestral', 'Tag para acompanhamento trimestral'),
('CA', 'Mixa', 'Tag de uso geral ou misto');

ALTER TABLE tag
MODIFY COLUMN tipo VARCHAR(100) NOT NULL COMMENT 'CA: Carteira, PD: Padrao';
