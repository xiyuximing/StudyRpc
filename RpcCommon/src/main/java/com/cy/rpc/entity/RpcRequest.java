package com.cy.rpc.entity;

import java.io.Serializable;

public class RpcRequest implements Serializable {

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 请求类名
     */
    private String className;


    /**
     * 请求方法名
     */
    private String methodName;

    /**
     * 请求方法类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 请求方法入参
     */
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
