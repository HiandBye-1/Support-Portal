
 use Ldata;
 drop table if exists users;


 create table if not exists users(
	user_name varchar(20),
	user_id int auto_increment primary key,
	user_password varchar(60),
	one_time_pass varchar(60)
 );

 create table if not exists employees(
	id int primary key,
	name varchar(20)
 );

drop procedure if exists addUser;
delimiter //

create procedure addUser(p_name varchar(20), p_password varchar(60), one_time varchar(60))
begin
insert into users(user_name, user_password, one_time_pass) values(p_name, p_password, one_time);
end //
 
delimiter ;


select * from users;