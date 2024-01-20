package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.vo.common.ResultCodeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomLoginException extends RuntimeException {
    private Integer code;
    private String message;
    private ResultCodeEnum resultCode;

    public CustomLoginException(ResultCodeEnum resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.resultCode = resultCode;
    }
}
