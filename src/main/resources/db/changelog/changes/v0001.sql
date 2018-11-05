CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

CREATE TABLE user_data (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  username VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  role user_role NOT NULL
);

INSERT INTO user_data (id, username, email, password, role) VALUES (0, 'admin', 'admin', 'admin', 'ADMIN');

CREATE TABLE person_data (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  user_id BIGINT NOT NULL REFERENCES user_data(id),
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  birthday DATE,
  avatar VARCHAR(150)
);

INSERT INTO person_data (user_id, first_name, last_name) VALUES (0, 'admin', 'admin');


CREATE TYPE test_status AS ENUM ('PENDING', 'APPROVED');

CREATE TABLE test_data (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  user_id BIGINT NOT NULL REFERENCES user_data(id),
  name VARCHAR(50),
  description VARCHAR(50),
  status test_status NOT NULL,
  created_at DATE,
  questions JSON
);

INSERT INTO test_data (id, user_id, name, description, status, created_at, questions)
    VALUES (0, 0, 'testtest', 'test', 'APPROVED', now(), '{"question":"q"}'::json);