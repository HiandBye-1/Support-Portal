
 use Ldata;

drop table if exists temp_emails;
drop table if exists url_shortener;
drop table if exists employees;
drop table if exists users;

 create table if not exists users(
	user_name varchar(20),
	user_id int auto_increment primary key,
	user_password varchar(60),
	one_time_pass varchar(60),
	email varchar(254) NULL
 );

 create table if not exists employees(
	id int auto_increment primary key,
	name varchar(20)
 );


 create table if not exists  url_shortener(
	short_url varchar(9) primary key,
	long_url varchar(2048) not null,
	user_id int not null,
	foreign key(user_id) references users(user_id)
 );

 create table if not exists temp_emails(
	user_id int not null primary key,
	email varchar(254) not null,
	code_hashed varchar(255) not null,
	expires datetime not null,
	attempts int not null default 0,
	foreign key(user_id) references users(user_id) 
 );



