CREATE TABLE IF NOT EXISTS pedidos(
    id SERIAL PRIMARY KEY,
    caixa_id BIGINT NOT NULL,
    itens_cardapio BIGINT[] NULL,
    comanda TEXT NULL,
    valor_comanda DOUBLE PRECISION NULL,
    desconto DOUBLE PRECISION NULL,
    total DOUBLE PRECISION NULL,
    concluido BOOLEAN
);