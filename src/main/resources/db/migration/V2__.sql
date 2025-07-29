-- Times
INSERT INTO time (nome, cidade, estado)
VALUES
    ('Sampaio Corrêa', 'São Luís', 'MA'),
    ('Moto Club', 'São Luís', 'MA'),
    ('Cordino', 'Barra do Corda', 'MA'),
    ('Juventude', 'São Mateus', 'MA'),
    ('Imperatriz', 'Imperatriz', 'MA'),
    ('São José', 'São José de Ribamar', 'MA');

-- Campeonatos
INSERT INTO campeonato (nome, ano)
VALUES
    ('Campeonato Maranhense', 2024),
    ('Copa FMF', 2024),
    ('Copa do Nordeste', 2024);

-- Estádios
INSERT INTO estadio (nome, endereco, capacidade, cidade, estado)
VALUES
    ('Castelão', 'Av. Gal. Arthur Carvalho, s/n - Outeiro da Cruz', 40000, 'São Luís', 'MA'),
    ('Nhozinho Santos', 'R. Vinte e Um de Abril, s/n - Centro', 10000, 'São Luís', 'MA'),
    ('Leandrão', 'R. Eurico Macedo, S/N - São Francisco', 5000, 'Barra do Corda', 'MA'),
    ('Estádio Frei Epifânio', 'R. Dom Pedro II, s/n', 8000, 'Imperatriz', 'MA'),
    ('Pinheirão', 'Av. Tancredo Neves, SN', 5000, 'Pinheiro', 'MA'),
    ('Dário Santos', 'Av. Kennedy', 3000, 'São José de Ribamar', 'MA');

-- Jogadores
INSERT INTO jogador (nome, posicao, numero_camisa, time_id)
VALUES
    -- Jogadores do Sampaio Corrêa
    ('Luiz Daniel', 'Goleiro', 1, 1),
    ('Matheus Pivô', 'Zagueiro', 3, 1),
    ('Gustavo Carvalho', 'Meia', 10, 1),
    ('Ytalo', 'Atacante', 9, 1),

    -- Jogadores do Moto Club
    ('Railson', 'Goleiro', 1, 2),
    ('André Penalva', 'Zagueiro', 4, 2),
    ('Cláudio Mercado', 'Meia', 8, 2),
    ('Ted Love', 'Atacante', 11, 2),

    -- Jogadores do Cordino
    ('Wallace', 'Goleiro', 1, 3),
    ('Dadinha', 'Zagueiro', 2, 3),
    ('Raí', 'Meia', 10, 3),
    ('Adeilson', 'Atacante', 9, 3),

    -- Jogadores do Juventude
    ('João Paulo', 'Goleiro', 1, 4),
    ('Elivelton', 'Zagueiro', 3, 4),
    ('Thiago Santos', 'Meia', 8, 4),
    ('Pedro', 'Atacante', 9, 4),

    -- Jogadores do Imperatriz
    ('Waldson', 'Goleiro', 1, 5),
    ('Igor João', 'Zagueiro', 4, 5),
    ('Charles', 'Meia', 10, 5),
    ('Matheus Lima', 'Atacante', 11, 5),

    -- Jogadores do São José
    ('Lucas Alves', 'Goleiro', 1, 6),
    ('Rafael Silva', 'Zagueiro', 2, 6),
    ('Diego Soares', 'Meia', 10, 6),
    ('Marcos Paulo', 'Atacante', 9, 6);

-- Relacionamento time_campeonato (todos os times no Campeonato Maranhense)
INSERT INTO time_campeonato (time_id, campeonato_id)
VALUES
    -- Campeonato Maranhense (todos os times)
    (1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1),
    -- Copa FMF (4 times)
    (1, 2), (2, 2), (3, 2), (4, 2),
    -- Copa do Nordeste (2 times)
    (1, 3), (2, 3);

-- Resultados das partidas já realizadas
INSERT INTO resultado (gols_mandante, gols_visitante)
VALUES
    (2, 1),   -- Sampaio 2 x 1 Moto
    (3, 0),   -- Cordino 3 x 0 Juventude
    (1, 1),   -- Imperatriz 1 x 1 São José
    (2, 2),   -- Moto 2 x 2 Cordino
    (4, 2);   -- Sampaio 4 x 2 Imperatriz

-- Partidas
INSERT INTO partida (data, time_mandante_id, time_visitante_id, estadio_id, campeonato_id, resultado_id)
VALUES
    -- Partidas já realizadas do Campeonato Maranhense
    ('2024-05-01', 1, 2, 1, 1, 1),  -- Sampaio x Moto
    ('2024-05-08', 3, 4, 3, 1, 2),  -- Cordino x Juventude
    ('2024-05-15', 5, 6, 4, 1, 3),  -- Imperatriz x São José
    ('2024-05-22', 2, 3, 2, 1, 4),  -- Moto x Cordino
    ('2024-06-01', 1, 5, 1, 1, 5),  -- Sampaio x Imperatriz

    -- Próximas partidas do Campeonato Maranhense
    ('2024-08-01', 4, 6, 1, 1, null),  -- Juventude x São José
    ('2024-08-08', 2, 5, 2, 1, null),  -- Moto x Imperatriz

    -- Copa FMF (quartas de final - futuras)
    ('2024-07-15', 1, 4, 1, 2, null),  -- Sampaio x Juventude
    ('2024-07-22', 2, 3, 2, 2, null),  -- Moto x Cordino

    -- Copa do Nordeste
    ('2024-06-15', 1, 2, 1, 3, null);  -- Sampaio x Moto
