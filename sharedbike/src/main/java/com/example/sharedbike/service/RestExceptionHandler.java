package com.example.sharedbike.service;

import com.example.sharedbike.domin.BaseResponse;
import com.example.sharedbike.result.LoginException;
//import com.example.sharedbike.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public BaseResponse<String> handleGeneralException(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return BaseResponse.fail("Fail!");
    }

    /**
     * 处理登录失败异常。
     * @param e the LoginException
     * @return Result<String>
     */
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<String> handleBusinessException(LoginException e) {
        log.warn("业务异常 ex={}", e.getMessage(), e);
        return BaseResponse.loginFail("Fail!");
    }
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<BaseResponse<String>> handleDuplicateKeyException(DuplicateKeyException ex) {
        BaseResponse<String> errorResponse = new BaseResponse<>(BaseResponse.ReturnCode.RC999.getCode(), "Duplicate Key: The provided key already exists in the database.", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
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

