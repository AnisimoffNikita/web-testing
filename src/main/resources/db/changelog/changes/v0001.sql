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
INSERT INTO person_data (id, first_name, last_name) VALUES ('9a932257-bd92-4e87-ade0-046006fe134f', 'user', 'user');
INSERT INTO person_data (id, first_name, last_name) VALUES ('7be76d3f-ccac-42f8-9ded-e09ad7fef5d7', 'user2', 'user2');
INSERT INTO person_data (id, first_name, last_name) VALUES ('74657632-ea59-4183-932d-2e57bbd4e9e9', 'user3', 'user3');


CREATE TABLE users (
  id UUID PRIMARY KEY NOT NULL,
  person_id UUID NOT NULL REFERENCES person_data(id),
  username VARCHAR(100) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  role user_role NOT NULL
);

INSERT INTO users (id, person_id, username, email, password, role) VALUES ('12412cdb-398f-4def-9cec-325b11968b56', 'e22e6ce8-406e-4618-9d9e-44114ac13697', 'admin', 'admin', 'admin', 'ADMIN');
INSERT INTO users (id, person_id, username, email, password, role) VALUES ('7c803c41-ca5f-4e66-9483-7e361db72917', '833f9d04-9d81-4983-9dae-b69a89d9efe7', 'admin2', 'admin2', 'admin2', 'ADMIN');
INSERT INTO users (id, person_id, username, email, password, role) VALUES ('a4c7eb02-1c79-48df-a1a7-7701bb500dcf', 'b040df7e-5d36-4c08-9252-521a5461485c', 'admin3', 'admin3', 'admin3', 'ADMIN');
INSERT INTO users (id, person_id, username, email, password, role) VALUES ('be7b8ae6-5368-40f1-9b07-b33027acf43f', '9a932257-bd92-4e87-ade0-046006fe134f', 'user', 'user', 'user', 'USER');
INSERT INTO users (id, person_id, username, email, password, role) VALUES ('ecc4f8af-c758-434a-84c1-6df04cbb0936', '7be76d3f-ccac-42f8-9ded-e09ad7fef5d7', 'user2', 'user2', 'user2', 'USER');
INSERT INTO users (id, person_id, username, email, password, role) VALUES ('0d984722-ba7f-4b57-9afb-3078191f2a01', '74657632-ea59-4183-932d-2e57bbd4e9e9', 'user3', 'user3', 'user3', 'USER');


CREATE TYPE exam_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED', 'DELETED');

CREATE TABLE exams (
  id UUID PRIMARY KEY NOT NULL,
  user_id UUID NOT NULL REFERENCES users(id),
  name VARCHAR(100),
  description VARCHAR(800),
  pass_count INT NOT NULL,
  status exam_status NOT NULL,
  created_at DATE,
  questions JSON
);

