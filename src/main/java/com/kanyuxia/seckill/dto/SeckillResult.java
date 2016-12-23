package com.kanyuxia.seckill.dto;

/**
 * 封装json结果
 * @author kanyuxia
 *
 * @param <T> dto对象
 */
public class SeckillResult<T> {

	private boolean success;
	// dto对象
	private T data;
	// 错误信息
	private String error;
	
	// 失败
	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}
	// 成功
	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	
	
	
	

}
