package com.arturoeanton.fxemmy.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionGroovyRunnerChainValidation extends Exception{
    private String msg;
    public ExceptionGroovyRunnerChainValidation(String msg){
        this.msg = msg;
    }
}
