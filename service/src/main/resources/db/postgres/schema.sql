CREATE TABLE IF NOT EXISTS USERS (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_at TIMESTAMP,
    PRIMARY KEY (id)
);

INSERT INTO USERS (name, password, email, role, status)
VALUES ('John Doe', 'johnspassword', 'john@doe.com', 'Admin', 'Active');

INSERT INTO USERS (name, password, email, role, status)
VALUES ('Jane Smith', 'janespassword', 'jane@smith.com', 'User', 'Active');

INSERT INTO USERS (name, password, email, role, status)
VALUES ('Alice Johnson', 'alicespassword', 'alice@johnson.com', 'User', 'Inactive');