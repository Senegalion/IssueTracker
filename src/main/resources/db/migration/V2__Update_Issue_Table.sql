ALTER TABLE issues
    ADD COLUMN latitude      VARCHAR(50)     NOT NULL,
    ADD COLUMN longitude     VARCHAR(50)     NOT NULL,
    ADD COLUMN technician_id BIGINT UNSIGNED NULL,
    ADD CONSTRAINT fk_issues_technician FOREIGN KEY (technician_id) REFERENCES technicians (technician_id);