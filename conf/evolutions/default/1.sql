# Contacts Schema

# --- !Ups

CREATE SEQUENCE contact_id_seq;

CREATE TABLE contact (
    id integer NOT NULL DEFAULT nextval('contact_id_seq'),
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    phone varchar(20) not null,
    address varchar(255) not null,
    primary key (id)
);

# --- !Downs

DROP TABLE contact;
DROP SEQUENCE contact_id_seq;

