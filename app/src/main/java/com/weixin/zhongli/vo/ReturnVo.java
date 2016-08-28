package com.weixin.zhongli.vo;


import java.io.Serializable;

public class ReturnVo implements Serializable {

	private static final long serialVersionUID = -5191887923230961779L;

	private String code;
	private String msg;
	private String data;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
