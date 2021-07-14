CREATE TABLE user (
    id UUID PRIMARY KEY,
    name VARCHAR(128)
);

CREATE TABLE user_balance (
    user_id UUID,
    amount VARCHAR(255) NOT NULL,
    currency CHAR(3),

    FOREIGN KEY (user_id) REFERENCES user(id)
);
