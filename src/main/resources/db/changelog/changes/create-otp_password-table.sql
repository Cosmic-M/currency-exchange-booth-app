CREATE TABLE IF NOT EXISTS otp_password (
    deal_id bigint NOT NULL,
    password varchar(255) NOT NULL
    );

--rollback DROP TABLE otp_password;