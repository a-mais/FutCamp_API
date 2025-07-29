CREATE TABLE campeonato
(
    id   INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(100)       NOT NULL,
    ano  INT                NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE estadio
(
    id         INT AUTO_INCREMENT NOT NULL,
    nome       VARCHAR(100)       NOT NULL,
    capacidade INT                NOT NULL,
    cidade     VARCHAR(100)       NOT NULL,
    estado     VARCHAR(2)         NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE flyway_schema_history
(
    installed_rank INT                     NOT NULL,
    version        VARCHAR(50)             NULL,
    `description`  VARCHAR(200)            NOT NULL,
    type           VARCHAR(20)             NOT NULL,
    script         VARCHAR(1000)           NOT NULL,
    checksum       INT                     NULL,
    installed_by   VARCHAR(100)            NOT NULL,
    installed_on   timestamp DEFAULT NOW() NOT NULL,
    execution_time INT                     NOT NULL,
    success        TINYINT(1)              NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (installed_rank)
);

CREATE TABLE jogador
(
    id            INT AUTO_INCREMENT NOT NULL,
    nome          VARCHAR(100)       NOT NULL,
    numero_camisa INT                NOT NULL,
    posicao       VARCHAR(50)        NOT NULL,
    time_id       INT                NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE partida
(
    id                INT AUTO_INCREMENT NOT NULL,
    data              date               NOT NULL,
    time_mandante_id  INT                NOT NULL,
    time_visitante_id INT                NOT NULL,
    campeonato_id     INT                NOT NULL,
    resultado_id      INT                NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE resultado
(
    id             INT AUTO_INCREMENT NOT NULL,
    gols_mandante  INT                NOT NULL,
    gols_visitante INT                NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE time
(
    id     INT AUTO_INCREMENT NOT NULL,
    nome   VARCHAR(100)       NOT NULL,
    cidade VARCHAR(100)       NOT NULL,
    estado VARCHAR(2)         NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE time_campeonato
(
    time_id       INT NOT NULL,
    campeonato_id INT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (time_id, campeonato_id)
);

CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);

ALTER TABLE jogador
    ADD CONSTRAINT jogador_ibfk_1 FOREIGN KEY (time_id) REFERENCES time (id) ON DELETE NO ACTION;

CREATE INDEX time_id ON jogador (time_id);

ALTER TABLE partida
    ADD CONSTRAINT partida_ibfk_1 FOREIGN KEY (time_mandante_id) REFERENCES time (id) ON DELETE NO ACTION;

CREATE INDEX time_mandante_id ON partida (time_mandante_id);

ALTER TABLE partida
    ADD CONSTRAINT partida_ibfk_2 FOREIGN KEY (time_visitante_id) REFERENCES time (id) ON DELETE NO ACTION;

CREATE INDEX time_visitante_id ON partida (time_visitante_id);

ALTER TABLE partida
    ADD CONSTRAINT partida_ibfk_3 FOREIGN KEY (campeonato_id) REFERENCES campeonato (id) ON DELETE NO ACTION;

CREATE INDEX campeonato_id ON time_campeonato (campeonato_id);

ALTER TABLE partida
    ADD CONSTRAINT partida_ibfk_4 FOREIGN KEY (resultado_id) REFERENCES resultado (id) ON DELETE NO ACTION;

CREATE INDEX resultado_id ON partida (resultado_id);

ALTER TABLE time_campeonato
    ADD CONSTRAINT time_campeonato_ibfk_1 FOREIGN KEY (time_id) REFERENCES time (id) ON DELETE NO ACTION;

ALTER TABLE time_campeonato
    ADD CONSTRAINT time_campeonato_ibfk_2 FOREIGN KEY (campeonato_id) REFERENCES campeonato (id) ON DELETE NO ACTION;

CREATE INDEX campeonato_id ON time_campeonato (campeonato_id);