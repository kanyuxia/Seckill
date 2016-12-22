package com.kanyuxia.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.kanyuxia.seckill.dao.SeckillDao;
import com.kanyuxia.seckill.dao.SuccessKilledDao;
import com.kanyuxia.seckill.dto.Exposer;
import com.kanyuxia.seckill.dto.SeckillExecution;
import com.kanyuxia.seckill.entity.Seckill;
import com.kanyuxia.seckill.entity.SuccessKilled;
import com.kanyuxia.seckill.enums.SeckillStateEnum;
import com.kanyuxia.seckill.exception.RepeatKillException;
import com.kanyuxia.seckill.exception.SeckillCloseException;
import com.kanyuxia.seckill.exception.SeckillException;
import com.kanyuxia.seckill.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// md5盐值字符串，用户混淆md5
	private final String slat = "dsfjskdf1454$#%&*(#%jdfifkd";
	
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if(seckill == null){
			// 不能进行秒杀且商品id错误
			return new Exposer(false, seckillId);
		}
		// 系统时间
		long nowTime = System.currentTimeMillis();
		long startTime = seckill.getStartTime().getTime();
		long endTime = seckill.getEndTime().getTime();
		if(nowTime<startTime||nowTime>endTime){
			// 不能进行秒杀
			return new Exposer(false, seckillId, nowTime, startTime, endTime);
		}
		// 转换特定字符串的过程，不可逆。
		String md5 = getMD5(seckillId);
		// 可以进行秒杀
		return new Exposer(true, md5, seckillId, startTime, endTime);
	}
	@Transactional
	/**
	 * 使用注解控制事务方法的优点：
	 * 1.开发团队达成一致的约定，明确标注事务方法的编程风格。
	 * 2.保证事务方法的执行时间尽可能的短,不要穿插其他的耗时操作,如:网络操作RPC、HTTP请求(需要剥离到事务方法以外)。
	 * 3.并不是所有的数据库操作的方法都需要事务,如:只有一条修改操作、只读操作等。
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		if(!md5.equals(getMD5(seckillId))||md5 == null){
			throw new SeckillException("seckill data rewrite");
		}
		// 执行秒杀逻辑：减库存 + 记录购买行为
		try {
			// 减库存
			Date nowDate = new Date();
			int updateCount = seckillDao.reduceNumber(seckillId, nowDate);
			if(updateCount!=1){
				// 更新记录错误，秒杀结束。
				throw new SeckillCloseException("seckill is closed");
			}
			// 记录购买行为
			if(successKilledDao.insertSuccesskilled(seckillId, userPhone)!=1){
				// 重复秒杀错误!
				throw new RepeatKillException("seckill repeated");
			}
			// 秒杀成功
			SuccessKilled successKilled = successKilledDao.queryWithSeckill(seckillId, userPhone);
			return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
		}catch(SeckillCloseException e1){
			throw e1;
		}
		catch(RepeatKillException e2){
			throw e2;
		}
		catch(SeckillException e3){
			throw e3;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
			// 编译期异常转换成运行期异常
			throw new SeckillException("seckill inner error:"+e.getMessage());
		}
	}
	
	private String getMD5(long seckillId){
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
