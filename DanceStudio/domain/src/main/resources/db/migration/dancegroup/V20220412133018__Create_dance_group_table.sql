CREATE TABLE IF NOT EXISTS DanceGroup (
    id serial PRIMARY KEY,
    style VARCHAR (256) not null,
    teacherId serial not null,
    firstName VARCHAR (256) not null,
    lastName VARCHAR (256) not null,
    ageCategorie VARCHAR (256) not null,
    danceHall VARCHAR (256) not null,
    daysOfWeek VARCHAR (256) not null,
    danceTime VARCHAR (256) not null
    );