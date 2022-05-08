CREATE TABLE IF NOT EXISTS contato (
      id BIGSERIAL PRIMARY KEY,
      telefone NUMERIC,
      operadora VARCHAR(100),
      nome VARCHAR(100),
      created_at TIMESTAMP NOT NULL DEFAULT NOW(),
      updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);