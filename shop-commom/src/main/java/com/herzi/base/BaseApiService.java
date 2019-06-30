package com.herzi.base;

import com.herzi.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//封装返回结果
@Component //扫包redis等组件
public class BaseApiService {
    //redis是单线程，所以不用考虑线程安全问题
    @Autowired
    protected BaseRedisService baseRedisService;

    //返回错误信息
    public ResponseBase setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null);
    }

    public ResponseBase setResultError(Integer code,String msg) {
        return setResult(code, msg, null);
    }


    //有数据值
    public ResponseBase setResultSuccess(Object data) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }


    //返回 成功，有返回信息
    public ResponseBase setResultSuccess(String msg) {
      /*  ResponseBase responseBase = new ResponseBase();
        responseBase.setRtnCode(Constants.HTTP_RES_CODE_200);
        responseBase.setMsg(Constants.HTTP_RES_CODE_200_VALUE);*/
      //换了个写法，更加简便
      return setResult(Constants.HTTP_RES_CODE_200, msg, null);
    }

    public ResponseBase setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    //通用封装
     public ResponseBase setResult(Integer rtncode, String msg, Object data) {

        return new ResponseBase(rtncode, msg, data);

    }
}
