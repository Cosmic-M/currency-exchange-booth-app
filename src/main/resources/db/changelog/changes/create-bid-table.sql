CREATE TABLE IF NOT EXISTS bid (
    id bigint auto_increment,
    ccy_sale varchar(255) NOT NULL,
    ccy_buy varchar(255) NOT NULL,
    ccy_sale_amount decimal(14, 5) NOT NULL,
    ccy_buy_amount decimal(14, 5) NOT NULL,
    phone varchar(255) NOT NULL,
    date_time timestamp NOT NULL,
    status varchar(255) NOT NULL,
    CONSTRAINT bid_pk PRIMARY KEY (id)
    );

--rollback DROP TABLE bid;
