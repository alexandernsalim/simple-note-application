create table if not exists note(
	id			serial primary key not null,
	title 		varchar(250) not null,
	description varchar(250) not null,
	author 		varchar(250) not null,
	created_at	timestamp not null,
	updated_at	timestamp not null
);

insert into note (title, description, author, created_at, updated_at)
values ('Welcome to simple note', 'This will be you description', 'Simple Note', now(), now());
