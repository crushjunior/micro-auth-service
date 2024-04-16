CREATE TABLE IF NOT EXISTS passport_data
(
    passport_number VARCHAR(255) PRIMARY KEY,
    issuance_date   DATE,
    birth_date      DATE
);

CREATE TABLE IF NOT EXISTS client
(
    id                             UUID PRIMARY KEY,
    first_name                     VARCHAR(30)         NOT NULL,
    last_name                      VARCHAR(30)         NOT NULL,
    passport_number                VARCHAR(255) REFERENCES passport_data (passport_number)
);

CREATE TABLE IF NOT EXISTS user_profile
(
    id                    UUID PRIMARY KEY,
    client_id             UUID REFERENCES client (id),
    password              VARCHAR(255)       NOT NULL,
    email                 VARCHAR(50),
    app_registration_date DATE               NOT NULL,
    mobile_phone          VARCHAR(12) UNIQUE NOT NULL
);


CREATE TABLE IF NOT EXISTS token
(
    id          BIGSERIAL PRIMARY KEY,
    token       VARCHAR,
    expiry_date TIMESTAMP,
    client_id   UUID REFERENCES client (id)
);

CREATE TABLE IF NOT EXISTS refresh_token
(
    id          BIGSERIAL PRIMARY KEY,
    token       VARCHAR,
    expiry_date TIMESTAMP,
    client_id   UUID REFERENCES client (id)
);