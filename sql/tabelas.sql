CREATE TABLE hierarquias(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(10)
);

CREATE TABLE usuarios(
	id SERIAL PRIMARY KEY,
	id_categoria INTEGER
		REFERENCES hierarquias(id),
	nome VARCHAR(50) NOT NULL,
	senha VARCHAR(20) NOT NULL
);

CREATE TABLE cursos(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(50),
	qtd_exercicios INTEGER,
	url_gabarito VARCHAR(50),
	url_gabarito_alt VARCHAR(50)
);

CREATE TABLE exercicios(
	id SERIAL PRIMARY KEY,
	id_aluno INTEGER
		REFERENCES usuarios(id),
	id_curso INTEGER
		REFERENCES cursos(id),
	id_instrutor INTEGER
		REFERENCES usuarios(id),
	num_aula INTEGER,
	qtd_perguntas INTEGER,
	respostas TEXT,
	dt_envio VARCHAR(8),
	correcao TEXT,
	is_corrigido SMALLINT
);