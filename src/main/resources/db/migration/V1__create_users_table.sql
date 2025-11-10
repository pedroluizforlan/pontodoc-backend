CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    use_type VARCHAR(50) NOT NULL CHECK (use_type IN ('MANAGER', 'ASSESSOR', 'EMPLOYEE')),
    email_verified BOOLEAN, 
    created_date TIMESTAMP,
    uploaded_date TIMESTAMP,
    deleated_date TIMESTAMP
);