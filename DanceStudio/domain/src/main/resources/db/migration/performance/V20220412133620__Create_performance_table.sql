CREATE TABLE IF NOT EXISTS Performance (
    id serial PRIMARY KEY,
    name VARCHAR (256) not null,
    date VARCHAR (256) not null,
    place VARCHAR (256) not null,
    price serial not null
    );