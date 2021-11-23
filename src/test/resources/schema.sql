CREATE TABLE pauta (
      idPauta SERIAL PRIMARY KEY,
      uuid UUID NOT NULL,
      titulo VARCHAR NOT NULL,
      descricao VARCHAR NOT NULL);