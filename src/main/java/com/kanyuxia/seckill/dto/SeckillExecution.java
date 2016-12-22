package com.kanyuxia.seckill.dto;

import com.kanyuxia.seckill.entity.SuccessKilled;
import com.kanyuxia.seckill.enums.SeckillStateEnum;

/**
 * 封装秒杀执行后的结果
 * @author kanyuxia
 *
 */
public class SeckillExecution {
	// 商品id
	private long seckillId;
	// 秒杀状态标识
	private int state;
	// 秒杀状态说明
	private String stateInfo;
	// 秒杀成功对象
	private SuccessKilled successKilled;
	// 秒杀成功
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum,
			SuccessKilled successKilled) {
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.successKilled = successKilled;
	}
	// 秒杀失败
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}
	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}
	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state
				+ ", stateInfo=" + stateInfo + ", successKilled="
				+ successKilled + "]";
	}

}
