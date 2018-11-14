CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE person_data (
  id UUID PRIMARY KEY NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  birthday DATE,
  avatar VARCHAR(150)
);

INSERT INTO person_data (id, first_name, last_name) VALUES ('e22e6ce8-406e-4618-9d9e-44114ac13697', 'admin', 'admin');
INSERT INTO person_data (id, first_name, last_name) VALUES ('833f9d04-9d81-4983-9dae-b69a89d9efe7', 'admin2', 'admin2');
INSERT INTO person_data (id, first_name, last_name) VALUES ('b040df7e-5d36-4c08-9252-521a5461485c', 'admin3', 'admin3');


CREATE TABLE user_data (
  id UUID PRIMARY KEY NOT NULL,
  person_id UUID NOT NULL REFERENCES person_data(id),
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  role user_role NOT NULL
);

INSERT INTO user_data (id, person_id, username, email, password, role) VALUES ('12412cdb-398f-4def-9cec-325b11968b56', 'e22e6ce8-406e-4618-9d9e-44114ac13697', 'admin', 'admin', 'admin', 'ADMIN');
INSERT INTO user_data (id, person_id, username, email, password, role) VALUES ('7c803c41-ca5f-4e66-9483-7e361db72917', '833f9d04-9d81-4983-9dae-b69a89d9efe7', 'admin2', 'admin2', 'admin2', 'ADMIN');
INSERT INTO user_data (id, person_id, username, email, password, role) VALUES ('a4c7eb02-1c79-48df-a1a7-7701bb500dcf', 'b040df7e-5d36-4c08-9252-521a5461485c', 'admin3', 'admin3', 'admin3', 'ADMIN');




CREATE TYPE test_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

CREATE TABLE test_data (
  id UUID PRIMARY KEY NOT NULL,
  user_id UUID NOT NULL REFERENCES user_data(id),
  name VARCHAR(50),
  description VARCHAR(400),
  pass_count INT NOT NULL,
  status test_status NOT NULL,
  created_at DATE,
  questions JSON
);

INSERT INTO test_data (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('0596c2c0-a70a-47dd-81c8-31411a5b132a', '12412cdb-398f-4def-9cec-325b11968b56', 'тест главный', 'большое описание со словом математика', 0, 'APPROVED', to_timestamp(1195333200), '[]'::json);
INSERT INTO test_data (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('66bcd4a3-a3d5-409e-9a38-e0d7b029a020', '7c803c41-ca5f-4e66-9483-7e361db72917', 'дичь какая то', 'еще что то, на пофиг', 0, 'APPROVED', to_timestamp(1195333200), '[]'::json);


CREATE TABLE test_result_data (
  id UUID PRIMARY KEY NOT NULL,
  user_id UUID NOT NULL REFERENCES user_data(id),
  test_id UUID NOT NULL REFERENCES test_data(id),
  result VARCHAR(50),
  passed_at DATE
);

INSERT INTO test_result_data (id, user_id, test_id, result, passed_at)
    VALUES ('18c4f984-22bd-4edd-ae66-6fb157328337', '12412cdb-398f-4def-9cec-325b11968b56', '0596c2c0-a70a-47dd-81c8-31411a5b132a', '11', to_timestamp(1195333200));

INSERT INTO test_result_data (id, user_id, test_id, result, passed_at)
    VALUES ('9d7c4fc9-56bb-4e39-b359-f23d56c3bfe2', '7c803c41-ca5f-4e66-9483-7e361db72917', '66bcd4a3-a3d5-409e-9a38-e0d7b029a020', '12', to_timestamp(1195333200));
