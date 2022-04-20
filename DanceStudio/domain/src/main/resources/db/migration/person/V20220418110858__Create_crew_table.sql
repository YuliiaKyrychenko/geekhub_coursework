CREATE TABLE IF NOT EXISTS Crew (
    id serial PRIMARY KEY,
    firstName VARCHAR (256) not null,
    lastName VARCHAR (256) not null,
    contacts VARCHAR (256) not null,
    birthday VARCHAR (256) not null,
    role VARCHAR (256) not null
    );