package com.kanyuxia.seckill.entity;

import java.sql.Date;

/**
 * 成功秒杀实体
 * 
 * @author rice
 * 
 */
public class SuccessKilled {
	private long seckillId;
	
	private long userPhone;
	
	// 订单状态标识：-1:无效 0:成功 1:已付款 2:已发货'
	private short state;
	
	private Date createTime;
	
	// 多对一
	private Seckill seckill;

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + ", userPhone="
				+ userPhone + ", state=" + state + ", createTime=" + createTime
				+ ", seckill=" + seckill + "]";
	}

	

}
