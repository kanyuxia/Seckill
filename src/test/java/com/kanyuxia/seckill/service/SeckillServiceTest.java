package com.kanyuxia.seckill.service;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import com.kanyuxia.seckill.dto.Exposer;
import com.kanyuxia.seckill.dto.SeckillExecution;
import com.kanyuxia.seckill.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:spring/*.xml")
public class SeckillServiceTest {
	private final String slat = "dsfjskdf1454$#%&*(#%jdfifkd";
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills = seckillService.getSeckillList();
		logger.info("list={}",seckills);
	}
	
	@Test
	public void GetById() {
		Seckill seckill = seckillService.getById(1000);
		logger.info("seckill={}",seckill);
	}
	
	@Test
	public void testExportSeckillUrl() {
		Exposer exposer = null;
		try {
			exposer = seckillService.exportSeckillUrl(1000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		logger.info("exposer={}",exposer);
	}
	
	@Test
	@Rollback(false)
	public void testExecuteSeckill() {
		SeckillExecution seckillExecution = null;
		try {
			long userPhone = 1848362036899L;
			seckillExecution = seckillService.executeSeckill(1000, userPhone,getMD5(1000));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		logger.info("seckillExecution={}",seckillExecution);
	}
	
	private String getMD5(long seckillId){
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
