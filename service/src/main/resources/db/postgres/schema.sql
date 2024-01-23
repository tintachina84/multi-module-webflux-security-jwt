DROP TABLE IF EXISTS USERS CASCADE;

CREATE TABLE IF NOT EXISTS USERS (
    id SERIAL NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_at TIMESTAMP,
    PRIMARY KEY (id)
);

INSERT INTO USERS (username, password, email, roles, active)
VALUES ('john@doe.com', 'johnspassword', 'john@doe.com', 'Admin,User', true);

INSERT INTO USERS (username, password, email, roles, active)
VALUES ('jane@smith.com', 'janespassword', 'jane@smith.com', 'User', true);

INSERT INTO USERS (username, password, email, roles, active)
VALUES ('alice@johnson.com', 'alicespassword', 'alice@johnson.com', 'User', false);