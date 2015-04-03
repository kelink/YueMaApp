package com.gdufs.gd.yuema.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 和服务端交互的JSON格式定义
 * 
 * @author Administrator
 * 
 */
public class TransferMessage implements Serializable {
	private static final long serialVersionUID = 7814300739991315981L;

	public TransferMessage() {
		super();
	}

	public TransferMessage(int code, String message, Map<?, ?> resultMap) {
		super();
	}

	private String code;
	private String message;
	private Map<String, ?> resultMap;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<?, ?> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, ?> resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public String toString() {
		return "TransferMessage [code=" + code + ", message=" + message
				+ ", resultMap=" + resultMap + "]";
	}

}
