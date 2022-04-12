CREATE TABLE IF NOT EXISTS Attendance (
    id serial PRIMARY KEY,
    studentId serial (256) not null,
    firstName VARCHAR (256) not null,
    lastName VARCHAR (256) not null,
    month VARCHAR (256) not null,
    discount serial (256) not null,
    currentAttendance serial (256) not null,
    generalAttendace serial (256) not null,
    generalSum serial (256) not null
    );