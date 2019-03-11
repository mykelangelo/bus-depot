INSERT IGNORE INTO depot_user
  (email, user_type, password_hash)
VALUES ('human.admin@bus-depot.com', 'DEPOT_ADMIN', '$2a$10$BTrA6YQEY1JiSh5rLPheq.U89JgbCtXcr06ignTdA93bg5mp9OHLW'),
       -- password: openThePodBayDoorHal
       ('HAL9000@bus-depot.com', 'DEPOT_ADMIN', '$2a$10$WCSo6X64uMsx7rp4edWuz.p9CKd/WfU/APMXtMk.1g/hnBUEAqi2C'),
       -- password: ImSorryDaveImAfraidICantDoThat
       ('james.bond@bus-depot.com', 'BUS_DRIVER', '$2a$10$tXVcKkwVDnRm0lJWxFPsjOJuqazvXHShc9MkuXy0IJgAypIWaMo7u'),
       -- password: fromRussiaWithLove
       ('Lock.Picketts@bus-depot.com', 'BUS_DRIVER', '$2a$10$o05FNlrJt9TapZVinu8gy.Vm6H33UN5xSvbt6w.gHRp9LTzCHbczK'),
       -- password: ChaseFarmHospitalMetroLineWoodGreenLondon
       ('james.gordon@bus-depot.com', 'BUS_DRIVER', '$2a$10$1cgqoTmLi.wbLJEDpeiPH.AIRJDrOn.m0H9FFVAQAVPrJboELhZ/G'),
       -- password: itsJeeSeePeeDee
       ('penguin@bus-depot.com', 'BUS_DRIVER', '$2a$10$.aSHj7NLTgIp8SNerqqH9evYNyquS84MgT2DB9HDcXpCf0lxcoRKu'),
       -- password: OswaldCobblePotTheMayor
       ('bruce.wayne@bus-depot.com', 'BUS_DRIVER', '$2a$10$gUg0TbxdGYGB5afi/tIvvuUpHOhZvSOf4MW2vl2G7aAKh2O1WHLHW'),
       -- password: isDarkKnightRisingIfBatmanIsInTheWoodsAndNoOneSeesHim
       ('abe.lincoln@bus-depot.com', 'BUS_DRIVER', '$2a$10$csC/FaxmZ1Oi0SywXKGnK.rHfncT5rsFAN49RG83I8Kx6LtGENHaS'),
       -- password: MyQuestionIsWhetherYouAreContentWithYourFailure
       ('mykle.shchur@bus-depot.com', 'BUS_DRIVER', '$2a$10$nh6QbeTT0S4BkkGUus7f9u5YNhQI9F/xK9uG7JmtZSon10bSxxnuu'),
       -- password: SlavaIsuTaVyoDoNovyn
       ('donald.trump@bus-depot.com', 'BUS_DRIVER', '$2a$10$pLqrxqwXOPvAQCwx2T2WZei9vU/8gVNL8h0wc8D07z1L4aURJBmSW');
-- password: makeAmericaGreatAgainAgain


INSERT IGNORE INTO route (route_name)
VALUES ('W8'),
       ('GR8'),
       ('M8'),
       ('B8'),
       ('L8'),
       ('007');

INSERT IGNORE INTO bus (bus_serial, route_name)
VALUES ('MW6060GC', 'W8'),
       ('GC0666PD', 'W8'),
       ('OO0000OO', 'GR8'),
       ('PN0001GN', 'B8'),
       ('BM0000BM', 'B8'),
       ('AL4141AL', 'L8'),
       ('JB0007JB', '007');

INSERT IGNORE INTO bus_driver (user_email, bus_serial, aware_of_assignment)
VALUES ('james.bond@bus-depot.com', 'JB0007JB', TRUE),
       ('Lock.Picketts@bus-depot.com', 'MW6060GC', FALSE),
       ('james.gordon@bus-depot.com', 'GC0666PD', TRUE),
       ('penguin@bus-depot.com', 'PN0001GN', TRUE),
       ('bruce.wayne@bus-depot.com', 'BM0000BM', TRUE),
       ('mykle.shchur@bus-depot.com', NULL, TRUE),
       ('donald.trump@bus-depot.com', NULL, FALSE),
       ('abe.lincoln@bus-depot.com', 'AL4141AL', FALSE);