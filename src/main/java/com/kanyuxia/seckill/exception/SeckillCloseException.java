package com.kanyuxia.seckill.exception;

/**
 * 秒杀关闭异常
 * @author kanyuxia
 *
 */
@SuppressWarnings("serial")
public class SeckillCloseException extends SeckillException{

	public SeckillCloseException(String message) {
		super(message);
	}
	
	public SeckillCloseException(String message,Throwable cause) {
		super(message,cause);
	}

}
