-- Inserindo registros iniciais na tabela de classificação para todos os times no Campeonato Maranhense
INSERT INTO tabela (time_id, campeonato_id, pontos, jogos, vitorias, empates, derrotas, gols_pro, gols_contra, saldo_gols)
VALUES
    -- Sampaio Corrêa (2 jogos: 2 vitórias - Moto e Imperatriz)
    (1, 1, 6, 2, 2, 0, 0, 6, 3, 3),

    -- Moto Club (2 jogos: 1 empate, 1 derrota)
    (2, 1, 1, 2, 0, 1, 1, 3, 4, -1),

    -- Cordino (2 jogos: 1 vitória, 1 empate)
    (3, 1, 4, 2, 1, 1, 0, 5, 2, 3),

    -- Juventude (1 jogo: 1 derrota)
    (4, 1, 0, 1, 0, 0, 1, 0, 3, -3),

    -- Imperatriz (2 jogos: 1 empate, 1 derrota)
    (5, 1, 1, 2, 0, 1, 1, 3, 5, -2),

    -- São José (1 jogo: 1 empate)
    (6, 1, 1, 1, 0, 1, 0, 1, 1, 0);

-- Inserindo registros para a Copa FMF (ainda sem jogos realizados)
INSERT INTO tabela (time_id, campeonato_id, pontos, jogos, vitorias, empates, derrotas, gols_pro, gols_contra, saldo_gols)
VALUES
    (1, 2, 0, 0, 0, 0, 0, 0, 0, 0), -- Sampaio
    (2, 2, 0, 0, 0, 0, 0, 0, 0, 0), -- Moto
    (3, 2, 0, 0, 0, 0, 0, 0, 0, 0), -- Cordino
    (4, 2, 0, 0, 0, 0, 0, 0, 0, 0); -- Juventude

-- Inserindo registros para a Copa do Nordeste (ainda sem jogos realizados)
INSERT INTO tabela (time_id, campeonato_id, pontos, jogos, vitorias, empates, derrotas, gols_pro, gols_contra, saldo_gols)
VALUES
    (1, 3, 0, 0, 0, 0, 0, 0, 0, 0), -- Sampaio
    (2, 3, 0, 0, 0, 0, 0, 0, 0, 0); -- Moto
