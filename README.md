# 秒杀系统Seckill
今天(2018-03-09)整理项目文件时发现并重新整理。从写完该项目到现在快要1年多了，回过头来看，该项目算是自己写的第一个比较正式的项目。该项目是慕课网上的教学视频，个人觉得非常不错的视频，尤其是对于框架初学者。

注：下面的都是久远的历史版本所写，大概快一年了。
## 写在前面的话
首先感谢[慕课网](http://www.imooc.com/)分享的[教学视频](http://www.imooc.com/course/programdetail/pid/59)。老师讲的挺好的，欢迎大家去观看。
## 开发环境
Eclipse+Maven+SSM框架
## 相关技术
1.数据库：mysql
2.持久层：Mybatis
3.MVC框架：SpringMVC
4.容器、事务管理：Spring
5.前端：Bootstrap、jQuery
## 相关知识点
1.Mybatis的使用以及与Spring整合
2.Spring的IOC与声明式事务控制
3.业务逻辑接口的设计与实现
4.SpringMVC的使用以及URL的设计(Resful风格)
5.前端逻辑流程的分析
## 知识点的细解

### 把业务逻辑放到数据库中，进行业务操作
在数据库中来进行一些验收并发行为：如reduceNumber(seckillId,killTime) 在写插入数据库SQL时进行验收，直接在可以在返回结果中寻找答案。update seckill set number = number -1 where seckillId = #{seckillId} and create_time< killTIme and end_time>killTime and number>1. 这样的话，就可以直接在插入数据库中进行并发验证：如果成功-->return 1 如果失败-->return 0

### 该秒杀系统的前台交互设计(这里仅仅是慕课网提供秒杀系统的前台逻辑交互设计)
a.访问秒杀列表页面--->b.访问单个商品的秒杀详情页面进行-->c.查看浏览器中的cookie是否有"userPhone"(也就是说该用户是否登录)--> d1.输入电话号码(登录) / d2.从服务器获取系统时间，与该商品的秒杀时间(开始,结束)比较，页面显示计时交互页面(秒杀结束、秒杀倒计时、) --> e.可以开始秒杀后，从服务器获取该秒杀商品的真实URL地址(通过MD5加密)。-->f.执行秒杀，返回秒杀结果

### mybatis的优点
a.实现DAO接口(在mybatis中将DAO实现类一同称之为mapper)(mybatis通过xml文件中的<mapper namespace="com.kanyuxia.seckill.dao.SeckillDao">)自动把dao接口方法与相应的SQL方法对接起来,只需要配置mabatis与spring整合bean，不用再写实现类了(自动实现了，称之为mapper)。当然也可以使用Mybatis的API写实现类)。
b.通过配置文件将DAO接口类实现类(mapper)注入到Spring容器中，可以直接在service中使用DAO接口类。(注：Spring注入必须为实现类，而不能为接口。)
c.足够的灵活性：自己定制SQL，自由的传递参数，自动结果集可以赋值。
d.mybatis没有Hibernate实现的功能多，如：级联操作

### web并发优化
**前端**
a.暴露秒杀接口(到了时间点后才开始从服务器中访问加密后的秒杀接口地址)
b.防止重复按按钮
**服务器**
a.动静态数据分离：CDN缓存、Redis缓存
**数据库**
a.优化方向：减少行级锁的持有时间。
b.优化思路：把客户端的逻辑放到mysql服务器上，避免网络延迟以及GC带来的影响。
c.解决方案：
(1).定制SQL,如修改mysql源码，让其在update后就直接commit or collback。
(2).使用存储过程：把业务逻辑(事务控制)放到存储过程中去,也就是说让存储过程控制事务。

### 优化过程
a.在秒杀暴露接口地址使用Redis作为缓存优化时(通过seckillId取得整个Seckill),因为Redis作为Key-Value型数据库，所有key为seckillId，value为Seckill。因为Redis内部没有实现序列化操作，所有我们需要把Seckill实现序列化(get-->byte[]-->反序列化-->Object(Seckill))。如果我们Seckill实现Serializable接口,则我们使用的是jdk自动的序列化操作，性能不好。所以我们使用protostuff使用序列化。
b.在执行秒杀过程中，可以先执行Insert操作后执行Update操作。这样的话可以首先排除掉一些重复秒杀的操作。还有在uptate seckill时行级锁时间会变短一般(不懂)。
c.把秒杀行为放到mysql中去执行(写存储过程,由存储过程去控制事务)。

### 总结
DAO
a.数据库设计与实现(使用DDL语言)
b.MyBtis理解与使用技巧
c.MyBtis整合Spring技巧
Service
e.业务接口设计与封装
f.SpringIOC配置
g.Spring声明式事务使用与理解
Web
h.Restful接口运用
i:SpringMVC使用技巧
j:前端交互分析
L:Bootstrap与JS使用
并发优化
M:系统瓶颈点分析
N:事务、锁、网络延迟的理解
O:前端,CDN，缓存理解使用

## 相关代码的细解
SQL文件
``` sql
-- 数据库初始化化脚本

-- 创建数据库
CREATE DATABASE seckill DEFAULT CHARSET=utf8;
-- 使用数据库
use seckill;
set explicit_defaults_for_timestamp = 1;
-- 创建秒杀库存表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '商品库存',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`start_time` timestamp NOT NULL COMMENT '秒杀开始时间',
`end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'; 

-- 初始化数据
insert into 
	seckill(name,number,start_time,end_time)
values
('1000元秒杀iPhone6',100,'2016-12-20 00:00:00','2016-12-30 00:00:00'),
('500元秒杀iPad3',100,'2016-12-20 00:00:00','2016-12-30 00:00:00'),
('200元秒杀iPad mimi2',100,'2016-12-20 00:00:00','2016-12-30 00:00:00'),
('200元秒杀小米5',100,'2016-12-20 00:00:00','2016-12-30 00:00:00'),
('100元秒杀红米note2',100,'2016-12-20 00:00:00','2016-12-30 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标示: -1:无效 0:成功 1:已付款 2:已发货',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(seckill_id,user_phone),/*联合主键*/
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
```
