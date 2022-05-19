create table user
(
    email      varchar(255) not null
        primary key,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    password   varchar(255) null,
    role       varchar(255) null
);

INSERT INTO user (email, first_name, last_name, password, role) VALUES ('test@test.com', 'firstName', 'lastName', '9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08', 'student');

