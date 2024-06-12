package com.example.sharedbike.service;

import com.example.sharedbike.domin.BaseResponse;
import com.example.sharedbike.result.LoginException;
import com.example.sharedbike.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 处理通用异常。
     * @param e the exception
     * @return Result<String>
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleGeneralException(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return Result.fail("Fail!");
    }

    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public Object handleShiroException(ShiroException e) {
        BaseResponse<Object> ret = new BaseResponse<Object>();
        ret.setErrCode(401);
        ret.setMsg(e.getMessage());
        return ret;
    }


//    /**
//     * 处理自定义业务异常。
//     * @param e the BusinessException
//     * @return Result<String>
//     */
//    @ExceptionHandler(BusinessException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Result<String> handleBusinessException(BusinessException e) {
//        log.warn("业务异常 ex={}", e.getMessage(), e);
//        return Result.fail("Fail!");
//    }

//    /**
//     * 处理参数验证异常。
//     * @param e the ConstraintViolationException
//     * @return ResultData<String>
//     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResultData<String> handleConstraintViolationException(ConstraintViolationException e) {
//        log.warn("参数验证失败 ex={}", e.getMessage(), e);
//        return ResultData.fail(ReturnCode.RC400.getCode(), "参数验证失败: " + e.getMessage());
//    }

//    /**
//     * 处理方法参数类型不匹配异常。
//     * @param e the MethodArgumentTypeMismatchException
//     * @return ResultData<String>
//     */
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResultData<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
//        log.warn("方法参数类型不匹配 ex={}", e.getMessage(), e);
//        return ResultData.fail(ReturnCode.RC400.getCode(), "参数类型错误: " + e.getName());
//    }

//    /**
//     * 处理HTTP请求方法不支持异常。
//     * @param e the HttpRequestMethodNotSupportedException
//     * @return ResultData<String>
//     */
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    public ResultData<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//        log.warn("请求方法不支持 ex={}", e.getMessage(), e);
//        return ResultData.fail(ReturnCode.RC405.getCode(), "不支持的请求方法: " + e.getMethod());
//    }
}

