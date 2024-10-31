-- invest_master.v_media_dividendos source

CREATE OR REPLACE
 VIEW `v_media_dividendos` AS
SELECT
    `tmp`.`ano` AS `ano`,
    `tmp`.`valor_total` AS `valor_total`,
    round((`tmp`.`valor_total` / `tmp`.`meses`), 2) AS `media`,
    `tmp`.`meses` AS `meses`
FROM
    (
    SELECT
        date_format(`d`.`data_recebimento`, '%Y') AS `ano`,
        sum(`d`.`valor_total`) AS `valor_total`,
        (
        SELECT
            count(DISTINCT date_format(`d2`.`data_recebimento`, '%Y-%m'))
        FROM
            `dividendo` `d2`
        WHERE
            (date_format(`d2`.`data_recebimento`, '%Y') = date_format(`d`.`data_recebimento`, '%Y'))
        GROUP BY
            date_format(`d2`.`data_recebimento`, '%Y')) AS `meses`
    FROM
        (`dividendo` `d`
    LEFT JOIN `ativo` `a` ON
        ((`a`.`id` = `d`.`ativo_id`)))
    WHERE
        (1 = 1)
    GROUP BY
        date_format(`d`.`data_recebimento`, '%Y')) `tmp`
ORDER BY
    `tmp`.`ano` DESC;


-- invest_master.v_media_dividendos_acoes source

CREATE OR REPLACE
 VIEW `v_media_dividendos_acoes` AS
SELECT
    `tmp`.`ano` AS `ano`,
    `tmp`.`valor_total` AS `valor_total`,
    round((`tmp`.`valor_total` / `tmp`.`meses`), 2) AS `media`,
    `tmp`.`meses` AS `meses`
FROM
    (
    SELECT
        date_format(`d`.`data_recebimento`, '%Y') AS `ano`,
        sum(`d`.`valor_total`) AS `valor_total`,
        (
        SELECT
            count(DISTINCT date_format(`d2`.`data_recebimento`, '%Y-%m'))
        FROM
            `dividendo` `d2`
        WHERE
            (date_format(`d2`.`data_recebimento`, '%Y') = date_format(`d`.`data_recebimento`, '%Y'))
        GROUP BY
            date_format(`d2`.`data_recebimento`, '%Y')) AS `meses`
    FROM
        (`dividendo` `d`
    LEFT JOIN `ativo` `a` ON
        ((`a`.`id` = `d`.`ativo_id`)))
    WHERE
        ((1 = 1)
            AND (`a`.`tipo` = 'A'))
    GROUP BY
        date_format(`d`.`data_recebimento`, '%Y')) `tmp`
ORDER BY
    `tmp`.`ano` DESC;


-- invest_master.v_media_dividendos_fundos source

CREATE OR REPLACE
 VIEW `v_media_dividendos_fundos` AS
SELECT
    `tmp`.`ano` AS `ano`,
    `tmp`.`valor_total` AS `valor_total`,
    round((`tmp`.`valor_total` / `tmp`.`meses`), 2) AS `media`,
    `tmp`.`meses` AS `meses`
FROM
    (
    SELECT
        date_format(`d`.`data_recebimento`, '%Y') AS `ano`,
        sum(`d`.`valor_total`) AS `valor_total`,
        (
        SELECT
            count(DISTINCT date_format(`d2`.`data_recebimento`, '%Y-%m'))
        FROM
            `dividendo` `d2`
        WHERE
            (date_format(`d2`.`data_recebimento`, '%Y') = date_format(`d`.`data_recebimento`, '%Y'))
        GROUP BY
            date_format(`d2`.`data_recebimento`, '%Y')) AS `meses`
    FROM
        (`dividendo` `d`
    LEFT JOIN `ativo` `a` ON
        ((`a`.`id` = `d`.`ativo_id`)))
    WHERE
        ((1 = 1)
            AND (`a`.`tipo` = 'F'))
    GROUP BY
        date_format(`d`.`data_recebimento`, '%Y')) `tmp`
ORDER BY
    `tmp`.`ano` DESC;


-- invest_master.v_media_dividendos_outros source

CREATE OR REPLACE
 VIEW `v_media_dividendos_outros` AS
SELECT
    `tmp`.`ano` AS `ano`,
    `tmp`.`valor_total` AS `valor_total`,
    round((`tmp`.`valor_total` / `tmp`.`meses`), 2) AS `media`,
    `tmp`.`meses` AS `meses`
