package com.kanyuxia.seckill.dto;


/**
 * 暴露秒杀地址DTO(data transmission object)
 * @author kanyuxia
 *
 */
public class Exposer {
	
	//是否开启秒杀
	private boolean exposed;
	
	// 加密后的秒杀接口URL地址
	private String md5;
	
	// 商品id
	private long seckillId;
	
	// 系统当前时间
	private long now;
	
	// 秒杀开启时间
	private long start;
	
	// 秒杀结束时间
	private long end;
	
	// 可以进行秒杀
	public Exposer(boolean exposed, String md5, long seckillId,long start,long end) {
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
		this.start = start;
		this.end = end;
	}
	
	// 不能进行秒杀
	public Exposer(boolean exposed, long seckillId, long now, long start,
			long end) {
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}
	
	// 不能进行秒杀且秒杀对象出错
	public Exposer(boolean exposed, long seckillId) {
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	public boolean isExposed() {
		return exposed;
	}

	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", seckillId="
				+ seckillId + ", now=" + now + ", start=" + start + ", end="
				+ end + "]";
	}
	
	
	
}
