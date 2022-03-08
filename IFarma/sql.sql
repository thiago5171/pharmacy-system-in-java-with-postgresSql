CREATE DATABASE IFARMA;

\ c IFARMA;

CREATE TABLE cliente (
    -- atributos
    codigo serial primary key,
    cpf varchar(11) unique,
    nome varchar(60),
    data_nascimento date,
    endereco text
);

CREATE TABLE medicamento (
    -- atributos
    codigo serial primary key,
    nome varchar(60),
    valor decimal,
    remedio_controlado boolean
);

CREATE TABLE fornecedor (
    -- atributos
    codigo serial primary key,
    nome varchar(40),
    endereco text,
    telefone varchar(15)
);

CREATE TABLE funcionario (
    -- atributos
    codigo serial primary key,
    cpf varchar(11) unique,
    nome varchar(60),
    data_nascimento date,
    endereco text
);

CREATE TABLE atendente (
    -- atributos
    codigo serial primary key,
    -- chaves estrangeiras
    codigo_funcionario int,
    -- restricoes
    constraint fk_atendente_funcionario foreign key (codigo_funcionario) references funcionario(codigo)
);

CREATE TABLE gerente (
    -- atributos
    codigo serial primary key,
    -- chaves estrangeiras
    codigo_funcionario int,
    -- restricoes
    constraint fk_gerente_funcionario foreign key (codigo_funcionario) references funcionario(codigo)
);

CREATE TABLE compra (
    -- atributos
    codigo serial primary key,
    valor float,
    quantidade int,
    -- chaves estrangeiras
    codigo_fornecedor int,
    codigo_gerente int,
    codigo_medicamento int,
    -- restricoes
    constraint fk_compra_fornecedor foreign key (codigo_fornecedor) references fornecedor(codigo),
    constraint fk_compra_gerente foreign key (codigo_gerente) references gerente(codigo),
    constraint fk_compra_emdicamento foreign key (codigo_medicamento) references medicamento(codigo)
);

CREATE TABLE receita (
    -- atributos
    codigo serial primary key,
    descricao text,
    -- chaves estrangeiras
    codigo_medicamento int,
    -- restricoes
    constraint fk_receita_medicamento foreign key (codigo_medicamento) references medicamento(codigo)
);

CREATE TABLE venda (
    -- atributos
    codigo serial,
    data_venda timestamp,
    double decimal,
    -- chave primaria
    primary key(codigo, data_venda),
    -- chaves estrangeiras
    codigo_atendente int,
    codigo_receita int,
    codigo_medicamento int,
    codigo_cliente int,
    -- restricoes
    constraint fk_venda_atendente foreign key (codigo_atendente) references atendente(codigo),
    constraint fk_venda_receita foreign key (codigo_receita) references receita(codigo),
    constraint fk_venda_medicamento foreign key (codigo_medicamento) references medicamento(codigo),
    constraint fk_venda_cliente foreign key (codigo_cliente) references cliente(codigo)
);