-- 创建骑行者表
CREATE TABLE Rider (
    userid INT  PRIMARY KEY,
    username VARCHAR(50) ,
    gender ENUM('male', 'female', 'other') ,
    password VARCHAR(255) ,
    phone_number VARCHAR(20),
    avatar VARCHAR(255),
    birthday DATE
);

-- 创建单车表
CREATE TABLE Bike (
    bikeid INT  PRIMARY KEY,
    brand VARCHAR(50) ,
    release_date DATE,
    warranty_period INT,
		location_x FLOAT,
		location_y FLOAT,
    status ENUM('available', 'locked', 'damaged') DEFAULT 'available'
);

-- 创建骑行记录表
CREATE TABLE RideRecord (
    orderid INT  PRIMARY KEY,
    bikeid INT,
    userid INT,
    start_time DATETIME,
    start_location_x FLOAT,
    start_location_y FLOAT,
    end_time DATETIME,
    end_location_x FLOAT,
    end_location_y FLOAT,
    track TEXT,
    FOREIGN KEY (bikeid) REFERENCES Bike(bikeid),
    FOREIGN KEY (userid) REFERENCES Rider(userid)
);

-- 创建禁停区表
CREATE TABLE NoParkingZone (
    zone_id INT  PRIMARY KEY,
    name VARCHAR(100) ,
    location GEOMETRY 
);
-- 创建管理员表
CREATE TABLE Admin (
    admin_id INT  PRIMARY KEY,
    privileges ENUM('read_only', 'data_modification', 'superuser') ,
    username VARCHAR(50)  UNIQUE,
    gender ENUM('male', 'female', 'other') ,
    password VARCHAR(255) ,
    phone_number VARCHAR(20),
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
    DECLARE start_date DATE;
    DECLARE end_date DATE;
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
        SET start_date = '1950-01-01';
        SET end_date = CURDATE();
        SET random_days = FLOOR(RAND() * DATEDIFF(end_date, start_date));
        SET birthday = DATE_ADD(start_date, INTERVAL random_days DAY);

        -- 插入到Rider表
        SET random_gender = RAND();
        INSERT INTO Rider (userid, username, gender, password, phone_number, avatar, birthday)
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
        INSERT INTO Bike (bikeid, brand, release_date, warranty_period,location_x ,
		location_y,  status)
        VALUES (NEW.bikeid, 'Hellobike', 
                DATE_ADD(NOW(), INTERVAL -FLOOR(RAND() * 365) DAY), 
                FLOOR(RAND() * 3) + 1, 
								NEW.end_location_x,
								NEW.end_location_y,
                CASE 
                    WHEN random_status < 1/3 THEN 'available' 
                    WHEN random_status < 2/3 THEN 'locked' 
                    ELSE 'damaged' 
                END);
    ELSE
        -- Update Bike location
        UPDATE Bike
        SET location_x = NEW.end_location_x, 
            location_y = NEW.end_location_y
        WHERE bikeid = NEW.bikeid;
    END IF;
END;
//

DELIMITER ;
