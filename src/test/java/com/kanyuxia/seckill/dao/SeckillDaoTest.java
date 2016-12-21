package com.kanyuxia.seckill.dao;


import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kanyuxia.seckill.entity.Seckill;
/**
 * 配置spring与junit整合，junit启动时加载springIOC容器（spring-test,junit）
 * @author rice
 *
 */
//junit启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration(value="classpath:spring/spring-dao.xml")
public class SeckillDaoTest {
	
	// 注入需要的类
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		Date killTime = new Date(System.currentTimeMillis());
		int updateColumn = seckillDao.reduceNumber(1000,killTime);
		System.out.println(updateColumn);
	}
	@Test
	public void testQueryById() {
		Seckill seckill = seckillDao.queryById(1001);
		if(seckill!=null){
			System.out.println(seckill);
		}
		
	}
	@Test
	public void testQueryAllr() {
		List<Seckill> seckills = seckillDao.queryAll(0,100);
		if(seckills!=null){
			for(Seckill seckill:seckills){
				System.out.println(seckill);
			}
		}
	}

}
