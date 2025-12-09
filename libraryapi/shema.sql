CREATE TABLE author (
	id uuid not null primary key,
	name varchar(100) not null,
	birthday date not null,
	nationality varchar(50) not null
);

CREATE TABLE book (
	id uuid not null primary key,
	isbn varchar(20) not null,
	title varchar(150) not null,
	publication_date date not null,
	genre varchar(30) not null,
	price numeric(18,2),
	id_author uuid not null references author(id)
	constraint chk_genre check (genre in ('FICCAO', 'FANTASIA', 'MISTERIO', 'ROMANCE', 'BIOGRAFIA', 'CIÊNCIA'))
);
