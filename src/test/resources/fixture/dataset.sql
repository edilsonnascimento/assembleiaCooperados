MERGE INTO pauta(id, uuid, titulo, descricao, created_at, updated_at) VALUES
    (1, '1e73cdb3-0923-4452-a190-3c7eb7857e20', 'PRIMEIRO-TITULO', 'PRIMEIRA-DESCICAO', '2021-12-08 05:53:00.901884', '2021-12-08 05:53:00.901884'),
    (2, '3731c747-ea27-42e5-a52b-1dfbfa9617db', 'SEGUNDO-TITULO', 'SEGUNDA-DESCICAO', '2021-12-08 05:54:09.854376', '2021-12-08 05:54:09.854376');

MERGE INTO cooperado(id, uuid, nome, cpf, created_at, updated_at) VALUES
    (1, '1e73cdb3-0923-4452-a190-3c7eb7857e20', 'NOME-EXISTENTE-1', '74656849359', '2021-12-08 05:53:00.901884', '2021-12-08 05:53:00.901884'),
    (2, '3731c747-ea27-42e5-a52b-1dfbfa9617db', 'NOME-EXISTENTE-2', '38176004707', '2021-12-08 05:54:00.901884', '2021-12-08 05:54:00.901884'),
    (3, '9fd33037-09ad-4027-9ad7-6d2b83f2a5b4', 'NOME-EXISTENTE-3', '75277742662', '2021-12-09 05:54:00.901884', '2021-12-09 05:54:00.901884');

MERGE INTO status(id, descricao, created_at, updated_at) VALUES
    (1, 'ABERTA', '2021-12-08 05:53:00.901884', '2021-12-08 05:53:00.901884'),
    (2, 'ENCERRADA', '2021-12-08 05:54:00.901884', '2021-12-08 05:54:00.901884');

MERGE INTO sessao( id, uuid, id_pauta, id_urna, inicio_sessao, fim_sessao, total_votos_favor, total_votos_contra, created_at, updated_at, id_status) VALUES
    (1, '91459bb4-07e9-47ab-85c5-4af513db36a3', 1, null, '2021-12-21 08:54:06.058491', '2021-12-21 08:59:06.058491', 0, 0, '2021-12-21 08:54:06.059382', '2021-12-21 08:54:06.059382', 1);

MERGE INTO urna(id, uuid, id_sessao, id_cooperado, voto, created_at, updated_at) VALUES
     (1, '0d28786f-8dbd-41f7-8a77-59ea8bed7d8c', 1, 1, 'FAVORAVEL', '2021-12-08 05:55:00.901884', '2021-12-08 05:55:00.901884'),
     (2, '147e966b-7b4a-4702-b322-ba0d5b707d18', 1, 2, 'CONTRA'   , '2021-12-08 05:56:00.901884', '2021-12-08 05:56:00.901884'),
     (3, '32d6b0a1-d8b6-4c4d-824f-cdb9eb46e015', 1, 3, 'ABSTENCAO', '2021-12-08 05:57:00.901884', '2021-12-08 05:57:00.901884');

