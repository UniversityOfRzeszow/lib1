CREATE SEQUENCE author_id_seq;
CREATE TABLE authors (
    id smallint NOT NULL DEFAULT nextval('author_id_seq'),
    name CHARACTER VARYING,
    surname CHARACTER VARYING,
    PRIMARY KEY (id)
);

INSERT INTO authors (name, surname) VALUES ('Henryk', 'Sienkiewicz');

CREATE SEQUENCE book_id_seq;
CREATE TABLE books (
    id SMALLINT NOT NULL DEFAULT nextval('book_id_seq'),
    title CHARACTER VARYING,
    publisher CHARACTER VARYING,
    year CHARACTER VARYING,
    description CHARACTER VARYING,
    author_id SMALLINT,
    PRIMARY KEY (id)
    --FOREIGN KEY (author_id) REFERENCES books_authors (id)
 );

 --ALTER TABLE books 
 -- ADD COLUMN publisher CHARACTER VARYING,
 --- ADD COLUMN description CHARACTER VARYING;

 INSERT INTO books (title, publisher, year, description) 
 VALUES ('Krzyżacy', 'Nowa Era', 1990, 'Krzyżacy - ciężka książka');

 --SELECT * FROM authors;

 CREATE TABLE books_authors (
	author_id SMALLINT,
	book_id SMALLINT,
	FOREIGN KEY (author_id) REFERENCES authors(id),
	FOREIGN KEY (book_id) REFERENCES books(id)
)

INSERT INTO books_authors (author_id, book_id) VALUES (
(SELECT id FROM authors limit 1)
,
(SELECT id FROM books limit 1)
);

--select * from authors;
--select * from books;

select * from books_authors;


SELECT * 
  FROM books 
  JOIN books_authors 
    ON books.id = books_authors.book_id
  JOIN authors 
    ON authors.id = books_authors.author_id;


