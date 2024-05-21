DROP TABLE IF EXISTS "books";
DROP TABLE IF EXISTS "authors";

-- Create the sequence
CREATE SEQUENCE authors_id_seq;

-- Create the table
CREATE TABLE "authors"
(
    "id"   bigint NOT NULL,
    "name" text,
    "age"  integer,
    CONSTRAINT "authors_pkey" PRIMARY KEY ("id")
);

-- Alter the table to set the default value for the id column
ALTER TABLE "authors"
    ALTER COLUMN "id" SET DEFAULT nextval('authors_id_seq');


CREATE TABLE "books"
(
    "isbn"      text NOT NULL,
    "title"     text,
    "author_id" bigint,
    CONSTRAINT "books_pkey" PRIMARY KEY ("isbn"),
    CONSTRAINT "fk_author" FOREIGN KEY (author_id)
        REFERENCES authors (id)
);