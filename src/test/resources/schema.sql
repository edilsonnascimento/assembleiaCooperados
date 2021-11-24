CREATE TABLE pauta (
      id SERIAL PRIMARY KEY,
      uuid UUID UNIQUE NOT NULL,
      titulo VARCHAR UNIQUE NOT NULL,
      descricao VARCHAR NOT NULL
);

INSERT INTO pauta(uuid, titulo, descricao)
VALUES ('1e73cdb3-0923-4452-a190-3c7eb7857e20', 'Título Existe', 'Descrição Existe');