package com.kanyuxia.seckill.exception;

/**
 * 重复秒杀异常(运行期异常)
 * @author kanyuxia
 *
 */
@SuppressWarnings("serial")
public class RepeatKillException extends SeckillException{

	public RepeatKillException(String message) {
		super(message);
	}
	
	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

}
