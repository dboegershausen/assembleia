--liquibase formatted sql

--changeset changelog-init:1
CREATE TABLE IF NOT EXISTS cooperado (
    id uuid NOT NULL,
    cpf character varying(50) NOT NULL,
    nome character varying(255) NOT NULL,
    CONSTRAINT pk_cooperado PRIMARY KEY(id),
    UNIQUE(cpf)
);

--changeset changelog-init:2
CREATE TABLE IF NOT EXISTS pauta (
    id uuid NOT NULL,
    conteudo text NOT NULL,
    CONSTRAINT pk_pauta PRIMARY KEY(id)
);

--changeset changelog-init:3
CREATE TABLE IF NOT EXISTS votacao (
    id uuid NOT NULL,
    pauta_id uuid NOT NULL,
    inicio timestamp(6) without time zone NOT NULL,
    fim timestamp(6) without time zone NOT NULL,
    status character varying(50) NOT NULL,
    resultado character varying(50),
    votos_a_favor numeric,
    votos_contrarios numeric,
    CONSTRAINT pk_votacao PRIMARY KEY(id),
    CONSTRAINT fk_pauta FOREIGN KEY(pauta_id) REFERENCES pauta(id)
);

--changeset changelog-init:4
CREATE TABLE IF NOT EXISTS voto (
    id uuid NOT NULL,
    cooperado_id uuid NOT NULL,
    votacao_id uuid NOT NULL,
    opcao character varying(50) NOT NULL,
    CONSTRAINT pk_voto PRIMARY KEY(id),
    CONSTRAINT fk_cooperado FOREIGN KEY(cooperado_id) REFERENCES cooperado(id),
    CONSTRAINT fk_votacao FOREIGN KEY(votacao_id) REFERENCES votacao(id)
);