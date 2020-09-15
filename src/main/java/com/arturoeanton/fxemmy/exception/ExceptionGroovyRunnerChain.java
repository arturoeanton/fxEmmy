package com.arturoeanton.fxemmy.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionGroovyRunnerChain extends Exception{
    private String msg;
    public ExceptionGroovyRunnerChain(String msg){
        this.msg = msg;
    }
}
