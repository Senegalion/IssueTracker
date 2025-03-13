CREATE TABLE IF NOT EXISTS users
(
    user_id    BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50)         NOT NULL,
    surname    VARCHAR(50)         NOT NULL,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    role       VARCHAR(20)         NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP                    DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS technicians
(
    technician_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)    NOT NULL,
    surname       VARCHAR(100)    NOT NULL,
    skillset      TEXT            NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS issues
(
    issue_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT UNSIGNED NOT NULL,
    description TEXT            NOT NULL,
    status      ENUM ('NEW', 'IN_PROGRESS', 'RESOLVED') DEFAULT 'NEW',
    location    VARCHAR(255),
    created_at  TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
