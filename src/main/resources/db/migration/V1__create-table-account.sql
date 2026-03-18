CREATE TABLE accounts (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name_account VARCHAR(150) NOT NULL,
                          account_type VARCHAR(50) NOT NULL,
                          initial_balance DECIMAL(15,2) NOT NULL,
                          status_account VARCHAR(20) NOT NULL DEFAULT 'ATIVA'
);