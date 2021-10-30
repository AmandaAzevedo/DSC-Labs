CREATE SEQUENCE comment_sequence
    INCREMENT BY 1
    START WITH 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    NO CYCLE;


CREATE TABLE comment (
    id int8 NOT NULL DEFAULT nextval('comment_sequence'),
    content varchar(280) NOT NULL,
    discipline_id int8 NOT NULL ,
    CONSTRAINT discipline_id_FK FOREIGN KEY (discipline_id) REFERENCES discipline(id),
    user_id int8 NOT NULL,
    CONSTRAINT user_id_FK FOREIGN KEY (user_id) REFERENCES user_system(id) ON DELETE CASCADE

)