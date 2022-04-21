CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) not null primary key,
    password VARCHAR(250) not null,
    enabled BOOLEAN NOT NULL
);