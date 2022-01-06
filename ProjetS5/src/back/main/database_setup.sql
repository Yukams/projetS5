CREATE TABLE IF NOT EXISTS dbGroup (
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE
);

INSERT IGNORE INTO dbGroup VALUES   (90, 'testGroup1'),
                                    (91, 'testGroup2'),
                                    (92, 'testGroup3'),
                                    (93, 'testGroup4');

CREATE TABLE IF NOT EXISTS dbUser (
    id INT PRIMARY KEY NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    isAdmin BIT NOT NULL
);

INSERT IGNORE INTO dbUser VALUES    (10, 'Jean31', 'Dupont', 'Jean', '123', false),
                                    (11, 'Pierre31', 'Dubois', 'Pierre', '123', false),
                                    (12, 'Dylan31', 'Dulin', 'Dylan', '123', false),
                                    (13, 'Vincent31', 'Depierre', 'Vincent', '123', false),
                                    (14, 'root', 'root', 'root', 'root', true);

CREATE TABLE IF NOT EXISTS dbLinkUserGroup (
    userId INT NOT NULL,
    groupId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES dbUser(id) ON DELETE CASCADE,
    FOREIGN KEY (groupId) REFERENCES dbGroup(id) ON DELETE CASCADE,
    PRIMARY KEY (userId, groupId)
);

INSERT IGNORE INTO dbLinkUserGroup VALUES   (10, 91),
                                            (11, 91),
                                            (11, 93),
                                            (12, 93),
                                            (13, 92);

CREATE TABLE IF NOT EXISTS dbMessage (
    id INT PRIMARY KEY NOT NULL,
    authorId INT NOT NULL,
    text VARCHAR(3000) NOT NULL,
    date BIGINT NOT NULL,
    FOREIGN KEY (authorId) REFERENCES dbUser(id) ON DELETE CASCADE
);

INSERT IGNORE INTO dbMessage VALUES (20, 13, 'testMessage1', '1640500000000'),
                                    (21, 11, 'testMessage2', '1640500000001'),
                                    (22, 11, 'testMessage3', '1640500000000');

CREATE TABLE IF NOT EXISTS dbLinkUserMessage (
    userId INT NOT NULL,
    messageId INT NOT NULL,
    status VARCHAR(10),
    FOREIGN KEY (userId) REFERENCES dbUser(id) ON DELETE CASCADE,
    FOREIGN KEY (messageId) REFERENCES dbMessage(id) ON DELETE CASCADE,
    PRIMARY KEY (userId, messageId)
);

INSERT IGNORE INTO dbLinkUserMessage VALUES (10, 20, 'NOT_SEEN'),
                                            (11, 20, 'SEEN'),
                                            (13, 20, 'SEEN'),
                                            (10, 21, 'NOT_SEEN'),
                                            (11, 21, 'SEEN'),
                                            (13, 21, 'NOT_SEEN'),
                                            (11, 22, 'SEEN'),
                                            (13, 22, 'NOT_SEEN');

CREATE TABLE IF NOT EXISTS dbThread (
    id INT PRIMARY KEY NOT NULL,
    title VARCHAR(300) NOT NULL,
    groupId INT NOT NULL,
    authorId INT NOT NULL,
    FOREIGN KEY (authorId) REFERENCES dbUser(id) ON DELETE CASCADE,
    FOREIGN KEY (groupId) REFERENCES dbGroup(id) ON DELETE CASCADE
);

INSERT IGNORE INTO dbThread VALUES  (30, 'testThread1', 91, 13),
                                    (31, 'testThread2', 92, 11);

CREATE TABLE IF NOT EXISTS dbLinkMessageThread (
     messageId INT NOT NULL,
     threadId INT NOT NULL,
     FOREIGN KEY (messageId) REFERENCES dbMessage(id) ON DELETE CASCADE,
     FOREIGN KEY (threadId) REFERENCES dbThread(id) ON DELETE CASCADE,
     PRIMARY KEY (messageId, threadId)
);

INSERT IGNORE INTO dbLinkMessageThread VALUES   (20, 30),
                                                (21, 30),
                                                (22, 31);

CREATE TABLE IF NOT EXISTS dbConnectionToken (
    id INT PRIMARY KEY NOT NULL,
    userId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES dbUser(id) ON DELETE CASCADE
);