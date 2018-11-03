CREATE TABLE "user" (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
  login VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
);

CREATE TABLE "person" (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
  user_id VARCHAR(50) NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  birthday DATE
);
