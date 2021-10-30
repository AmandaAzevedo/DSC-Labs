CREATE SEQUENCE user_sequence
    INCREMENT BY 1
    START WITH 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    NO CYCLE;

CREATE TABLE user_system (
    id int8 NOT NULL DEFAULT nextval('user_sequence'),
    name varchar(400) NOT NULL,
    email varchar(400) NOT NULL,
    password varchar(400) NOT NULL,
    unique(email)
)
