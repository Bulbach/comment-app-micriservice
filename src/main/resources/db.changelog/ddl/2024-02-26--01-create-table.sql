CREATE TABLE IF NOT EXISTS comments (
                          id SERIAL PRIMARY KEY,
                          time TIMESTAMP NOT NULL,
                          text VARCHAR(255) NOT NULL,
                          username VARCHAR(255) NOT NULL,
                          news_id BIGINT NOT NULL REFERENCES news(id)
);