CREATE TABLE login_users
(
    id serial NOT NULL,
    username character varying(200) NOT NULL,
    email character varying(300) NOT NULL,
    firstname character varying(200),
    lastname character varying(200),
    nomdeplume character varying(200),
    password text NOT NULL,
    create_date timestamp without time zone NOT NULL,
    PRIMARY KEY (id)
);