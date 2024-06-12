-- Creating the Role table --
CREATE TABLE roles (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Creating the User table --
CREATE TABLE users (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(100) NOT NULL CHECK (LENGTH(password) BETWEEN 8 AND 100)
);

-- Creating a table for linking users and roles (user_roles) --
CREATE TABLE user_roles (
    user_id LONG NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- Creating the URL table --
CREATE TABLE urls (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    short_id VARCHAR(20) NOT NULL UNIQUE,
    long_url VARCHAR(500) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    click_count INT NOT NULL,
    user_id LONG NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);