CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL DEFAULT 'USER',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE issues (
                        id SERIAL PRIMARY KEY,
                        user_id INT REFERENCES users(id) ON DELETE CASCADE,
                        description TEXT NOT NULL,
                        status VARCHAR(20) DEFAULT 'NEW',
                        location VARCHAR(255),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE technicians (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             skillset TEXT NOT NULL
);
