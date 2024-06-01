# SharedBikeDatabaseSystem

## **1.后端数据库（Mysql）部分设计：**

### **实体和属性**

**管理员（Admin）**

*privileges权限（三类）*

*adminid（管理员ID）*

*username（用户名）*

*gender（性别）*

*password（密码）*

*phone_number（手机号）*

*avatar（头像）*

*生日*

**骑行者（Rider）**

*userid（骑行者ID）*

*username（用户名）*

*gender（性别）*

*password（密码）*

*phone_number（手机号）*

*avatar（头像）*

*生日*

**单车（Bike）**

*bikeid（单车ID）*

*brand（品牌）*

*release_date（投放时间）*

*warranty_period（保修时长）*

*status（状态：是否关锁损坏）*

**骑行记录（RideRecord）**

*orderid（订单ID）*

*bikeid（单车ID）*

*userid（骑行者ID）*

*start_time（开始时间）*

*start_location_x（起点X坐标）*

*start_location_y（起点Y坐标）*

*end_time（结束时间）*

*end_location_x（终点X坐标）*

*end_location_y（终点Y坐标）*

*track（轨迹）*

**禁停区（NoParkingZone）**

*zoneid（区域ID）*

*name（区域名称）*

*location（位置）*

### **关系**

管理员（Admin）可以增删改查骑行者（Rider）

管理员（Admin）可以增删改查单车（Bike）

管理员（Admin）可以增删改查骑行记录（RideRecord）

管理员（Admin）可以增删改查禁停区（NoParkingZone）

骑行者（Rider）与骑行记录（RideRecord）之间是多对多关系，通过订单（RideRecord）来表示

骑行记录（RideRecord） 是典型的弱实体，因为它依赖于 Bike 和 Rider。它的主要标识 orderid 仅在关联的单车和骑行者存在时才有意义。

### **ER**图：（略）

### **MySQL设计：**

构建这五个表，并且定义一个Trigger，当navicat将Excel数据插入到**RideRecord**表中时，检查**bike**和**rider**表中是否存在对应的**id**，如果不存在就向其添加数据（除id外的数据进行随机初始化）。**NoParkingZone和Admin表不做初始化，之后联系springboot，再做输入定义。**

**代码见查询文件initTable.sql（我转储的本地数据库为backend.sql，可供参考）**

**有什么需要修改的请及时沟通。**

