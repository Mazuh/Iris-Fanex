CREATE TABLE hierarquias(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(10) NOT NULL
);

CREATE TABLE usuarios(
	id SERIAL PRIMARY KEY,
	id_hierarquia INTEGER NOT NULL
		REFERENCES hierarquias(id),
	nome VARCHAR(50) NOT NULL DEFAULT 'Aluno',
	senha VARCHAR(20) NOT NULL
);

CREATE TABLE cursos(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	qtd_exercicios INTEGER NOT NULL,
	url_gabarito VARCHAR(50) NOT NULL,
	url_gabarito_alt VARCHAR(50)
);

CREATE TABLE exercicios(
	id SERIAL PRIMARY KEY,
	id_aluno INTEGER NOT NULL
		REFERENCES usuarios(id),
	id_curso INTEGER NOT NULL
		REFERENCES cursos(id),
	id_instrutor INTEGER NOT NULL
		REFERENCES usuarios(id),
	num_aula INTEGER NOT NULL,
	qtd_perguntas INTEGER NOT NULL,
	respostas TEXT NOT NULL,
	dt_envio VARCHAR(8) DEFAULT '00/00/00',
	correcao TEXT,
	is_corrigido SMALLINT NOT NULL DEFAULT 0
);

-- modificações pós-produção.

DROP SEQUENCE usuarios_id_seq CASCADE;

ALTER TABLE cursos
	ALTER COLUMN url_gabarito TYPE varchar(200),
	ALTER COLUMN url_gabarito_alt TYPE varchar(200);