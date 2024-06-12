//package com.example.sharedbike.domin;
//
//import java.io.Serializable;
//
//public class BaseResponse<T> implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * 状态码
//	 */
//	private int errCode;
//
//	/**
//	 * 消息内容
//	 */
//	private String msg;
//
//	/**
//	 * 返回数据
//	 */
//	private T data;
//
//	public BaseResponse() {
//		super();
//	}
//
//	public BaseResponse(int errCode, String msg, T data) {
//		super();
//		this.errCode = errCode;
//		this.msg = msg;
//		this.data = data;
//	}
//
//	public int getErrCode() {
//		return errCode;
//	}
//
//	public void setErrCode(int errCode) {
//		this.errCode = errCode;
//	}
//
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//
//	public T getData() {
//		return data;
//	}
//
//	public void setData(T data) {
//		this.data = data;
//	}
//
//}
package com.example.sharedbike.domin;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	private int errCode;

	/**
	 * 消息内容
	 */
	private String msg;

	/**
	 * 返回数据
	 */
	private T data;

	/**
	 * 调用时间
	 */
	private long timestamp;

	public BaseResponse() {
		super();
		this.timestamp = System.currentTimeMillis();
	}

	public BaseResponse(int errCode, String msg, T data) {
		super();
		this.errCode = errCode;
		this.msg = msg;
		this.data = data;
		this.timestamp = System.currentTimeMillis();
	}

	public static <T> BaseResponse<T> success(T data) {
		BaseResponse<T> response = new BaseResponse<>();
		response.setErrCode(ReturnCode.RC100.getCode());
		response.setMsg(ReturnCode.RC100.getMessage());
		response.setData(data);
		return response;
	}

	public static <T> BaseResponse<T> fail(T data) {
		BaseResponse<T> response = new BaseResponse<>();
		response.setErrCode(ReturnCode.RC999.getCode());
		response.setMsg(ReturnCode.RC999.getMessage());
		response.setData(data);
		return response;
	}

	public static <T> BaseResponse<T> loginFail(T data) {
		BaseResponse<T> response = new BaseResponse<>();
		response.setErrCode(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getCode());
		response.setMsg(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
		response.setData(data);
		return response;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public enum ReturnCode {
		/** 操作成功 **/
		RC100(100, "操作成功"),
		/** 操作失败 **/
		RC999(999, "操作失败"),
		/** access_denied **/
		RC403(403, "无访问权限,请联系管理员授予权限"),
		/** access_denied **/
		RC401(401, "匿名用户访问无权限资源时的异常"),
		/** 服务异常 **/
		RC500(500, "系统异常，请稍后重试"),

		INVALID_TOKEN(2001, "访问令牌不合法"),
		ACCESS_DENIED(2003, "没有权限访问该资源"),
		CLIENT_AUTHENTICATION_FAILED(1001, "客户端认证失败"),
		USERNAME_OR_PASSWORD_ERROR(1002, "用户名或密码错误"),
		UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");

		/** 自定义状态码 **/
		private final int code;
		/** 自定义描述 **/
		private final String message;

		ReturnCode(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
