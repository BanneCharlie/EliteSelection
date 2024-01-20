package org.example.exception;

import org.example.model.vo.common.Result;
import org.example.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody // 处理的数据以Json格式返回
    public Result error(){
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    // 自定义异常处理
    @ExceptionHandler(CustomLoginException.class)
    @ResponseBody
    public Result loginError(CustomLoginException exception){
        return Result.build(null, exception.getResultCode());
    }
}
