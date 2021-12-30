CREATE TABLE dbGroup (
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE dbUser (
    id INT PRIMARY KEY NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    isAdmin BIT NOT NULL
);

CREATE TABLE dbLinkUserGroup (
    userId INT NOT NULL,
    groupId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES dbUser(id),
    FOREIGN KEY (groupId) REFERENCES dbGroup(id),
    PRIMARY KEY (userId, groupId)
);

CREATE TABLE dbMessage (
    id INT PRIMARY KEY NOT NULL,
    authorId INT NOT NULL,
    text VARCHAR(3000) NOT NULL,
    date BIGINT NOT NULL,
    FOREIGN KEY (authorId) REFERENCES dbUser(id)
);

CREATE TABLE dbLinkUserMessage (
    userId INT NOT NULL,
    messageId INT NOT NULL,
    status VARCHAR(10),
    FOREIGN KEY (userId) REFERENCES dbUser(id),
    FOREIGN KEY (messageId) REFERENCES dbMessage(id),
    PRIMARY KEY (userId, messageId)
);

CREATE TABLE dbThread (
    id INT PRIMARY KEY NOT NULL,
    title VARCHAR(300) NOT NULL,
    groupId INT NOT NULL,
    authorId INT NOT NULL,
    FOREIGN KEY (authorId) REFERENCES dbUser(id),
    FOREIGN KEY (groupId) REFERENCES dbGroup(id)
);

CREATE TABLE dbLinkMessageThread (
     messageId INT NOT NULL,
     threadId INT NOT NULL,
     FOREIGN KEY (messageId) REFERENCES dbMessage(id),
     FOREIGN KEY (threadId) REFERENCES dbThread(id),
     PRIMARY KEY (messageId, threadId)
);

CREATE TABLE dbConnectionToken (
    id INT PRIMARY KEY NOT NULL,
    userId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES dbUser(id)
);