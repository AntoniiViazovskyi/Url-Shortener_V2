-- Creating the Role table --
CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Creating the Role table --
CREATE TABLE "user" (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Creating a table for linking users and roles (user_roles) --
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

-- Creating the URL table --
CREATE TABLE url (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    short_id VARCHAR(255) NOT NULL,
    long_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    click_count INT NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE SET NULL
);