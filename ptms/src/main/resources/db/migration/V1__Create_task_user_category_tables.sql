-- V1__Create_task_user_category_tables.sql

-- Create the "users" table
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       user_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Create the "categories" table
CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

-- Create the "tasks" table
CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       due_date TIMESTAMP,
                       created_at TIMESTAMPTZ DEFAULT current_timestamp,
                       user_id BIGINT,
                       category_id BIGINT,
                       priority VARCHAR(255) NOT NULL,  -- Assuming Priority is an Enum stored as a string
                       status VARCHAR(255) NOT NULL,    -- Assuming TaskStatus is an Enum stored as a string
                       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);
