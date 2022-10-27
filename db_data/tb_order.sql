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

