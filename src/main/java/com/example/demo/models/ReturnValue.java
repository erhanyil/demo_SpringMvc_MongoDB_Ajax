package com.example.demo.models;

import java.io.Serializable;

public class ReturnValue implements Serializable {

    public Boolean isSuccess;
    public Object value;

    public ReturnValue(Boolean isSuccess, Object value) {
        this.isSuccess = isSuccess;
        this.value = value;
    }

    public ReturnValue(Object value) {
        this.isSuccess = true;
        this.value = value;
    }

    public ReturnValue() {
        this.isSuccess = true;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
