-- 创建骑行者表
CREATE TABLE Rider (
    userid INT  PRIMARY KEY,
    username VARCHAR(50)  UNIQUE,
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
    
    -- 检查rider是否存在
    SELECT COUNT(*) INTO rider_count FROM Rider WHERE userid = NEW.userid;
    
    IF rider_count = 0 THEN
        -- 生成随机用户名
        SET @chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        SET @username = '';
        SET @length = 8;
        WHILE @length > 0 DO
            SET @username = CONCAT(@username, SUBSTRING(@chars, FLOOR(RAND() * LENGTH(@chars) + 1), 1));
            SET @length = @length - 1;
        END WHILE;

        -- 生成随机生日（1950年至今的随机日期）
        SET @start_date = '1950-01-01';
        SET @end_date = CURDATE();
        SET @random_days = FLOOR(RAND() * DATEDIFF(@end_date, @start_date));
        SET @birthday = DATE_ADD(@start_date, INTERVAL @random_days DAY);

        -- 插入到Rider表
        INSERT INTO Rider (userid, username, gender, password, phone_number, avatar, birthday)
        VALUES (NEW.userid, @username, 
                CASE WHEN RAND() < 0.5 THEN 'male' ELSE 'female' END, 
                SUBSTRING(MD5(RAND()) FROM 1 FOR 8), 
                FLOOR(RAND() * 10000000), 
                'default.jpg', 
                @birthday);
    END IF;
    -- 检查bike是否存在
    SELECT COUNT(*) INTO bike_count FROM Bike WHERE bikeid = NEW.bikeid;
    IF bike_count = 0 THEN
        -- 插入到Bike表
        INSERT INTO Bike (bikeid, brand, release_date, warranty_period, status)
        VALUES (NEW.bikeid, 'Hellobike', 
                DATE_ADD(NOW(), INTERVAL -FLOOR(RAND() * 365) DAY), 
                FLOOR(RAND() * 3) + 1, 
                CASE WHEN RAND() < 0.8 THEN 'available' ELSE 'locked' END);
    END IF;
END;
//

DELIMITER ;


