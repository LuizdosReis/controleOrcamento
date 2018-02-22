DROP TABLE IF EXISTS client_roles;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS expense;
DROP TABLE IF EXISTS income;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS client;


CREATE TABLE client
(
  id BIGSERIAL,
  username VARCHAR (50) NOT NULL,
  name VARCHAR (256) NOT NULL,
  password VARCHAR (256) NOT NULL,
  CONSTRAINT client_pk PRIMARY KEY (id),
  CONSTRAINT client_u_username UNIQUE (username)
);

CREATE TABLE account
(
  id BIGSERIAL,
  description VARCHAR (50) NOT NULL,
  balance NUMERIC NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT account_pk PRIMARY KEY (id),
  CONSTRAINT account_fk_client FOREIGN KEY (user_id) REFERENCES client (id)
);

CREATE TABLE category
(
  id BIGSERIAL,
  description VARCHAR (50) NOT NULL,
  user_id BIGINT NOT NULL,
  type VARCHAR(3) NOT NULL,
  CONSTRAINT category_pk PRIMARY KEY (id),
  CONSTRAINT category_fk_client FOREIGN KEY (user_id) REFERENCES client (id)
);

CREATE TABLE expense
(
  id BIGSERIAL,
  description VARCHAR (50) NOT NULL,
  date DATE NOT NULL ,
  effected BOOLEAN NOT NULL,
  value NUMERIC NOT NULL,
  category_id BIGINT NOT NULL,
  account_id BIGINT NOT NULL,
  CONSTRAINT expense_pk PRIMARY KEY (id),
  CONSTRAINT expense_fk_category FOREIGN KEY (category_id) REFERENCES category (id),
  CONSTRAINT expense_fk_account FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE income
(
  id BIGSERIAL,
  description VARCHAR (50) NOT NULL,
  date DATE NOT NULL,
  received BOOLEAN NOT NULL,
  value NUMERIC NOT NULL,
  category_id BIGINT NOT NULL,
  account_id BIGINT NOT NULL,
  CONSTRAINT income_pk PRIMARY KEY (id),
  CONSTRAINT income_fk_category FOREIGN KEY (category_id) REFERENCES category (id),
  CONSTRAINT income_fk_account FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE role
(
  id BIGSERIAL,
  name VARCHAR (50) NOT NULL,
  CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE client_roles
(
  user_id BIGINT NOT NULL,
  roles_id BIGINT NOT NULL,
  CONSTRAINT client_roles_pk PRIMARY KEY (user_id,roles_id),
  CONSTRAINT client_roles_fk_client FOREIGN KEY (user_id) REFERENCES client (id),
  CONSTRAINT client_roles_fk_roles FOREIGN KEY (roles_id) REFERENCES role (id)
);
