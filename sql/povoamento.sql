
INSERT INTO hierarquias(id, nome) VALUES(1, 'admin');
INSERT INTO hierarquias(id, nome) VALUES(2, 'instrutor');
INSERT INTO hierarquias(id, nome) VALUES(3, 'aluno');


INSERT INTO usuarios(id, id_hierarquia, nome, senha)
	VALUES(90001, 1, 'Ale', 'senhaultrasecreta');

INSERT INTO usuarios(id, id_hierarquia, nome, senha)
	VALUES(90002, 2, 'Luciana', 'pinguelo');
	
INSERT INTO usuarios(id, id_hierarquia, nome, senha)
	VALUES(90003, 2, 'Mayara', 'bolotascheias');