INSERT INTO exams (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('0596c2c0-a70a-47dd-81c8-31411a5b132a', '12412cdb-398f-4def-9cec-325b11968b56', 'тест главный', 'большое описание со словом математика', 8, 'APPROVED', to_timestamp(1195333200)::timestamp with time zone, '[]'::json);
INSERT INTO exams (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('66bcd4a3-a3d5-409e-9a38-e0d7b029a020', '7c803c41-ca5f-4e66-9483-7e361db72917', 'тест номер два', 'описание теста номер два', 10, 'APPROVED', to_timestamp(1195333200)::timestamp with time zone, '[]'::json);

INSERT INTO exams (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('446ae2f3-eb60-44cb-b889-22f14ef06d82', 'be7b8ae6-5368-40f1-9b07-b33027acf43f', 'тестик', 'много слов', 3, 'APPROVED', to_timestamp(1195333200)::timestamp without time zone, '[{"id":0,"questionText":"1+1","type":"SINGLE_ANSWER","variants":["1","2"],"correctVariantsId":[1],"correctInputAnswer":null}]'::json);

INSERT INTO exams (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('6c8d5358-4111-4d28-a12e-fecfa3c1ce78', 'ecc4f8af-c758-434a-84c1-6df04cbb0936', 'тест второго юзера', 'длинное описание', 10, 'APPROVED', to_timestamp(1195333200)::timestamp with time zone, '[]'::json);

INSERT INTO exams (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('e17b57b3-232f-4405-82e7-f21f9ef93c1a', '0d984722-ba7f-4b57-9afb-3078191f2a01', 'ненужный тест', 'тест, который будет удален юзером 3', 0, 'APPROVED', to_timestamp(1195333200)::timestamp with time zone, '[]'::json);
INSERT INTO exams (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('6d94c110-43f1-433f-87fa-9ce0fb81edcc', '0d984722-ba7f-4b57-9afb-3078191f2a01', 'еще один ненужный тест', 'тест, который будет удален админом', 0, 'APPROVED', to_timestamp(1195333200)::timestamp with time zone, '[]'::json);
INSERT INTO exams (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('a7f330fb-9a1a-492f-a52c-3a5be37ccff4', '0d984722-ba7f-4b57-9afb-3078191f2a01', 'ожидающий тест', 'тест, который будет добавлен админом', 0, 'PENDING', to_timestamp(1195333200)::timestamp with time zone, '[]'::json);
INSERT INTO exams (id, user_id, name, description, pass_count, status, created_at, questions)
    VALUES ('4dfe3450-b8eb-4d50-9f0b-01b324a553b1', '0d984722-ba7f-4b57-9afb-3078191f2a01', 'еще один ожидающий тест', 'тест, который будет отклонен админом', 0, 'PENDING', to_timestamp(1195333200)::timestamp with time zone, '[]'::json);

CREATE TABLE exam_results (
  id UUID PRIMARY KEY NOT NULL,
  user_id UUID NOT NULL REFERENCES users(id),
  test_id UUID NOT NULL REFERENCES exams(id),
  result VARCHAR(50),
  passed_at DATE
);

INSERT INTO exam_results  (id, user_id, test_id, result, passed_at)
    VALUES ('18c4f984-22bd-4edd-ae66-6fb157328337', '12412cdb-398f-4def-9cec-325b11968b56', '0596c2c0-a70a-47dd-81c8-31411a5b132a', '11', to_timestamp(1195333200)::timestamp with time zone);

INSERT INTO exam_results  (id, user_id, test_id, result, passed_at)
    VALUES ('9d7c4fc9-56bb-4e39-b359-f23d56c3bfe2', '7c803c41-ca5f-4e66-9483-7e361db72917', '66bcd4a3-a3d5-409e-9a38-e0d7b029a020', '12', to_timestamp(1195333200)::timestamp with time zone);
INSERT INTO exam_results  (id, user_id, test_id, result, passed_at)
    VALUES ('5f4f4ac2-bd8f-4193-847c-7fef7bb4c7ab', 'be7b8ae6-5368-40f1-9b07-b33027acf43f', '66bcd4a3-a3d5-409e-9a38-e0d7b029a020', '1/2', to_timestamp(1195333200)::timestamp with time zone);

INSERT INTO exam_results  (id, user_id, test_id, result, passed_at)
    VALUES ('74f7a1b8-5bbd-411f-82a3-9c2aa1e9ab3e', 'ecc4f8af-c758-434a-84c1-6df04cbb0936', '446ae2f3-eb60-44cb-b889-22f14ef06d82', '1/3', to_timestamp(1195333200)::timestamp with time zone);

INSERT INTO exam_results  (id, user_id, test_id, result, passed_at)
    VALUES ('073b22c6-4deb-40cf-99d5-4b36c1e41478', '0d984722-ba7f-4b57-9afb-3078191f2a01', '6c8d5358-4111-4d28-a12e-fecfa3c1ce78', '3/3', to_timestamp(1195333200)::timestamp with time zone);
