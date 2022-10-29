drop table if exists tb_userdetail;
create table tb_userdetail(
		userdetail_id serial primary key,
		userdetail_name varchar(30),
		userdetail_email varchar(30),
		userdetail_phone varchar(20),
		userdetail_deleted integer default 0,
		userdetail_userid integer,
		constraint FK1_tb_userdetail foreign key(userdetail_userid) 
	    references tb_user(user_id) on update cascade on delete cascade
);
