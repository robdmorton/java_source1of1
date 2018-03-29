drop database movies;
create database movies;
use movies;
create table movie (
    id int not null auto_increment,
    title varchar(50),
    year int,
    price decimal(8,2),
    primary key(id)
);

insert into movie (title, year, price)
    values ("It's a Wonderful Life", 1946, 14.95);
insert into movie (title, year, price) 
    values ("The Great Race", 1965, 12.95);
insert into movie (title, year, price) 
    values ("Young Frankenstein", 1974, 16.95);
insert into movie (title, year, price) 
    values ("The Return of the Pink Panther", 
    1975, 11.95);
insert into movie (title, year, price) 
    values ("Star Wars", 1977, 17.95);
insert into movie (title, year, price) 
    values ("The Princess Bride", 1987, 16.95);
insert into movie (title, year, price) 
    values ("Glory", 1989, 14.95);
insert into movie (title, year, price) 
    values ("Apollo 13", 1995, 19.95);
insert into movie (title, year, price) 
    values ("The Game", 1997, 14.95);
insert into movie (title, year, price) 
    values ("The Lord of the Rings: The Fellowship of the
      Ring, 2001, 19.95);
