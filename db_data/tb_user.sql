drop table if exists tb_user;
create table tb_user(
	user_id serial primary key,
	user_username varchar(30),
	user_password varchar(30),
	user_deleted integer default 0
);
insert into tb_user values(1,'admin','admin',0);
