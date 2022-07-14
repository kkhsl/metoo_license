package com.abtnetworks.totems.common;

import com.abtnetworks.totems.common.constants.ReturnCode;
import com.abtnetworks.totems.common.vo.ResultResponseVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${startPath}")
    protected String startPath;
    @Autowired
    protected Validator validator;

    public BaseController() {
    }

    protected <T, G> void validatorParam(T tClass, Class... gClass) {
        Set<ConstraintViolation<T>> result = this.validator.validate(tClass, gClass);
        if (CollectionUtils.isNotEmpty(result) && result.size() > 0) {
            Iterator<ConstraintViolation<T>> iteratorResult = result.iterator();
            if (iteratorResult.hasNext()) {
                ConstraintViolation<T> message = (ConstraintViolation)iteratorResult.next();
                throw new IllegalArgumentException(message.getMessage());
            }
        }

    }

    @ResponseBody
    public <T> JSONObject returnJSON(String status, T data, String errcode, String errmsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status == "" ? "0" : status);
        jsonObject.put("data", data);
        jsonObject.put("errcode", errcode);
        jsonObject.put("errmsg", errmsg);
        return jsonObject;
    }

    @ResponseBody
    public <T> ResultResponseVO returnResponseSuccess(T data) {
        ResultResponseVO resultResponseVO = new ResultResponseVO(data, "0");
        return resultResponseVO;
    }

    @ResponseBody
    public <T> ResultResponseVO returnResponseFail(String status, String msg) {
        ResultResponseVO resultResponseVO = new ResultResponseVO(status, msg);
        return resultResponseVO;
    }

    @ResponseBody
    public JSONObject returnJSON(String status, JSONArray data, String errcode, String errmsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status == "" ? "0" : status);
        jsonObject.put("data", data);
        jsonObject.put("errcode", errcode);
        jsonObject.put("errmsg", errmsg);
        return jsonObject;
    }

    @ResponseBody
    public JSONObject returnJSON(String status, String data, String errcode, String errmsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status == "" ? "0" : status);
        jsonObject.put("data", data);
        jsonObject.put("errcode", errcode);
        jsonObject.put("errmsg", errmsg);
        return jsonObject;
    }

    public JSONObject getReturnJSON(int rc) {
        JSONObject jsonObject = new JSONObject();
        return this.getReturnJSON(rc, (Object)jsonObject);
    }

    public JSONObject getReturnJSON(int rc, String msg) {
        String status = "-1";
        String errcode = "";
        String errmsg = "";
        JSONObject jsonObject = new JSONObject();
        if (rc == 0) {
            status = "0";
        }

        errcode = String.valueOf(rc);
        return this.returnJSON(status, (Object)jsonObject, errcode, msg);
    }

    public <T> JSONObject getReturnJSON(int rc, T t) {
        String status = "-1";
        String errcode = "";
        String errmsg = "";
        if (rc == 0) {
            status = "0";
        }

        errcode = String.valueOf(rc);
        errmsg = ReturnCode.getMsg(rc);
        String jsonObjectString = JSONObject.toJSONString(t);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.returnJSON(status, (Object)jsonObject, errcode, errmsg);
    }

    public JSONObject getReturnJSON(int rc, JSONArray jsonArray) {
        String status = "-1";
        String errcode = "";
        String errmsg = "";
        if (rc == 0) {
            status = "0";
        }

        errcode = String.valueOf(rc);
        errmsg = ReturnCode.getMsg(rc);
        return this.returnJSON(status, jsonArray, errcode, errmsg);
    }

    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            return null;
        } catch (IOException var5) {
            return null;
        }
    }

    @ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {
        return "error/400";
    }
}
