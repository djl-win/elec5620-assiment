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

select * from tb_nft;

