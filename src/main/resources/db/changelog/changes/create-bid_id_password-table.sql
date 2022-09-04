CREATE TABLE IF NOT EXISTS bid_id_password (
    bid_id bigint NOT NULL,
    password varchar(255) NOT NULL
    );

--rollback DROP TABLE bid_id_password;