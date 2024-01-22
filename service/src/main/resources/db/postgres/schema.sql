DROP TABLE IF EXISTS USERS CASCADE;

CREATE TABLE IF NOT EXISTS USERS (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_at TIMESTAMP,
    PRIMARY KEY (id)
);

INSERT INTO USERS (name, password, email, roles, active)
VALUES ('John Doe', 'johnspassword', 'john@doe.com', 'Admin,User', true);

INSERT INTO USERS (name, password, email, roles, active)
VALUES ('Jane Smith', 'janespassword', 'jane@smith.com', 'User', true);

INSERT INTO USERS (name, password, email, roles, active)
VALUES ('Alice Johnson', 'alicespassword', 'alice@johnson.com', 'User', false);