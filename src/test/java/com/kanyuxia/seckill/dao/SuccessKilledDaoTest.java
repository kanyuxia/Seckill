package com.kanyuxia.seckill.dao;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kanyuxia.seckill.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccesskilled() {
		long id = 1000L;
		long userPhone = 184836203687L;
		int updateColumn = successKilledDao.insertSuccesskilled(id,userPhone);
		System.out.println(updateColumn);
	}
	
	@Test
	public void queryByIdWithSeckill() {
		long id = 1000L;
		List<SuccessKilled> sList = successKilledDao.queryByIdWithSeckill(id);
		if(sList!=null){
			for(SuccessKilled successKilled:sList){
				System.out.println(successKilled);
			}
		}
	}
	
	@Test
	public void queryByWithSeckill() {
		long id = 1000L;
		long userPhone = 184836203687L;
		SuccessKilled successKilled = successKilledDao.queryWithSeckill(id,userPhone);
		if(successKilled!=null){
			System.out.println(successKilled);
		}
		
	}

}
