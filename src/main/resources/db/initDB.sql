DROP TABLE IF EXISTS meal;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meal
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id     INTEGER NOT NULL,
    date_time   TIMESTAMP,
    description TEXT NOT NULL,
    calories    INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX meals_user_id_idx ON meal (user_id);
CREATE INDEX meals_date_idx ON meal (date_time);
CREATE UNIQUE INDEX meals_unique_user_id_date_idx ON meal (user_id, date_time);