
CREATE TABLE users
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name    VARCHAR(255)                            NOT NULL,
    last_name     VARCHAR(255)                            NOT NULL,
    email         VARCHAR(255)                            NOT NULL,
    contact_phone VARCHAR(45),
    password      VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);