FROM
    (
    SELECT
        date_format(`d`.`data_recebimento`, '%Y') AS `ano`,
        sum(`d`.`valor_total`) AS `valor_total`,
        (
        SELECT
            count(DISTINCT date_format(`d2`.`data_recebimento`, '%Y-%m'))
        FROM
            `dividendo` `d2`
        WHERE
            (date_format(`d2`.`data_recebimento`, '%Y') = date_format(`d`.`data_recebimento`, '%Y'))
        GROUP BY
            date_format(`d2`.`data_recebimento`, '%Y')) AS `meses`
    FROM
        (`dividendo` `d`
    LEFT JOIN `ativo` `a` ON
        ((`a`.`id` = `d`.`ativo_id`)))
    WHERE
        ((1 = 1)
            AND (`a`.`tipo` = 'B'))
    GROUP BY
        date_format(`d`.`data_recebimento`, '%Y')) `tmp`
ORDER BY
    `tmp`.`ano` DESC;


-- invest_master.v_resultado_dividendo source

CREATE OR REPLACE
 VIEW `v_resultado_dividendo` AS
SELECT
    `tmp`.`data` AS `data`,
    `tmp`.`ano` AS `ano`,
    `tmp`.`mes` AS `mes`,
    `tmp`.`fundos` AS `fundos`,
    `tmp`.`acoes` AS `acoes`,
    `tmp`.`outros` AS `outros`,
    ((`tmp`.`fundos` + `tmp`.`acoes`) + `tmp`.`outros`) AS `total`,
    `pa`.`id` AS `acao_id`,
    `pf`.`id` AS `fundos_id`,
    0 AS `bdr_id`
FROM
    ((((
    SELECT
        date_format(`d`.`data_recebimento`, '%Y-%m') AS `data`,
        date_format(`d`.`data_recebimento`, '%Y') AS `ano`,
        date_format(`d`.`data_recebimento`, '%c') AS `mes`,
        (
        SELECT
            COALESCE(sum(`d2`.`valor_total`), 0)
        FROM
            (`dividendo` `d2`
        LEFT JOIN `ativo` `a2` ON
            ((`a2`.`id` = `d2`.`ativo_id`)))
        WHERE
            ((`a2`.`tipo` = 'F')
                AND (date_format(`d2`.`data_recebimento`, '%m%Y') = date_format(`d`.`data_recebimento`, '%m%Y')))) AS `fundos`,
        (
        SELECT
            COALESCE(sum(`d2`.`valor_total`), 0)
        FROM
            (`dividendo` `d2`
        LEFT JOIN `ativo` `a2` ON
            ((`a2`.`id` = `d2`.`ativo_id`)))
        WHERE
            ((`a2`.`tipo` = 'A')
                AND (date_format(`d2`.`data_recebimento`, '%m%Y') = date_format(`d`.`data_recebimento`, '%m%Y')))) AS `acoes`,
        (
        SELECT
            COALESCE(sum(`d2`.`valor_total`), 0)
        FROM
            (`dividendo` `d2`
        LEFT JOIN `ativo` `a2` ON
            ((`a2`.`id` = `d2`.`ativo_id`)))
        WHERE
            ((`a2`.`tipo` NOT IN ('A', 'F'))
                AND (date_format(`d2`.`data_recebimento`, '%m%Y') = date_format(`d`.`data_recebimento`, '%m%Y')))) AS `outros`
    FROM
        (`ativo` `a`
    LEFT JOIN `dividendo` `d` ON
        ((`a`.`id` = `d`.`ativo_id`)))
    WHERE
        (1 = 1)
    GROUP BY
        date_format(`d`.`data_recebimento`, '%m%Y')
    ORDER BY
        `d`.`data_recebimento`)) `tmp`
LEFT JOIN `projecao` `pa` ON
    (((`pa`.`ano` = `tmp`.`ano`)
        AND (`pa`.`mes` = `tmp`.`mes`)
            AND (`pa`.`tipo` = 'A')
                AND (`pa`.`totalizador` = 0))))
LEFT JOIN `projecao` `pf` ON
    (((`pf`.`ano` = `tmp`.`ano`)
        AND (`pf`.`mes` = `tmp`.`mes`)
            AND (`pf`.`tipo` = 'F')
                AND (`pf`.`totalizador` = 0))))
WHERE
    ((1 = 1)
        AND (`tmp`.`data` IS NOT NULL))
ORDER BY
    `tmp`.`data` DESC;