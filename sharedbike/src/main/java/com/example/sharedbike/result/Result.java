package com.example.sharedbike.result;


import lombok.Data;

public class Result<t> {
    //响应码
    private int code;
    //信息
    private String message;
    //返回数据
    private t data;
    //调用时间
    private long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <t> Result<t> success(t data) {
        Result<t> resultData = new Result<>();
        resultData.setCode(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <t> Result<t> fail(t data) {
        Result<t> resultData = new Result<>();
        resultData.setCode(ReturnCode.RC999.getCode());
        resultData.setMessage(ReturnCode.RC999.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <t> Result<t> loginFail(t data) {
        Result<t> resultData = new Result<>();
        resultData.setCode(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getCode());
        resultData.setMessage(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public t getData() {
        return data;
    }

    public void setData(t data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public enum ReturnCode {
        /**操作成功**/
        RC100(100,"操作成功"),
        /**操作失败**/
        RC999(999,"操作失败"),
        /**access_denied**/
        RC403(403,"无访问权限,请联系管理员授予权限"),
        /**access_denied**/
        RC401(401,"匿名用户访问无权限资源时的异常"),
        /**服务异常**/
        RC500(500,"系统异常，请稍后重试"),

        INVALID_TOKEN(2001,"访问令牌不合法"),
        ACCESS_DENIED(2003,"没有权限访问该资源"),
        CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),
        USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),
        UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");

        /**自定义状态码**/
        private final int code;
        /**自定义描述**/
        private final String message;

        ReturnCode(int code, String message){
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
