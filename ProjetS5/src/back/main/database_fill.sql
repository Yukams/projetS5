INSERT INTO dbGroup VALUES (90, 'testGroup1'),
                           (91, 'testGroup2'),
                           (92, 'testGroup3'),
                           (93, 'testGroup4');

INSERT INTO dbUser VALUES  (10, 'Jean31', 'Dupont', 'Jean'),
                           (11, 'Pierre31', 'Dubois', 'Pierre'),
                           (12, 'Dylan31', 'Dulin', 'Dylan'),
                           (13, 'Vincent31', 'Depierre', 'Vincent');

INSERT INTO dbLinkUserGroup VALUES (10, 91),
                                   (11, 91),
                                   (11, 93),
                                   (12, 93),
                                   (13, 92);

INSERT INTO dbMessage VALUES (20, 13, 'testMessage1', '1998-08-14'),
                             (21, 11, 'testMessage2', '2012-12-12'),
                             (22, 11, 'testMessage3', '2010-10-10');

INSERT INTO dbThread VALUES (30, 'testThread1', 91, 20, 13),
                            (31, 'testThread2', 92, 22, 11);

INSERT INTO dbLinkMessageThread VALUES (20, 30),
                                       (21, 30),
                                       (22, 31);

INSERT INTO dbLinkUserMessage VALUES (10, 20, 'NOT_SEEN'),
                                     (11, 20, 'SEEN'),
                                     (13, 20, 'SEEN'),
                                     (10, 21, 'NOT_SEEN'),
                                     (11, 21, 'SEEN'),
                                     (13, 21, 'NOT_SEEN'),
                                     (11, 22, 'SEEN'),
                                     (13, 22, 'NOT_SEEN');