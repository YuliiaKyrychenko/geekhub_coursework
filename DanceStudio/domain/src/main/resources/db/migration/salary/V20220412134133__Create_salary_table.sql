CREATE TABLE IF NOT EXISTS Salary (
    id serial PRIMARY KEY,
    teacherId serial (256) not null,
    firstName VARCHAR (256) not null,
    lastName VARCHAR (256) not null,
    month VARCHAR (256) not null,
    salary serial (256) not null,
    );