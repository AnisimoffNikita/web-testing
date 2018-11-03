CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

CREATE TABLE user_data (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
  username VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  role user_role NOT NULL
);

INSERT INTO user_data (username, email, password, role) VALUES ('admin', 'admin', 'admin', 'ADMIN');

CREATE TABLE person_data (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
  user_id VARCHAR(50) NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  birthday DATE
);
