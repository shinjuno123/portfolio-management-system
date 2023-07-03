CREATE TABLE IF NOT EXISTS notification (
    id CHAR(36) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    image_url TEXT NOT NULL,
    video_url TEXT,
    active BOOLEAN NOT NULL,
    displayed BOOLEAN NOT NULL,
    version INTEGER,
    updated TIMESTAMP,
    uploaded TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS relevant_site (
    id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    url TEXT NOT NULL,
    version INTEGER,
    uploaded TIMESTAMP,
    updated TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
