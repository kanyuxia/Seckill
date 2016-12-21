package com.kanyuxia.seckill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kanyuxia.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	
	/**
	 * 插入成功秒杀明细，可过滤重复(userPhone,seckillId为联合主键)
	 * @param seckillId
	 * @param userPhone
	 * @return 插入影响行数
	 */
	int insertSuccesskilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	/**
	 * 通过商品id查询订单，这些订单都携带秒杀对象实体
	 * @param seckillId
	 * @return
	 */
	List<SuccessKilled> queryByIdWithSeckill(long seckillId);
	
	/**
	 * 根据商品id和用户电话号码查询该订单，并携带秒杀对象实体
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	SuccessKilled queryWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	
	
	
	
	
	/**
	 * 修改成功秒杀订单的状态标识
	 * @param seckillId
	 * @param userPhone
	 */
	int updateSuccesskilledState(long seckillId,long userPhone);
	
	
	
	/**
	 * 根据商品id查询成功秒杀该商品的订单
	 * @param seckillId
	 * @return
	 */
	List<SuccessKilled> queryBySeckillId(long seckillId);
	
	/**
	 * 根据偏移量查询所有成功秒杀订单
	 * @param offet
	 * @param limit
	 * @return
	 */
	List<SuccessKilled> queryAll(int offet,int limit);
	
	/**
	 * 根据商品id和订单状态查询该商品的该状态下的订单
	 * @param seckillId
	 * @param state
	 * @return
	 */
	List<SuccessKilled> queryBySeckillIdAndState(long seckillId,short state);
	
	
	
}
