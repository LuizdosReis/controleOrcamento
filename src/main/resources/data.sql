INSERT INTO usuario (nome,password,username) values ('luiz henrique', '$2a$10$QhAxvOfeaN1/mSDbpcl6UehmgcccUk5n.3wGW.ZOydz1l0Pq2jIdy','luiz.reis');
INSERT INTO role (nome) values ('USER');
INSERT INTO usuario_roles values (1,1);
INSERT INTO categoria (descricao,tipo,usuario_id) VALUES ('Carro','SAIDA',1);
INSERT INTO categoria (descricao,tipo,usuario_id) VALUES ('Salario','ENTRADA',1);
INSERT INTO conta (descricao,saldo,usuario_id) values ('banco do brasil',12.50,1);
INSERT INTO despesa (descricao,data,valor,categoria_id,conta_id,efetivada) values ('troca de oleo','2017-10-14', 32.50,1,1,true);
INSERT INTO receita (descricao,data,valor,categoria_id,conta_id,efetivada) values ('salario','2017-10-05',1500.00,2,1,true);