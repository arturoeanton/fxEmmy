package com.arturoeanton.fxemmy.component;

import org.springframework.stereotype.Component;

@Component
public class ComponentTest {

        public String p(String str){
            System.out.println(str);
            return str;
        }

}
