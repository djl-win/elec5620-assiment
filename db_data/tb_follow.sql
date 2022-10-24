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