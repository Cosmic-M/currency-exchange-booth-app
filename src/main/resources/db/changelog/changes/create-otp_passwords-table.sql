CREATE TABLE IF NOT EXISTS otp_passwords (
    deal_id bigint NOT NULL,
    password varchar(255) NOT NULL
    );

--rollback DROP TABLE otp_passwords;