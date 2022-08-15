drop table if exists tb_account;
create table tb_account(
	account_id serial primary key,
-- 	用户公钥
	account_publicKey varchar(500),
-- 	用户头像url，可以去网上找api生成
	account_avatar varchar(500),
--  用户余额
	account_balance numeric,
	
	account_deleted integer default 0,
	account_userid integer,
	constraint FK1_tb_account foreign key(account_userid) 
	references tb_user(user_id) on update cascade on delete cascade
);
select * from tb_account;
