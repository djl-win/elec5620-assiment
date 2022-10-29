drop table if exists tb_user;
create table tb_user(
	user_id serial primary key,
	user_username varchar(30),
	user_password varchar(30),
	user_deleted integer default 0
);

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

drop table if exists tb_account;
create table tb_account(
	account_id serial primary key,
-- 	user public key
	account_publicKey varchar(500),
-- 	user avatar url
	account_avatar varchar(500),
--  	user balance
	account_balance numeric,
	
	account_deleted integer default 0,
	account_userid integer,
	constraint FK1_tb_account foreign key(account_userid) 
	references tb_user(user_id) on update cascade on delete cascade
);

drop table if exists tb_nft;
create table tb_nft (
	-- nft id
	nft_id serial primary key,
	-- nft unique address, encrypt future
	nft_signature varchar(500),
	-- nft address to show
	nft_url varchar(500),
	-- nft price default 0
	nft_price numeric default 0,
	-- nft description
	nft_description varchar(100),
	-- nft likes to rank
	nft_likes integer default 0,
	-- logic delete
	nft_deleted integer default 0,
	nft_version integer default 1,
	nft_userid integer,
	constraint FK1_tb_nft foreign key(nft_userid) 
	references tb_user(user_id) on update cascade on delete cascade
);

drop table if exists tb_log;
create table tb_log(
-- 	log id
	log_id serial primary key,
--  	pubKeyUserA
	log_pubKeyUserA varchar(500),
-- 	pubKeyUserB
	log_pubKeyUserB varchar(500),
-- 	Transaction type 1 charge, 2 A buy from B, 3 A sell to B
	log_type integer,
-- 	NFT ID
	log_nftid integer,
-- 	Transaction price
	log_price numeric,
-- 	Transaction time
	log_date date,
--  	Transaction description
	log_description varchar(500),
--  	Transaction status 1 success, 2 fail
	log_status integer
)

drop table if exists tb_follow;
create table tb_follow(
		follow_id serial primary key,
		follow_userid integer,
		follow_nftid integer,
		constraint FK1_tb_follow foreign key(follow_userid) 
	    references tb_user(user_id) on update cascade on delete cascade,
		constraint FK2_tb_follow foreign key(follow_nftid) 
	    references tb_nft(nft_id) on update cascade on delete cascade
);

drop table if exists tb_order;
create table tb_order(
	
	order_id serial primary key,

	order_buyerid integer,
	
	order_sellerid integer,
	
	order_nftid integer,

	order_price numeric,
-- 	0 pending 1 success 2 failure
	order_status integer,
	
	order_date timestamp without time zone,
	
	constraint FK1_tb_order foreign key(order_buyerid) 
	references tb_user(user_id) on update cascade on delete cascade,
	
	constraint FK2_tb_order foreign key(order_sellerid) 
	references tb_user(user_id) on update cascade on delete cascade,
	
	constraint FK3_tb_order foreign key(order_nftid) 
	references tb_nft(nft_id) on update cascade on delete cascade
);



