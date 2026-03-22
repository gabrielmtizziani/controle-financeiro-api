CREATE TABLE transactions (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              description VARCHAR(255),
                              amount DECIMAL(15,2) NOT NULL,
                              transaction_type VARCHAR(20) NOT NULL,
                              transaction_date DATE NOT NULL,
                              created_at TIMESTAMP NOT NULL,
                              account_id BIGINT NOT NULL,
                              category_id BIGINT NOT NULL,
                              user_id BIGINT NOT NULL,
                              CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts(id),
                              CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories(id),
                              CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(id)
);