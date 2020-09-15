package com.arturoeanton.fxemmy.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChainCut extends Exception{
    private Object data;
    public ChainCut(Object data){
        this.data = data;
    }
}
