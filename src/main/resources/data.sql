-- -----------------------------
-- USERS
-- -----------------------------
INSERT INTO users (name, email, password) VALUES
('Amit Sharma', 'amit1@example.com', 'pass123'),
('Rohit Verma', 'rohit@example.com', 'pass123'),
('Simran Kaur', 'simran@example.com', 'pass123'),
('Neha Singh', 'neha@example.com', 'pass123'),
('Arjun Mehta', 'arjun@example.com', 'pass123'),
('Driver One', 'driver1@example.com', 'pass123'),
('Driver Two', 'driver2@example.com', 'pass123'),
('Driver Three', 'driver3@example.com', 'pass123'),
('Driver Four', 'driver4@example.com', 'pass123'),
('Driver Five', 'driver5@example.com', 'pass123'),
('Admin User', 'admin1@example.com', 'pass123'),
('Driver Six', 'driver6@example.com', 'pass123'),
('Driver Seven', 'driver7@example.com', 'pass123'),
('Driver Eight', 'driver8@example.com', 'pass123'),
('Driver Nine', 'driver9@example.com', 'pass123'),
('Driver Ten', 'driver10@example.com', 'pass123'),
('Rider Six', 'rider6@example.com', 'pass123'),
('Rider Seven', 'rider7@example.com', 'pass123'),
('Rider Eight', 'rider8@example.com', 'pass123'),
('Rider Nine', 'rider9@example.com', 'pass123');

-- -----------------------------
-- USER ROLES
-- -----------------------------
INSERT INTO users_roles (users_id, roles) VALUES
(1, 'RIDER'),
(2, 'RIDER'),
(3, 'RIDER'),
(4, 'RIDER'),
(5, 'RIDER'),
(6, 'DRIVER'),
(7, 'DRIVER'),
(8, 'DRIVER'),
(9, 'DRIVER'),
(10, 'DRIVER'),
(12, 'DRIVER'),
(13, 'DRIVER'),
(14, 'DRIVER'),
(15, 'DRIVER'),
(16, 'DRIVER'),
(11, 'ADMIN'),
(17, 'RIDER'),
(18, 'RIDER'),
(19, 'RIDER'),
(20, 'RIDER');

-- -----------------------------
-- RIDERS
-- -----------------------------
INSERT INTO rider (users_id, average_Rating , total_Rating) VALUES
(1, 4.8 ,1),
(2, 4.6,1),
(3, 4.9,1),
(4, 4.5,1),
(5, 4.4,1),
(17, 4.7,1),
(18, 4.5,1),
(19, 4.9,1),
(20, 4.6,1);

-- -----------------------------
-- DRIVERS
-- -----------------------------
INSERT INTO driver (users_id, average_Rating, lic_no, available, current_location , total_Rating) VALUES
(6, 4.7, 'DL01A1001', true,  ST_GeomFromText('POINT(77.2080 28.6145)', 4326) , 1),
(7, 4.8, 'DL01A1002', true,  ST_GeomFromText('POINT(77.2095 28.6140)', 4326) ,1),
(8, 4.6, 'DL01A1003', false, ST_GeomFromText('POINT(77.2102 28.6130)', 4326),1),
(9, 4.9, 'DL01A1004', true,  ST_GeomFromText('POINT(77.2110 28.6128)', 4326),1),
(10, 4.5, 'DL01A1005', true,  ST_GeomFromText('POINT(77.2088 28.6132)', 4326),1),
(12, 4.8, 'DL01A1006', true,  ST_GeomFromText('POINT(77.2075 28.6141)', 4326),1),
(13, 4.7, 'DL01A1007', true,  ST_GeomFromText('POINT(77.2099 28.6137)', 4326),1),
(14, 4.4, 'DL01A1008', false, ST_GeomFromText('POINT(77.2105 28.6150)', 4326),1),
(15, 4.6, 'DL01A1009', true,  ST_GeomFromText('POINT(77.2091 28.6125)', 4326),1),
(16, 4.5, 'DL01A1010', true,  ST_GeomFromText('POINT(77.2097 28.6133)', 4326),1);


INSERT INTO wallet (users_id, balance) VALUES
(1, 500),
(12, 500);