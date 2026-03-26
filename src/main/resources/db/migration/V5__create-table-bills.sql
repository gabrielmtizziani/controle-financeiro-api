CREATE TABLE bills (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       description VARCHAR(255) NOT NULL,
                       amount DECIMAL(15,2) NOT NULL,
                       due_date DATE NOT NULL,
                       payment_date DATE,
                       status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
                       account_id BIGINT NOT NULL,
                       category_id BIGINT NOT NULL,
                       user_id BIGINT NOT NULL,
                       CONSTRAINT fk_bill_account FOREIGN KEY (account_id) REFERENCES accounts(id),
                       CONSTRAINT fk_bill_category FOREIGN KEY (category_id) REFERENCES categories(id),
                       CONSTRAINT fk_bill_user FOREIGN KEY (user_id) REFERENCES users(id)
);