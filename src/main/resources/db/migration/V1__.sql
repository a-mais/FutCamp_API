-- Tabela de times
CREATE TABLE time (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
);

-- Tabela de campeonatos
CREATE TABLE campeonato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ano INT NOT NULL
);

-- Tabela de relacionamento time_campeonato
CREATE TABLE time_campeonato (
    time_id INT NOT NULL,
    campeonato_id INT NOT NULL,
    PRIMARY KEY (time_id, campeonato_id),
    FOREIGN KEY (time_id) REFERENCES time(id),
    FOREIGN KEY (campeonato_id) REFERENCES campeonato(id)
);

-- Tabela de estádios
CREATE TABLE estadio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    capacidade INT NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
);

-- Tabela de jogadores
CREATE TABLE jogador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    posicao VARCHAR(50) NOT NULL,
    numero_camisa INT NOT NULL,
    time_id INT NOT NULL,
    FOREIGN KEY (time_id) REFERENCES time(id)
);

-- Tabela de resultados
CREATE TABLE resultado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gols_mandante INT NOT NULL,
    gols_visitante INT NOT NULL
);

-- Tabela de partidas
CREATE TABLE partida (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    time_mandante_id INT NOT NULL,
    time_visitante_id INT NOT NULL,
    estadio_id INT NOT NULL,
    campeonato_id INT NOT NULL,
    resultado_id INT,
    FOREIGN KEY (time_mandante_id) REFERENCES time(id),
    FOREIGN KEY (time_visitante_id) REFERENCES time(id),
    FOREIGN KEY (estadio_id) REFERENCES estadio(id),
    FOREIGN KEY (campeonato_id) REFERENCES campeonato(id),
    FOREIGN KEY (resultado_id) REFERENCES resultado(id),
    UNIQUE (resultado_id)
);

-- Índices para otimização
CREATE INDEX idx_jogador_time ON jogador(time_id);
CREATE INDEX idx_partida_campeonato ON partida(campeonato_id);
CREATE INDEX idx_partida_time_mandante ON partida(time_mandante_id);
CREATE INDEX idx_partida_time_visitante ON partida(time_visitante_id);
CREATE INDEX idx_time_campeonato_time ON time_campeonato(time_id);
CREATE INDEX idx_time_campeonato_campeonato ON time_campeonato(campeonato_id);
