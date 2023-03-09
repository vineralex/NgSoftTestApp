CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN NOT NULL,
    isActive BOOLEAN NOT NULL,
    password VARCHAR(255) NOT NULL,
    UNIQUE(email),
    UNIQUE(name)
);

CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status INT NOT NULL,
    assignee BIGINT NOT NULL
);

CREATE TABLE comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    taskId BIGINT NOT NULL,
    dateTime TIMESTAMP NOT NULL,
    comment VARCHAR(255) NOT NULL,
    FOREIGN KEY (userId) REFERENCES users (id),
    FOREIGN KEY (taskId) REFERENCES tasks (id)
);


INSERT INTO users (name, email, isAdmin, isActive, password)
VALUES ('user', 'user@admin.com', false, true, '{bcrypt}$2a$10$38fjA3MyGMTxV0xvvyXIZuYkZXMLUwg.z0T.nxuPQE.Q6/DxbrLX6');
INSERT INTO users (name, email, isAdmin, isActive, password)
VALUES ('admin', 'admin@admin.com', true, true, '{bcrypt}$2a$10$38fjA3MyGMTxV0xvvyXIZuYkZXMLUwg.z0T.nxuPQE.Q6/DxbrLX6');

INSERT INTO TASKS (TITLE, DESCRIPTION, STATUS, ASSIGNEE) VALUES ( 'Task 1', 'This is first task', 0, 1);
INSERT INTO TASKS (TITLE, DESCRIPTION, STATUS, ASSIGNEE) VALUES ( 'Task 2', 'This is second task', 0, 1);

INSERT INTO COMMENTS (USERID, TASKID, DATETIME, COMMENT) VALUES (1,1,'2023-01-01', 'Comment 1 to task 1');
INSERT INTO COMMENTS (USERID, TASKID, DATETIME, COMMENT) VALUES (1,1,'2023-01-01', 'Comment 2 to task 1');
INSERT INTO COMMENTS (USERID, TASKID, DATETIME, COMMENT) VALUES (1,1,'2023-01-01', 'Comment 3 to task 1');
INSERT INTO COMMENTS (USERID, TASKID, DATETIME, COMMENT) VALUES (1,2,'2023-01-01', 'Comment 1 to task 2');
INSERT INTO COMMENTS (USERID, TASKID, DATETIME, COMMENT) VALUES (1,2,'2023-01-01', 'Comment 2 to task 2');