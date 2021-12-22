CREATE TABLE dbGroup (
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(25) NOT NULL,
    groupType INT NOT NULL
);

CREATE TABLE dbUser (
    id INT PRIMARY KEY NOT NULL,
    username VARCHAR(25) NOT NULL,
    name VARCHAR(25) NOT NULL,
    surname VARCHAR(25) NOT NULL,
    groupType INT NOT NULL
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
    text VARCHAR(1000) NOT NULL,
    date DATE NOT NULL,
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
    title VARCHAR(100) NOT NULL,
    groupId INT NOT NULL,
    firstMessageId INT NOT NULL,
    authorId INT NOT NULL,
    FOREIGN KEY (authorId) REFERENCES dbUser(id),
    FOREIGN KEY (firstMessageId) REFERENCES dbMessage(id),
    FOREIGN KEY (groupId) REFERENCES dbGroup(id)
);

CREATE TABLE dbLinkMessageThread (
     messageId INT NOT NULL,
     threadId INT NOT NULL,
     FOREIGN KEY (messageId) REFERENCES dbMessage(id),
     FOREIGN KEY (threadId) REFERENCES dbThread(id),
     PRIMARY KEY (messageId, threadId)
);