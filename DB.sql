
 use Ldata;
 drop table if exists users;


 create table if not exists users(
	user_name varchar(20),
	user_id int auto_increment primary key,
	user_password varchar(60),
	one_time_pass varchar(60)
 );

 create table if not exists employees(
	id int auto_increment primary key,
	name varchar(20)
 );


 create table if not exists urlShortener(
	short_url varchar(9) primary key,
	long_url varchar(2048) not null,
	user_id int not null,
	foreign key(user_id) references users(user_id)
 )

drop procedure if exists addUser;




select * from users;