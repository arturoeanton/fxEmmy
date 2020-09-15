package com.arturoeanton.fxemmy.singleton;

import com.arturoeanton.fxemmy.exception.ChainCut;
import com.arturoeanton.fxemmy.exception.ExceptionGroovyRunnerChain;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroovyRunner {
    private static GroovyRunner INSTANCE;
    private  GroovyClassLoader loader;

    private GroovyRunner() {
    }

    public static synchronized GroovyRunner getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GroovyRunner();
        }
        return INSTANCE;
    }

    public Object run(String directory, String methods, Object... param) throws Exception {
        var initMethodName = "init";
        loader = new GroovyClassLoader(this.getClass().getClassLoader());
        Set<File> files = Stream.of(new File(directory + File.separator + "components").listFiles())
                .filter(file -> file.getName().endsWith(".groovy"))
                .collect(Collectors.toSet());
        for (File f : files) {
            loader.parseClass(f);
        }
        Class mainClass = loader.parseClass(new File(directory, "main.groovy"));

        GroovyObject gobj = (GroovyObject) mainClass.getDeclaredConstructor().newInstance();

        Object element = null;
        if (existMethod(gobj, initMethodName)) {
            element = executeMethod(gobj, initMethodName, param);
        }
        if (existMethod(gobj, "chain")) {
            methods += "+" + gobj.invokeMethod("chain", null);
        }

        var listMethod = methods.split("\\+");
        var realChain = new ArrayList<String>();
        for (var method : listMethod) {
            var methodType = method.charAt(0);
            var methodName = method.substring(1);
            var exist = existMethod(gobj, methodName);
            if (methodType == '!') {
                if (!exist) {
                    throw new ExceptionGroovyRunnerChain(String.format("The chain required %s method to runner", methodName));
                }
                realChain.add(methodName);
                continue;
            }
            if (methodType == '?' && exist) {
                realChain.add(methodName);
            }
        }

        for (var method : realChain) {
            try {
                element = executeMethod(gobj, method);
            } catch (Exception e) {
                if (e.getCause() instanceof ChainCut) {
                    element = ((ChainCut) e.getCause()).getData();
                    break;
                } else {
                    throw e;
                }
            }
        }
        return element;
    }

    private boolean existMethod(GroovyObject gobj, String name) {
        return (
                gobj.getMetaClass().getMethods()
                        .stream()
                        .filter(x -> x.getName().equals(name))
                        .collect(Collectors.toList())
                        .size() > 0
        );
    }

    private boolean existProperties(GroovyObject gobj, String name) {
        return (
                gobj.getMetaClass().getProperties()
                        .stream()
                        .filter(x -> x.getName().equals(name))
                        .collect(Collectors.toList())
                        .size() > 0
        );
    }

    private Object executeMethod(GroovyObject gobj, String method, Object... param) throws Exception {
        return gobj.invokeMethod(method, param);
    }


}
