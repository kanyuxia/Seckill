package com.kanyuxia.seckill.exception;

/**
 * 商品秒杀异常
 * @author kanyuxia
 *
 */
@SuppressWarnings("serial")
public class SeckillException extends RuntimeException{

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillException(String message) {
		super(message);
	}
	
}
