CREATE TABLE refresh_tokens (
                                id BIGSERIAL PRIMARY KEY,         -- Primary key for the table
                                token VARCHAR(255) NOT NULL,      -- Token string
                                expiry_date TIMESTAMPTZ NOT NULL, -- Expiry date and time for the token
                                user_id BIGINT UNIQUE,            -- User ID with a one-to-one relationship
                                CONSTRAINT fk_refresh_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);