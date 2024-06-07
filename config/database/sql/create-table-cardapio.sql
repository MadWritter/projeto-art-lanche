CREATE TABLE IF NOT EXISTS cardapio(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    descricao_item VARCHAR(200) NOT NULL,
    valor_por_unidade DECIMAL NOT NULL,
    ativo BOOLEAN NOT NULL
);