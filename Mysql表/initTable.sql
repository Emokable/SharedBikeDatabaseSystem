-- 创建骑行者表
CREATE TABLE Rider (
    userid INT  PRIMARY KEY,
    username VARCHAR(50) ,
    gender ENUM('male', 'female', 'other') ,
    password VARCHAR(255) ,
    phonenumber VARCHAR(20),
    avatar VARCHAR(255),
    birthday DATE
);
CREATE INDEX idx_rider_userid ON Rider(userid);
CREATE INDEX idx_rider_birthday ON Rider(birthday);
-- 创建单车表
CREATE TABLE Bike (
    bikeid INT  PRIMARY KEY,
    brand VARCHAR(50) ,
    releasedate DATE,
    warrantyperiod INT,
		lastusetime DATETIME, 
		locationx FLOAT,
		locationy FLOAT,
    status ENUM('unlocked', 'locked', 'damaged') DEFAULT 'locked'
);
CREATE INDEX idx_bike_bikeid ON bike(bikeid);
CREATE INDEX idx_bike_lastusetime ON bike(lastusetime);
CREATE INDEX idx_bike_location ON bike(locationx,locationy);

-- 创建骑行记录表
CREATE TABLE RideRecord (
    orderid INT  PRIMARY KEY,
    bikeid INT,
    userid INT,
    starttime DATETIME,
    startlocationx FLOAT,
    startlocationy FLOAT,
    endtime DATETIME,
    endlocationx FLOAT,
    endlocationy FLOAT,
    track TEXT,
    FOREIGN KEY (bikeid) REFERENCES Bike(bikeid),
    FOREIGN KEY (userid) REFERENCES Rider(userid)
);
CREATE INDEX idx_RideRecord_orderid ON RideRecord(orderid);
CREATE INDEX idx_RideRecord_bikeid ON RideRecord(bikeid);
CREATE INDEX idx_RideRecord_userid ON RideRecord(userid);
CREATE INDEX idx_RideRecord_location ON RideRecord(startlocationx,startlocationy);
CREATE INDEX idx_RideRecord_time ON RideRecord(starttime,endtime);

-- 创建禁停区表
CREATE TABLE NoParkingZone (
    zoneid INT  PRIMARY KEY,
    name VARCHAR(100) ,
    location GEOMETRY 
);
-- 创建管理员表
CREATE TABLE Admin (
    adminid INT  PRIMARY KEY,
    privileges ENUM('read_only', 'data_modification', 'superuser') ,
    username VARCHAR(50)  UNIQUE,
    gender ENUM('male', 'female', 'other') ,
    password VARCHAR(255) ,
    phonenumber VARCHAR(20),
    avatar VARCHAR(255),
    birthday DATE
);
DELIMITER //

CREATE TRIGGER insert_ride_record_trigger
BEFORE INSERT ON RideRecord
FOR EACH ROW
BEGIN
    DECLARE rider_count INT;
    DECLARE bike_count INT;
    DECLARE chars CHAR(52);
    DECLARE username VARCHAR(8);
    DECLARE length INT;
    DECLARE startdate DATE;
    DECLARE enddate DATE;
    DECLARE random_days INT;
    DECLARE birthday DATE;
    DECLARE random_value DECIMAL(10, 8);
    DECLARE random_gender DECIMAL(10, 8);
    DECLARE random_status DECIMAL(10, 8);

    -- 检查rider是否存在
    SELECT COUNT(*) INTO rider_count FROM Rider WHERE userid = NEW.userid;
    IF rider_count = 0 THEN
        -- 生成随机用户名
        SET chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        SET username = '';
        SET length = 8;
        WHILE length > 0 DO
            SET username = CONCAT(username, SUBSTRING(chars, FLOOR(1 + RAND() * 52), 1));
            SET length = length - 1;
        END WHILE;
        -- 生成随机生日（1950年至今的随机日期）
        SET startdate = '1950-01-01';
        SET enddate = CURDATE();
        SET random_days = FLOOR(RAND() * DATEDIFF(enddate, startdate));
        SET birthday = DATE_ADD(startdate, INTERVAL random_days DAY);

        -- 插入到Rider表
        SET random_gender = RAND();
        INSERT INTO Rider (userid, username, gender, password, phonenumber, avatar, birthday)
        VALUES (NEW.userid, username, 
                CASE 
                    WHEN random_gender < 0.4 THEN 'male' 
                    WHEN random_gender < 0.6 THEN 'female' 
                    ELSE 'other' 
                END, 
                SUBSTRING(MD5(RAND()), 1, 8), 
                FLOOR(RAND() * 10000000), 
                'default.jpg', 
                birthday);
    END IF;

    -- 检查bike是否存在
    SELECT COUNT(*) INTO bike_count FROM Bike WHERE bikeid = NEW.bikeid;
    IF bike_count = 0 THEN
        -- 插入到Bike表
        SET random_status = RAND();
        INSERT INTO Bike (bikeid, brand, releasedate, warrantyperiod,lastusetime,locationx ,
		locationy,  status)
        VALUES (NEW.bikeid, 'Hellobike', 
                DATE_ADD('2016-07-31', INTERVAL -FLOOR(RAND() * 365) DAY), 
                FLOOR(RAND() * 3) + 1, 
								NEW.endtime,
								NEW.endlocationx,
								NEW.endlocationy,
                CASE 
                    WHEN random_status < 6/10 THEN 'locked' 
                    WHEN random_status < 9/10 THEN 'unlocked' 
                    ELSE 'damaged' 
                END);
    ELSE
        -- Update Bike location
        UPDATE Bike
        SET locationx = NEW.endlocationx, 
            locationy = NEW.endlocationy
        WHERE bikeid = NEW.bikeid;
    END IF;
END;
//

DELIMITER ;


select * from bike
where locationx like "121.51%" and locationy like "%31.30%" 

SELECT 
    DATE(starttime) AS date, 
    AVG(TIMESTAMPDIFF(MINUTE, starttime, endtime)) AS avg_ride_time 
FROM 
    riderecord 
GROUP BY 
    DATE(starttime);
		
SELECT 
    AVG(TIMESTAMPDIFF(MINUTE, starttime, endtime)) AS avg_ride_time 
FROM 
    riderecord 

