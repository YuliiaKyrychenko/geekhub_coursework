CREATE TABLE IF NOT EXISTS Person (
    id serial PRIMARY KEY,
    name VARCHAR (256) not null,
    firstName VARCHAR (256) not null,
    lastName VARCHAR (256) not null,
    contacts VARCHAR (256) not null,
    birthday VARCHAR (256) not null,
    role VARCHAR (256) not null
    );