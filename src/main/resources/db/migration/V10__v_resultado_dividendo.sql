DROP VIEW v_resultado_dividendo;

CREATE VIEW v_resultado_dividendo AS
    SELECT tmp.*,
           fundos + acoes + outros AS total ,
           pa.id AS acao_id,
           pf.id AS fundos_id,
           pb.id AS bdr_id
      FROM (
                SELECT DATE_FORMAT(data_recebimento ,'%Y-%m') AS "data",
                       DATE_FORMAT(data_recebimento ,'%Y') AS "ano",
                       DATE_FORMAT(data_recebimento ,'%c') AS "mes",
        		(SELECT COALESCE(sum(valor_total),0)
        		   FROM dividendo d2
     		  LEFT JOIN ativo a2 ON a2.id = d2.ativo_id
        		  WHERE a2.tipo = 'F'
        			AND DATE_FORMAT(d2.data_recebimento ,'%m%Y') = DATE_FORMAT(d.data_recebimento ,'%m%Y') ) AS fundos,
        		(SELECT COALESCE(sum(valor_total),0)
        		   FROM dividendo d2
     		  LEFT JOIN ativo a2 ON a2.id = d2.ativo_id
        		  WHERE a2.tipo = 'A'
        			AND DATE_FORMAT(d2.data_recebimento ,'%m%Y') = DATE_FORMAT(d.data_recebimento ,'%m%Y') ) AS acoes,
        		(SELECT COALESCE (sum(valor_total) , 0)
        		   FROM dividendo d2
     		  LEFT JOIN ativo a2 ON a2.id = d2.ativo_id
        		  WHERE a2.tipo NOT IN ('A', 'F')
        			AND DATE_FORMAT(d2.data_recebimento ,'%m%Y') = DATE_FORMAT(d.data_recebimento ,'%m%Y') ) AS outros
          FROM ativo a
     LEFT JOIN dividendo d ON a.id = d.ativo_id
         WHERE 1=1
      GROUP BY DATE_FORMAT(d.data_recebimento ,'%m%Y')
      ORDER BY d.data_recebimento ASC
     ) tmp
        LEFT JOIN projecao pa ON pa.ano = tmp.ano AND pa.mes = tmp.mes AND pa.tipo = 'A' AND pa.totalizador = 0
        LEFT JOIN projecao pf ON pf.ano = tmp.ano AND pf.mes = tmp.mes AND pf.tipo = 'F' AND pf.totalizador = 0
        LEFT JOIN projecao pb ON pb.ano = tmp.ano AND pb.mes = tmp.mes AND pb.tipo = 'B' AND pb.totalizador = 0
     WHERE 1=1
    AND tmp.data IS NOT NULL
    ORDER BY tmp.data DESC ;