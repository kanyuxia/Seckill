package com.kanyuxia.seckill.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kanyuxia.seckill.entity.Seckill;

public interface SeckillDao {
	
	/**
	 * 通过商品id和秒杀时间把库存商品数量减一
	 * @param seckillId 库存商品id
	 * @param killTime 秒杀时间
	 * @return 更新影响行数
	 */
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
	
	/**
	 * 通过商品id查询该商品的信息
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量查询秒杀商品列表
	 * @param offet 偏移量
	 * @param limit 取出行数
	 * @return
	 */
	List<Seckill> queryAll(@Param("offet") int offet,@Param("limit") int limit);
}
