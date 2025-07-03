CREATE OR REPLACE VIEW v_cotacao_agora AS
SELECT
    a.codigo AS codigo,
    a.nome AS nome,
    c.preco AS cotacao_atual,
    c.variacao AS variacao_periodo,
    SUM(d.dividendo) AS dividendos_ano_anterior,
    ROUND((SUM(d.dividendo) / c.preco) * 100, 2) AS dy_percentual,
    c.referencia AS data,
    c.importacao AS data_importacao
FROM cotacao c
INNER JOIN ativo a ON a.id = c.ativo_id
INNER JOIN (
    SELECT ativo_id, MAX(referencia) AS max_referencia
    FROM cotacao
    GROUP BY ativo_id
) recent ON c.ativo_id = recent.ativo_id AND c.referencia = recent.max_referencia
LEFT JOIN dividendo d ON d.ativo_id = a.id AND YEAR(d.data_recebimento) = YEAR(CURDATE()) - 1
WHERE a.seguindo = '5'
GROUP BY a.codigo, a.nome, c.preco, c.variacao, c.referencia, c.importacao
ORDER BY c.variacao DESC;