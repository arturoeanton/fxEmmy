package com.arturoeanton.fxemmy.controller;

import com.arturoeanton.fxemmy.singleton.GroovyRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
public class RunnerController {

    @Value("${fxemmy.default.chaindefault}")
    private String chaindefault;

    @RequestMapping("/run/{name}")
    public ResponseEntity<Object> run(@PathVariable String name,   HttpServletRequest req) {
        try {
            String chain = chaindefault;
            chain += String.format("+?%s",req.getMethod().toLowerCase());
            Object obj = GroovyRunner.getInstance().run("scripts" + File.separator+ name,  chain,  req.getMethod().toLowerCase(), req);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}