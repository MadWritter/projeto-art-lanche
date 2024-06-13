CREATE TABLE IF NOT EXISTS preco_cardapio(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    cardapio_id BIGINT NOT NULL,
    descricao_item VARCHAR(200) NOT NULL,
    valor_por_unidade DECIMAL NOT NULL,
    data_do_valor DATE NOT NULL
);