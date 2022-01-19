CREATE TABLE IF NOT EXISTS pauta (
    id SERIAL PRIMARY KEY,
    uuid UUID UNIQUE NOT NULL,
    titulo VARCHAR(100) UNIQUE NOT NULL,
    descricao VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS cooperado (
     id BIGSERIAL PRIMARY KEY,
     uuid UUID UNIQUE NOT NULL,
     nome VARCHAR(100) NOT NULL,
     cpf VARCHAR(11) UNIQUE NOT NULL,
     created_at TIMESTAMP NOT NULL DEFAULT NOW(),
     updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS status (
     id BIGSERIAL PRIMARY KEY,
     descricao VARCHAR(50) NOT NULL,
     created_at TIMESTAMP NOT NULL DEFAULT NOW(),
     updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS sessao (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID UNIQUE NOT NULL,
    id_pauta BIGSERIAL,
    inicio_sessao TIMESTAMP NOT NULL,
    fim_sessao TIMESTAMP NOT NULL,
    total_votos_favor INT,
    total_votos_contra INT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    id_status BIGSERIAL,
    FOREIGN KEY (id_pauta) REFERENCES pauta(id),
    FOREIGN KEY (id_status) REFERENCES status(id)
);

CREATE TABLE IF NOT EXISTS cedula (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    id_sessao BIGSERIAL,
    id_cooperado BIGSERIAL NOT NULL,
    voto VARCHAR NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (id_sessao) REFERENCES sessao(id),
    FOREIGN KEY (id_cooperado) REFERENCES cooperado(id)
);
