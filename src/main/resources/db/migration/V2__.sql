ALTER TABLE estadio
    ADD endereco VARCHAR(255) NULL;

ALTER TABLE jogador
    ADD CONSTRAINT uc_b35c07d7aefb8baf0baf843f6 UNIQUE (time_id, numero_camisa);

ALTER TABLE partida
    ADD CONSTRAINT uc_partida_resultado UNIQUE (resultado_id);

ALTER TABLE time
    ADD CONSTRAINT uc_time_nome UNIQUE (nome);

ALTER TABLE campeonato
    MODIFY ano INT NULL;

ALTER TABLE estadio
    MODIFY cidade VARCHAR(255);

ALTER TABLE estadio
    MODIFY cidade VARCHAR(255) NULL;

ALTER TABLE time
    MODIFY cidade VARCHAR(255);

ALTER TABLE time
    MODIFY cidade VARCHAR(255) NULL;

ALTER TABLE estadio
    MODIFY estado VARCHAR(255);

ALTER TABLE estadio
    MODIFY estado VARCHAR(255) NULL;

ALTER TABLE time
    MODIFY estado VARCHAR(255);

ALTER TABLE time
    MODIFY estado VARCHAR(255) NULL;

ALTER TABLE campeonato
    MODIFY nome VARCHAR(255);

ALTER TABLE campeonato
    MODIFY nome VARCHAR(255) NULL;

ALTER TABLE estadio
    MODIFY nome VARCHAR(255);

ALTER TABLE estadio
    MODIFY nome VARCHAR(255) NULL;

ALTER TABLE jogador
    MODIFY nome VARCHAR(255);

ALTER TABLE time
    MODIFY nome VARCHAR(255);

ALTER TABLE time
    MODIFY nome VARCHAR(255) NULL;

ALTER TABLE jogador
    MODIFY posicao VARCHAR(255);