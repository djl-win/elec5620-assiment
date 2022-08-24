drop table if exists tb_log;
create table tb_log(
-- 	日志id
	log_id serial primary key,
--  交易人A的公钥
	log_pubKeyUserA varchar(500),
--  交易人B的公钥
	log_pubKeyUserB varchar(500),
-- 	交易类型 1为充值 2为A买B 3为A卖B
	log_type integer,
-- 	交易nftid
	log_nftid integer,
-- 	交易价格
	log_price numeric,
-- 	交易时间
	log_date date,
--  交易描述
	log_description varchar(500),
--  交易状态 1为成功，0为失败
	log_status integer
)