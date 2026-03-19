CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name_category VARCHAR(150) NOT NULL,
                            type_category VARCHAR(50) NOT NULL,
                            status_category VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
                            user_id BIGINT NOT NULL,
                            CONSTRAINT fk_categories_user FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT uk_categories_user_name UNIQUE (user_id, name_category)
);