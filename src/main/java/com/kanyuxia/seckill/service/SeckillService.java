package com.kanyuxia.seckill.service;

import java.util.List;

import com.kanyuxia.seckill.dto.Exposer;
import com.kanyuxia.seckill.dto.SeckillExecution;
import com.kanyuxia.seckill.entity.Seckill;
import com.kanyuxia.seckill.exception.RepeatKillException;
import com.kanyuxia.seckill.exception.SeckillCloseException;
import com.kanyuxia.seckill.exception.SeckillException;

/**
 * 业务接口，站在“使用者”角度设计接口
 * 三个方面考虑：1.方法定义粒度，参数，返回类型(return 类型/异常)
 * @author kanyuxia
 *
 */
public interface SeckillService {
	
	/**
	 * 查询全部秒杀信息。
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 通过商品id查询该商品的秒杀信息。
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 通过商品id查询该商品秒杀接口地址。(也就是说查询是否能够秒杀)
	 * 秒杀开启时输出秒杀地址，否则输出系统时间和秒杀时间。
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 通过验证秒杀地址(md5),执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5 秒杀地址
	 * @return SeckillExecution
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
		throws SeckillException,RepeatKillException,SeckillCloseException;
}
