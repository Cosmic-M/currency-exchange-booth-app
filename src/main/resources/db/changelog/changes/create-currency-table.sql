CREATE TABLE IF NOT EXISTS currency (
    id bigint auto_increment,
    ccy varchar(255) NOT NULL,
    base_ccy varchar(255) NOT NULL,
    buy decimal(14, 5) NOT NULL,
    sale decimal(14, 5) NOT NULL,
    date_time timestamp NOT NULL,
    CONSTRAINT currency_pk PRIMARY KEY (id)
    );

--rollback DROP TABLE currency;
