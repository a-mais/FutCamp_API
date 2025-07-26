-- Tabela de Estádios
CREATE TABLE estadio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    capacidade INT NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
) ENGINE=InnoDB;

-- Tabela de Times
CREATE TABLE time (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
) ENGINE=InnoDB;

-- Tabela de Jogadores
CREATE TABLE jogador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    numero_camisa INT NOT NULL,
    posicao VARCHAR(50) NOT NULL,
    time_id INT NOT NULL,
    FOREIGN KEY (time_id) REFERENCES time(id)
) ENGINE=InnoDB;

-- Tabela de Campeonatos
CREATE TABLE campeonato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ano INT NOT NULL
) ENGINE=InnoDB;

-- Tabela de relacionamento entre Times e Campeonatos
CREATE TABLE time_campeonato (
    time_id INT NOT NULL,
    campeonato_id INT NOT NULL,
    PRIMARY KEY (time_id, campeonato_id),
    FOREIGN KEY (time_id) REFERENCES time(id),
    FOREIGN KEY (campeonato_id) REFERENCES campeonato(id)
) ENGINE=InnoDB;

-- Tabela de Resultados
CREATE TABLE resultado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gols_mandante INT NOT NULL,
    gols_visitante INT NOT NULL
) ENGINE=InnoDB;

-- Tabela de Partidas
CREATE TABLE partida (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    time_mandante_id INT NOT NULL,
    time_visitante_id INT NOT NULL,
    campeonato_id INT NOT NULL,
    resultado_id INT,
    FOREIGN KEY (time_mandante_id) REFERENCES time(id),
    FOREIGN KEY (time_visitante_id) REFERENCES time(id),
    FOREIGN KEY (campeonato_id) REFERENCES campeonato(id),
    FOREIGN KEY (resultado_id) REFERENCES resultado(id)
) ENGINE=InnoDB;
