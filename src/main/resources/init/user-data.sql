
-- INSERT 5 Testers
INSERT INTO USERS (id, name) VALUES (1, 'Roger');
INSERT INTO USERS (id, name) VALUES (2, 'Camille');
INSERT INTO USERS (id, name) VALUES (3, 'Julien');
INSERT INTO USERS (id, name) VALUES (4, 'Alpha');
INSERT INTO USERS (id, name) VALUES (5, 'Dubois');

-- INSERT 10 Device specifications
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (1, 'Super phone', 'yes', 'yes', 'yes');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (2, 'Ultra super tech', 'yes', 'yes', 'yes');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (3, 'Apple technology', 'yes', 'yes', 'yes');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (4, 'Water proof', 'yes', 'yes', 'yes');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (5, 'Unbreakable', 'yes', 'yes', 'no');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (6, 'Super AMOLED', 'yes', 'yes', 'yes');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (7, 'Super AMOLED Ultra', 'yes', 'yes', 'yes');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (8, 'Physical sim', 'yes', 'yes', 'yes');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (9, 'E sim', 'yes', 'yes', 'yes');
INSERT INTO device_specifications (id, technology, bands_2G, bands_3G, bands_4G) VALUES (10, 'Dual sim', 'yes', 'yes', 'no');

-- INSERT 10 Devices
INSERT INTO devices (id, SPECIFICATION_ID , name) VALUES (1, 1,'Samsung Galaxy S9');
INSERT INTO devices (id,SPECIFICATION_ID , name) VALUES (2, 2, '2x Samsung Galaxy S8');
INSERT INTO devices (id, SPECIFICATION_ID ,name) VALUES (3, 3, 'Motorola Nexus 6');
INSERT INTO devices (id,SPECIFICATION_ID , name) VALUES (4,4, 'Oneplus 9');
INSERT INTO devices (id,SPECIFICATION_ID , name) VALUES (5,5, 'Apple iPhone 13');
INSERT INTO devices (id, SPECIFICATION_ID , name) VALUES (6, 6,'Apple iPhone 12');
INSERT INTO devices (id,SPECIFICATION_ID , name) VALUES (7, 7, 'Apple iPhone 11');
INSERT INTO devices (id, SPECIFICATION_ID ,name) VALUES (8, 8, 'iPhone X');
INSERT INTO devices (id,SPECIFICATION_ID , name) VALUES (9,9, 'Samsung Galaxy 20 Ultra');
INSERT INTO devices (id,SPECIFICATION_ID , name) VALUES (10,10, 'Nokia 3310');


INSERT INTO BOOKINGS  (id,device_id , user_id,booked_at,returned_at, VERSION) VALUES (1,1, 1,'1986-04-08 12:30', null, 1);



