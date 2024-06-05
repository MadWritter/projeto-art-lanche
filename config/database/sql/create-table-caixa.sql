CREATE TABLE IF NOT EXISTS caixa(
    id SERIAL NOT NULL PRIMARY KEY,
    valor_inicial DECIMAL NOT NULL,
    valor_final DECIMAL,
    data_abertura DATE NOT NULL,
    data_fechamento DATE,
    opAbertura VARCHAR(200) NOT NULL,
    opFechamento VARCHAR(200),
    lucro DECIMAL,
    aberto BOOLEAN NOT NULL
);