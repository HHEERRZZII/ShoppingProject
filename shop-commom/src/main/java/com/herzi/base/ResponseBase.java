package com.herzi.base;

//封装思想，很值的借鉴！
public class ResponseBase {

    private Integer rtnCode;

    private String msg;

    private Object data;
    //无参构造函数
    public ResponseBase(){

    }
    //有参构造函数
    public ResponseBase(Integer rtnCode, String msg, Object data) {
        super();
        this.rtnCode = rtnCode;
        this.msg = msg;
        this.data = data;
    }

    public Integer getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(Integer rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return data;
    }

    public void setObject(Object data) {
        this.data = data;
    }
}
