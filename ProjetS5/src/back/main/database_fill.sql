INSERT INTO dbGroup VALUES (90, 'testGroup1', 0),
                           (91, 'testGroup2', 1),
                           (92, 'testGroup3', 0),
                           (93, 'testGroup4', 1);

INSERT INTO dbUser VALUES  (10, 'Jean31', 'Dupont', 'Jean', 0),
                           (11, 'Pierre31', 'Dubois', 'Pierre', 0),
                           (12, 'Dylan31', 'Dulin', 'Dylan', 1),
                           (13, 'Vincent31', 'Depierre', 'Vincent', 0);

INSERT INTO dbLinkUserGroup VALUES (10, 91),
                                   (11, 91),
                                   (12, 93),
                                   (13, 92);

INSERT INTO dbMessage VALUES (20, 13, 'testMessage1', '1998-08-14'),
                             (21, 11, 'testMessage2', '2012-12-12');

INSERT INTO dbThread VALUES (30, 'testThread', 91, 20, 13);

INSERT INTO dbLinkMessageThread VALUES (20, 30),
                                       (21, 30);

INSERT INTO dbLinkUserMessage VALUES (10, 20, 'NOT_SEEN'),
                                     (11, 20, 'SEEN'),
                                     (13, 20, 'SEEN'),
                                     (10, 21, 'NOT_SEEN'),
                                     (11, 21, 'SEEN'),
                                     (13, 21, 'NOT_SEEN');