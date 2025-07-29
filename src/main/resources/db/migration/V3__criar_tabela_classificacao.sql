-- Tabela de classificação
CREATE TABLE tabela (
    id INT AUTO_INCREMENT PRIMARY KEY,
    time_id INT NOT NULL,
    campeonato_id INT NOT NULL,
    pontos INT DEFAULT 0,
    jogos INT DEFAULT 0,
    vitorias INT DEFAULT 0,
    empates INT DEFAULT 0,
    derrotas INT DEFAULT 0,
    gols_pro INT DEFAULT 0,
    gols_contra INT DEFAULT 0,
    saldo_gols INT DEFAULT 0,
    FOREIGN KEY (time_id) REFERENCES time(id),
    FOREIGN KEY (campeonato_id) REFERENCES campeonato(id),
    UNIQUE KEY uk_time_campeonato (time_id, campeonato_id)
);

-- Índices para otimização
CREATE INDEX idx_tabela_time ON tabela(time_id);
CREATE INDEX idx_tabela_campeonato ON tabela(campeonato_id);
