CREATE TABLE IF NOT EXISTS Attendance (
    id serial PRIMARY KEY,
    groupId serial not null,
    studentId serial not null,
    firstName VARCHAR (256) not null,
    lastName VARCHAR (256) not null,
    month VARCHAR (256) not null,
    currentAttendance serial not null,
    generalAttendance serial not null,
    generalSum serial not null
    );