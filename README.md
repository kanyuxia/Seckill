# 秒杀系统Seckill
***
## 写在前面的话
***
首先感谢[慕课网](http://www.imooc.com/)分享的[教学视频](http://www.imooc.com/course/programdetail/pid/59)。老师讲的挺好的，欢迎大家去观看。
## 开发环境
***
Eclipse+Maven+SSM框架
## 相关技术
***
1.数据库：mysql
2.持久层：Mybatis
3.MVC框架：SpringMVC
4.容器、事务管理：Spring
5.前端：Bootstrap、jQuery
## 相关知识点
***
1.Mybatis的使用以及与Spring整合
2.Spring的IOC与声明式事务控制
3.业务逻辑接口的设计与实现
4.SpringMVC的使用以及URL的设计(Resful风格)
5.前端逻辑流程的分析
## 知识点的细解
***
## 相关问题及解决方案
***
## 相关代码的细解
***
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
