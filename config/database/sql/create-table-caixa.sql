CREATE TABLE IF NOT EXISTS caixa(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    valor-inicial DECIMAL NOT NULL,
    valor-final DECIMAL,
    data-abertura DATE NOT NULL,
    data-fechamento DATE,
    lucro DECIMAL,
    aberto BOOLEAN NOT NULL
)